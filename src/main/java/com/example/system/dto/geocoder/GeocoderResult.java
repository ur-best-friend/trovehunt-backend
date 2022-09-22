package com.example.system.dto.geocoder;

public interface GeocoderResult {
    String getCity();
    String getCountry();
    String getState();
    String getRegion();
    String getFormatted();
}
