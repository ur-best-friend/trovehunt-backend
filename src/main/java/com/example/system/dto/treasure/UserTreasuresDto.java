package com.example.system.dto.treasure;

import com.example.system.entity.Treasure;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class UserTreasuresDto {
    @Getter
    private final List<Treasure> foundTreasures;
    @Getter
    private final List<Treasure> createdTreasures;
}
