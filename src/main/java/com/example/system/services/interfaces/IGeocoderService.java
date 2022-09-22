package com.example.system.services.interfaces;

import com.example.system.dto.geocoder.GeocoderResult;

public interface IGeocoderService {
    GeocoderResult reverseRequest(double latitude, double longitude);
}
