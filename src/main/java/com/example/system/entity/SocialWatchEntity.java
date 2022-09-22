package com.example.system.entity;

import java.util.Set;
import java.util.UUID;

public interface SocialWatchEntity {
    Set<String> getWatchedIds();
    default void watchBy(UUID id){
        getWatchedIds().add(id+"");
    }
}
