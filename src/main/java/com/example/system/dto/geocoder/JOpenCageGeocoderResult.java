package com.example.system.dto.geocoder;

import com.byteowls.jopencage.model.JOpenCageComponents;
import com.byteowls.jopencage.model.JOpenCageResult;
import lombok.Getter;

public class JOpenCageGeocoderResult implements GeocoderResult {
    @Getter
    private String city, country, state, region, formatted;

    public JOpenCageGeocoderResult(JOpenCageResult geocoderResult) {
        JOpenCageComponents components = geocoderResult.getComponents();
        city = components.getCity();
        country = components.getCountry();
        state = components.getState();
        region = components.getRegion();
        formatted = geocoderResult.getFormatted();
    }
}
