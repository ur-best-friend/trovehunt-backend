package com.example;

import com.example.system.services.AppRunnerImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.transaction.Transactional;

@Transactional
@SpringBootApplication
@RequiredArgsConstructor
@EntityScan("com.example")
@EnableJpaRepositories("com.example")
@ComponentScan(basePackages = "com.example")
public class TrovehuntApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
//        args = new String[]{"user_import", "import/users/200people(ANON).xls", "import/users/photos"};
        ConfigurableApplicationContext cac = SpringApplication.run(TrovehuntApplication.class, args);
        if (appRunnerImportService.shouldStopAfterInitialization(args)) cac.close();
    }
    private static AppRunnerImportService appRunnerImportService;
    @Autowired
    public void setAppRunnerImportService(AppRunnerImportService injectedAppRunnerImportService) {
        appRunnerImportService = injectedAppRunnerImportService;
    }
}
