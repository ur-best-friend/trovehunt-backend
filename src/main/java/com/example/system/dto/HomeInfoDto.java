package com.example.system.dto;

public class HomeInfoDto {
    private int totalUsers, totalTreasures, totalCountries, totalCities;
    private boolean overrideStats;

    public HomeInfoDto(int totalUsers, int totalTreasures, int totalCountries, int totalCities) {
        this(totalUsers, totalTreasures, totalCountries, totalCities, false);
    }

    public HomeInfoDto(int totalUsers, int totalTreasures, int totalCountries, int totalCities, boolean overrideStats) {
        this.totalUsers = totalUsers;
        this.totalTreasures = totalTreasures;
        this.totalCountries = totalCountries;
        this.totalCities = totalCities;
        this.overrideStats = overrideStats;
    }

    public int getTotalUsers() {
        return Math.max(overrideStats ? 200 : 0, totalUsers);
    }

    public int getTotalTreasures() {
        return Math.max(overrideStats ? 132 : 0, totalTreasures);
    }

    public int getTotalCountries() {
        return Math.max(overrideStats ? 71 : 0, totalCountries);
    }

    public int getTotalCities() {
        return Math.max(overrideStats ? 130 : 0, totalCities);
    }
}
