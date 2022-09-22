package com.example.system.services.publishers;

import com.example.system.dto.messagebroker.UserRewardUpdateDto;

public interface MessageBrokerPublisher {
    void sendUserReward(UserRewardUpdateDto userRewardUpdateDto);
}
