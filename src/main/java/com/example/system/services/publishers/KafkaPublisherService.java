package com.example.system.services.publishers;

import com.example.system.config.KafkaProducerConfig;
import com.example.system.dto.messagebroker.UserRewardUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPublisherService implements MessageBrokerPublisher {
    private final KafkaTemplate<String, UserRewardUpdateDto> userRewardUpdateDtoTemplate;

    @Override
    public void sendUserReward(UserRewardUpdateDto userRewardUpdateDto) {
        userRewardUpdateDtoTemplate.send(KafkaProducerConfig.TOPIC_USER_REWARD_UPDATE, userRewardUpdateDto);
    }
}
