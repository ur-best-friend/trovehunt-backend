package com.example.system.services;

import com.example.system.entity.User;
import com.example.system.entity.SocialLikeEntity;
import com.example.system.entity.SocialWatchEntity;
import com.example.system.services.interfaces.ISocialService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Service
public class SocialService implements ISocialService {
    public <T extends SocialWatchEntity, R extends CrudRepository<T, ?>> T watchEntity(T entity, R repository, User user, HttpServletRequest request){
        boolean added = user != null && entity.getWatchedIds().add(user.getId() + "");
        return added ? repository.save(entity) : entity;
    }

    public <T extends SocialLikeEntity, R extends CrudRepository<T, ?>> T likeEntity(Integer userId, T entity, R repository, boolean like){
        return switchChangeId(entity.getLikedIds(),userId,like) ? repository.save(entity) : entity;
    }

    private boolean switchChangeId(Set<Integer> ids, Integer id, boolean isAdd){
        boolean contains = ids.contains(id);
        if(isAdd == contains) return false;

        if(isAdd) ids.add(id);
        else ids.remove(id);
        return true;
    }
}
