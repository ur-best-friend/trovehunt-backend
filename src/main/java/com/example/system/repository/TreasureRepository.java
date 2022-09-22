package com.example.system.repository;

import com.example.system.entity.Treasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreasureRepository extends JpaRepository<Treasure, Integer> {
        List<Treasure> findTreasuresByAuthorId(int authorId);
}
