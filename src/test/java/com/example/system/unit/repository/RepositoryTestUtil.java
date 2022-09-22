package com.example.system.unit.repository;

import com.example.system.entity.Treasure;
import com.example.system.entity.TreasureFinding;
import com.example.system.entity.User;
import com.example.system.entity.UserImage;
import com.example.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Lazy
@Component
public class RepositoryTestUtil {
//    private List<Treasure> createdTreasures = new ArrayList<>();
    private List<Object> createdEntities = new ArrayList<>();

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    private <T> T saveEntity(T entity) {
        entity = testEntityManager.persistAndFlush(entity);
        testEntityManager.clear();
        createdEntities.add(entity);
        return entity;
    }

    private int totalUsers = 0;
    public User createUser() {
        String username = "test_user" + totalUsers++,
                pass = passEncoder.encode("test_pass");
        return saveEntity(new User(username, pass, UserService.DEFAULT_USER_AUTHORITIES, null, null));
    }

    public Treasure createTreasure(User user) {
        double lat = 50 + 1 * Math.random(), lng = 50 + 1 * Math.random();
        return saveEntity(new Treasure(user, lat, lng, Arrays.asList("imgURL"), Arrays.asList("text")));
    }

    public TreasureFinding createTreasureFinding(User user, Treasure treasure, boolean verified, boolean declined) {
        UserImage img = saveEntity(new UserImage("", user));
        TreasureFinding treasureFinding = new TreasureFinding(img, user, treasure);
        if (verified) treasureFinding.verify();
        if (declined) treasureFinding.decline();
        return saveEntity(treasureFinding);
    }

    public TreasureFinding createTreasureFinding(User user, Treasure treasure) {
        return createTreasureFinding(user, treasure, false ,false);
    }

    public void removeAllEntities() {
        totalUsers = 0;
        createdEntities.stream().map(testEntityManager::merge).forEach(testEntityManager::remove);
        createdEntities.clear();
    }
}
