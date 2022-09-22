package com.example.system.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:location.properties", ignoreResourceNotFound = true)
public class GeocoderConfig {
    //NOTE: Can be both specified in location.properties and as ENV variable
    @Getter
    @Value("${OPENCAGE_API_KEY:}")
    private String openCageApiKey;
}
