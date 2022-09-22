package com.example.system.config;

import com.example.system.dto.geocoder.GeocoderResult;
import com.example.system.services.interfaces.IGeocoderService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.ArgumentMatchers.anyDouble;

@Profile("test")
@Configuration
public class GeocoderServiceTestConfiguration {
    @Bean
    @Primary
    public IGeocoderService mockGeocoderService() {
        IGeocoderService geocoderService = Mockito.mock(IGeocoderService.class);
        Mockito.when(geocoderService.reverseRequest(anyDouble(), anyDouble())).thenAnswer(i -> new GeocoderResult() {
            private String postfix = String.format("for %.2f,%.2f",(double)i.getArguments()[0],(double)i.getArguments()[1]);
            @Override
            public String getCity() {
                return String.format("test city "+postfix);
            }
            @Override
            public String getCountry() {
                return String.format("test country "+postfix);
            }
            @Override
            public String getState() {
                return String.format("test state "+postfix);
            }
            @Override
            public String getRegion() {
                return String.format("test region "+postfix);
            }
            @Override
            public String getFormatted() {
                return String.format("test address "+postfix);
            }
        });
        return geocoderService;
    }
}
