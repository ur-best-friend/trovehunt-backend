package com.example.system.config;

import com.example.system.entity.User;
import com.example.system.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String rawPassword = authentication.getCredentials().toString();
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("user");

        User user = userService.getUserByUsername(username);
        if(user==null) throw new UsernameNotFoundException(username+" doesn't exist");
        if(!bCryptPasswordEncoder.matches(rawPassword,user.getPassword())) throw new BadCredentialsException("Invalid username/password pair");

        //userService.registerUser(username, rawPassword, authorities);
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
