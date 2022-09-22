package com.example.system.services.interfaces;

import com.example.system.entity.User;
import com.example.system.entity.SocialLikeEntity;
import com.example.system.entity.SocialWatchEntity;
import org.springframework.data.repository.CrudRepository;

import javax.servlet.http.HttpServletRequest;

public interface ISocialService {
    <T extends SocialWatchEntity,R extends CrudRepository<T,?>> T watchEntity(T entity, R repository, User user, HttpServletRequest request);
    <T extends SocialLikeEntity,R extends CrudRepository<T,?>> T likeEntity(Integer userId, T entity, R repository, boolean like);
}
