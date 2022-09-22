package com.example.system.config;

import com.example.system.entity.User;
import com.example.system.services.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Getter @Setter
    private UserService userService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //TODO: Replace authentication provider with userDetailsService
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(new AuthenticationProvider(userService,bCryptPasswordEncoder()))
                .userDetailsService((s) ->{
                    User ret = userService.getUserByUsername(s);
                    if(ret==null) throw new UsernameNotFoundException(s+" doesn't exist");
                    return ret;
                });
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // REQUIRED TO PREVENT SPRING BOOT AUTO-CONFIGURATION
        return super.authenticationManagerBean();
    }
    //TODO: Add CSRF tokens for all frontend requests (https://www.baeldung.com/spring-security-csrf)
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                    .antMatchers("/api/**")
                    .fullyAuthenticated()
                    .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/");
    }

    @Value("${cors.allow-local}")
    private boolean allowLocalCors;
    @Value("${server.domain}")
    private String appDomain;

    private CorsFilter corsFilter;

    @PostConstruct
    private void initializeWebSecurityConfigurer(){
        CorsConfiguration config = new CorsConfiguration();
        if(allowLocalCors) {
            config.setAllowedOriginPatterns(Arrays.asList(appDomain));
        }

        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        corsFilter = new CorsFilter(source);
        log.info("Local CORS is "+(allowLocalCors ? "enabled":"disabled"));
    }
}
