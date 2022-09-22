package com.example;

import com.example.system.config.WebMvcTestConfiguration;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

//testing kafka - https://www.baeldung.com/spring-boot-kafka-testing, https://stackoverflow.com/a/48756251

//@EnableKafka
@ActiveProfiles("test")
@RequiredArgsConstructor
@ContextConfiguration(classes = {TrovehuntApplication.class, WebMvcTestConfiguration.class, TrovehuntApplicationTests.class})
@EnableAutoConfiguration
//@EmbeddedKafka(partitions = 2, brokerProperties = { "listeners=PLAINTEXT://localhost:29095", "port=29095" })
public abstract class AbstractTrovehuntTest {
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
}
