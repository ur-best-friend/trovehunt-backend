package com.example.system.repository;

import com.example.system.entity.TreasureFinding;
import com.example.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreasureFindingRepository extends JpaRepository<TreasureFinding, Integer> {
    List<TreasureFinding> findByUser(User user);
    List<TreasureFinding> findByVerifiedAndDeclined(boolean verified, boolean declined);
}
