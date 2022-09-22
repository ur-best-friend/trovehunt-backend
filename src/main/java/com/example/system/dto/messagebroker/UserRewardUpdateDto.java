package com.example.system.dto.messagebroker;

import com.example.system.enums.MESSAGE_BROKER_REWARD_TYPE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRewardUpdateDto {
    private String integrationId;
    private int rewardAmount;
    private MESSAGE_BROKER_REWARD_TYPE rewardType;
}
