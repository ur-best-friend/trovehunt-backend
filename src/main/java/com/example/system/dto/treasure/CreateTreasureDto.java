package com.example.system.dto.treasure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: DTO image URL validation when receiving requestBody https://www.baeldung.com/spring-dynamic-dto-validation
@NoArgsConstructor
@AllArgsConstructor
public class CreateTreasureDto {
    // TODO: [LOW PRIOR] use object value w/ limited and matching amount of textBlocks & images
    @Getter
    private List<String> textBlocks;
    @Getter
    private List<String> imageUrls;
    @Getter
    private double latitude;
    @Getter
    private double longitude;
}
