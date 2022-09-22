package com.example.system.unit.repository;

import com.example.AbstractTrovehuntTest;
import com.example.system.entity.Treasure;
import com.example.system.entity.User;
import com.example.system.repository.TreasureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TreasureRepositoryTest extends AbstractTrovehuntTest {
    private Treasure treasure, treasure2, treasure3;
    private User user, otherUser;
    @Autowired
    private TreasureRepository underTest;

    @Test
    @Transactional
    public void findTreasuresByUser() {
        assertThat(underTest.findTreasuresByAuthorId(user.getId())).asList()
                .hasSize(2)
                .doesNotContain(treasure3)
                .containsOnly(treasure, treasure2);
    }

    @Test
    @Transactional
    public void findTreasuresByOtherUser() {
        assertThat(underTest.findTreasuresByAuthorId(otherUser.getId())).asList()
                .hasSize(1)
                .doesNotContain(treasure, treasure2)
                .containsOnly(treasure3);
    }

    @Autowired
    private RepositoryTestUtil repoTestUtil;

    @BeforeEach()
    void initializeData() {
        user = repoTestUtil.createUser();
        otherUser = repoTestUtil.createUser();

        treasure = repoTestUtil.createTreasure(user);
        treasure2 = repoTestUtil.createTreasure(user);
        treasure3 = repoTestUtil.createTreasure(otherUser);
    }

    @AfterEach
    void tearDown() {
        repoTestUtil.removeAllEntities();
    }
}