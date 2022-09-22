package com.example.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicStatsConfig {
    @Value("${demo.override-displayed-stats}")
    private boolean overrideHomePageAppStats;
    public boolean getOverrideHomePageAppStats() {
        return  overrideHomePageAppStats;
    }
}
