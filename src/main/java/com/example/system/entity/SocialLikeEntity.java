package com.example.system.entity;

import java.util.Set;
import java.util.UUID;

public interface SocialLikeEntity {
    Set<Integer> getLikedIds();
    default void likeBy(Integer id){
        Set<Integer> liked = getLikedIds();
        if( liked.remove(id) ) return;
        getLikedIds().add(id);
    }
}
