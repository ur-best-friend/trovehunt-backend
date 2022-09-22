package com.example.system.services.interfaces;

import com.example.system.entity.Treasure;
import com.example.system.entity.User;
import com.example.system.enums.MESSAGE_BROKER_REWARD_TYPE;

public interface IIntegrationService {
    void rewardUser(String integrationId, Treasure treasure, MESSAGE_BROKER_REWARD_TYPE rewardType);
    void rewardUser(User user, Treasure treasure, MESSAGE_BROKER_REWARD_TYPE rewardType);
}
