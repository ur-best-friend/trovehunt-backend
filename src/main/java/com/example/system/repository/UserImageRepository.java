package com.example.system.repository;

import com.example.system.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage,Integer> {
    UserImage findByUrl(String url);
}
