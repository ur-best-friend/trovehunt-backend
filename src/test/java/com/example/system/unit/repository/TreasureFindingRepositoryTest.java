package com.example.system.unit.repository;

import com.example.AbstractTrovehuntTest;
import com.example.system.entity.Treasure;
import com.example.system.entity.TreasureFinding;
import com.example.system.entity.User;
import com.example.system.repository.TreasureFindingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TreasureFindingRepositoryTest extends AbstractTrovehuntTest {
    @Test
    void findByUser() {
        // given
        TreasureFinding find = repoTestUtil.createTreasureFinding(user, treasure3);
        TreasureFinding otherFind = repoTestUtil.createTreasureFinding(otherUser, treasure);
        TreasureFinding otherFind2 = repoTestUtil.createTreasureFinding(otherUser, treasure2);

        // when
        List<TreasureFinding> userFindings = underTest.findByUser(user);
        List<TreasureFinding> otherUserFindings = underTest.findByUser(otherUser);

        // then
        assertThat(userFindings).asList()
                .hasSize(1)
                .containsOnly(find)
                .doesNotContain(otherUserFindings);

        assertThat(otherUserFindings).asList()
                .hasSize(2)
                .containsOnly(otherFind, otherFind2)
                .doesNotContain(userFindings);
    }

    @Test
    void findByVerifiedAndDeclined() {
        // TODO: Disable SpringRunner dev service test user creation, remove underTest#deleteAll() call
        underTest.deleteAll();

        // given
        User newUser = repoTestUtil.createUser();
        TreasureFinding verifiedFind = repoTestUtil.createTreasureFinding(newUser, treasure3, true, false);
        TreasureFinding declinedFind = repoTestUtil.createTreasureFinding(otherUser, treasure2, false, true);
        TreasureFinding otherDeclinedFind = repoTestUtil.createTreasureFinding(otherUser, treasure, false, true);
        TreasureFinding defaultFind = repoTestUtil.createTreasureFinding(newUser, treasure, false, false);

        // when
        List<TreasureFinding> allVerified = underTest.findByVerifiedAndDeclined(true, false);
        List<TreasureFinding> allDeclined = underTest.findByVerifiedAndDeclined(false, true);
        List<TreasureFinding> notVerifiedNotDeclined = underTest.findByVerifiedAndDeclined(false, false);

        // then
        assertThat(allVerified).asList()
                .hasSize(1)
                .containsOnly(verifiedFind)
                .doesNotContain(allDeclined, notVerifiedNotDeclined);

        assertThat(allDeclined).asList()
                .hasSize(2)
                .containsOnly(declinedFind, otherDeclinedFind)
                .doesNotContain(allVerified, notVerifiedNotDeclined);

        assertThat(notVerifiedNotDeclined).asList()
                .hasSize(1)
                .containsOnly(defaultFind)
                .doesNotContain(allVerified, allDeclined);
    }

    private Treasure treasure, treasure2, treasure3;
    private User user, otherUser;
    @Autowired
    private TreasureFindingRepository underTest;

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