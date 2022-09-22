package com.example.system.config;

import com.example.system.services.listeners.MessageBrokerConsumer;
import com.example.system.services.publishers.MessageBrokerPublisher;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class KafkaServiceTestConfiguration {
    @Bean
    @Primary
    public MessageBrokerConsumer mockMessageBrokerConsumer() {
        return Mockito.mock(MessageBrokerConsumer.class);
    }

    @Bean
    @Primary
    public MessageBrokerPublisher mockMessageBrokerPublisher() {
//                log.info(String.format("sending to user with code %s a reward of type %s (amount: %d)",
//                        userRewardUpdateDto.getIntegrationId(),
//                        userRewardUpdateDto.getRewardType(),
//                        userRewardUpdateDto.getRewardAmount()));
        return Mockito.mock(MessageBrokerPublisher.class);
    }
}
