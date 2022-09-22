package com.example.system.e2e.controller;

import com.example.AbstractTrovehuntTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(PublicController.class)
@DataJpaTest
public class PublicStatsControllerTest extends AbstractTrovehuntTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPublicStatsShouldReturn200() throws Exception {
        mockMvc.perform(get("/public/app-stats")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
