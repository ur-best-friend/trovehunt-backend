package com.example.system.services.listeners;

import com.example.system.config.KafkaConsumerConfig;
import com.example.system.dto.messagebroker.user.ImportUserDto;
import com.example.system.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService implements MessageBrokerConsumer {
    private final UserService userService;

    // TODO: Test kafka messages through tests or external tools (might need to add external connection ports in docker)
    // TODO: DTO image URL validation https://www.baeldung.com/spring-dynamic-dto-validation
    @Override
    @KafkaListener(
            topics = KafkaConsumerConfig.TOPIC_IMPORT_USER,
            containerFactory = "kafkaListenerContainerFactory",
            properties = {"spring.json.value.default.type=com.example.system.dto.messagebroker.user.ImportUserDto"})
    public void importUser(ImportUserDto importUserDto) throws IOException {
        userService.createUser(importUserDto.getUsername(), importUserDto.getPassword(), importUserDto.getImageUrl(), importUserDto.getIntegrationId());
    }
}
