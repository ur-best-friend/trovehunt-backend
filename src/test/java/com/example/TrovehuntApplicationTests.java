package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TrovehuntApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrovehuntApplicationTests extends AbstractTrovehuntTest {
    @Test
    void contextLoads() {
    }
}
