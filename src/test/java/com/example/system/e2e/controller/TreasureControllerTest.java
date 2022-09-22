package com.example.system.e2e.controller;

import com.example.AbstractTrovehuntTest;
import com.example.system.entity.Treasure;
import com.example.system.entity.User;
import com.example.system.services.TreasureService;
import com.example.system.unit.repository.RepositoryTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// problem: @DataJpaTest doesn't provide
// WebApplicationContext webApplicationContext that's needed for getting mockMvc. It's made available to the context
// when running a test with @SpringBootTest, which needs to run before TreasureControllerTest tests
// (this issue doesn't appear in repo tests since webmvctestconfig is not used in them)
// this applies to ALL context (objectMapper doesn't get injected & all other dependencies, check repo tests, only works with @SpringBootTest. see examples with @DataJpaTest)
//@SpringBootTest
@DataJpaTest
class TreasureControllerTest extends AbstractTrovehuntTest {
//    @Autowired
//    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TreasureService treasureService;

    @Test
    @Transactional
    void allTreasures() throws Exception {
//        Mockito.when(treasureService.getAllTreasures()).thenReturn(Arrays.asList(treasure, treasure2));
//
//        MvcResult result = mockMvc.perform(get("/api/treasure/all")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        String resultStr = result.getResponse().getContentAsString();
//        List<Treasure> treasures = objectMapper.readValue(resultStr, objectMapper.getTypeFactory().constructCollectionType(List.class, Treasure.class));
//
//        verify(treasureService, times(1)).getAllTreasures();
//        assertThat(treasures).asList().containsOnly(treasure, treasure2);
    }

    @Test
    void createTreasure() {
    }

    @Test
    void oneTreasure() {
    }

    @Autowired
    private RepositoryTestUtil repoTestUtil;

    private User user;
    private Treasure treasure, treasure2;

    @BeforeEach()
    void initializeData() {
        user = repoTestUtil.createUser();

        treasure = repoTestUtil.createTreasure(user);
        treasure2 = repoTestUtil.createTreasure(user);
    }

    @AfterEach
    void tearDown() {
        repoTestUtil.removeAllEntities();
    }
}