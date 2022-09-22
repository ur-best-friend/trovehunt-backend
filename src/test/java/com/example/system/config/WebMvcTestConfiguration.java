package com.example.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Profile("test")
@Configuration
public class WebMvcTestConfiguration {
    @Autowired
    private WebApplicationContext webApplicationContext;

    // Solution taken from https://stackoverflow.com/a/51299687, make sure that it won't break (original answer rebuilds mockMvc after every test)
    @Bean
    public MockMvc getMockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
