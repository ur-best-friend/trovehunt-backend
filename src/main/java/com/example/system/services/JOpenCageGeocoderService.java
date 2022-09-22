package com.example.system.services;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageResult;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import com.example.system.config.GeocoderConfig;
import com.example.system.dto.geocoder.GeocoderResult;
import com.example.system.dto.geocoder.JOpenCageGeocoderResult;
import com.example.system.services.interfaces.IGeocoderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Log
@Service
public class JOpenCageGeocoderService implements IGeocoderService {
    private final JOpenCageGeocoder jOpenCageGeocoder;

    @Autowired
    public JOpenCageGeocoderService(GeocoderConfig geocoderConfig) {
        String openCageApiKey = geocoderConfig.getOpenCageApiKey();
        if (StringUtils.isEmpty(openCageApiKey))
            log.warning("OpenCage api key is not specified. The app won't get new treasure addresses");

        this.jOpenCageGeocoder = new JOpenCageGeocoder(openCageApiKey);
    }
    @Override
    public GeocoderResult reverseRequest(double latitude, double longitude) {
        JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude,longitude);
        request.setLanguage("ru");
        request.setNoDedupe(true);
        request.setLimit(3);
        request.setNoAnnotations(true);
        JOpenCageResponse jOpenCageResponse = jOpenCageGeocoder.reverse(request);

        jOpenCageResponse.orderResultByConfidence();
        JOpenCageResult result = jOpenCageResponse.getResults().get(0);
        return new JOpenCageGeocoderResult(result);
    }
}
