package com.example.system.services;

import com.example.system.config.PublicStatsConfig;
import com.example.system.dto.HomeInfoDto;
import com.example.system.entity.Treasure;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicStatsService {
    private final TreasureService treasureService;
    private final UserService userService;
    private final PublicStatsConfig publicStatsConfig;

    public HomeInfoDto getHomeInfoDto() {
        List<Treasure> allTreasures = treasureService.getAllTreasures();
        int totalUsers = userService.getAllUsers().size();
        int totalTreasures = allTreasures.size();

        Predicate<String> stringIsNotNullOrEmpty = (s) -> s != null && s.length() > 0;
        Set<String> uniqueTreasureCities = allTreasures.stream()
                .map(Treasure::getCity)
                .filter(stringIsNotNullOrEmpty)
                .collect(Collectors.toSet());

        Set<String> uniqueTreasureCountries = allTreasures.stream()
                .map(Treasure::getCountry)
                .filter(stringIsNotNullOrEmpty)
                .collect(Collectors.toSet());

        return new HomeInfoDto(totalUsers, totalTreasures, uniqueTreasureCountries.size(), uniqueTreasureCities.size(), publicStatsConfig.getOverrideHomePageAppStats());
    }
}
