package com.example.system.dto.treasure;

import lombok.Getter;

import java.util.List;

// TODO: DTO image URL validation when receiving requestBody https://www.baeldung.com/spring-dynamic-dto-validation
public class EditTreasureDto {
    // TODO: [LOW PRIOR] use object value w/ limited and matching amount of textBlocks & images
    @Getter
    private List<String> textBlocks;

    @Getter
    private List<String> imageUrls;
}

