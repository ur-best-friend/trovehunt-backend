package com.example.system.controllers;

import com.example.system.dto.HomeInfoDto;
import com.example.system.services.PublicStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {
    private final PublicStatsService publicStatsService;

    @GetMapping("/app-stats")
    public HomeInfoDto getPublicInfo(){
        return publicStatsService.getHomeInfoDto();
    }
}

