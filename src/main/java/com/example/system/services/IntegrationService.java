package com.example.system.services;

import com.example.system.dto.messagebroker.UserRewardUpdateDto;
import com.example.system.entity.Treasure;
import com.example.system.entity.User;
import com.example.system.enums.MESSAGE_BROKER_REWARD_TYPE;
import com.example.system.services.interfaces.IIntegrationService;
import com.example.system.services.publishers.MessageBrokerPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//TODO: Create integrationConfig

@Service
@RequiredArgsConstructor
public class IntegrationService implements IIntegrationService {
    //NOTE: Can be both specified in location.properties and as ENV variable
    @Value("${integration.token}")
    private String token;
    @Value("${integration.endpoint}")
    private String apiEndpoint;
    @Value("${integration.adminId}")
    private int adminId;
    @Value("${integration.enabled}")
    private boolean enabled;

    private final MessageBrokerPublisher messagePublisherService;
//    private final OkHttpClient client = new OkHttpClient();

    public void rewardUser(User user, Treasure treasure, MESSAGE_BROKER_REWARD_TYPE rewardType){
        rewardUser(user.getIntegrationId(), treasure, rewardType);
    }
    public void rewardUser(String integrationId, Treasure treasure, MESSAGE_BROKER_REWARD_TYPE rewardType){
        int rewardAmount = rewardType == MESSAGE_BROKER_REWARD_TYPE.CREATED_TREASURE ? treasure.getRewardCreated() : treasure.getRewardFound();
        // TODO: move reward retrieval into entity/reward type class
        messagePublisherService.sendUserReward(new UserRewardUpdateDto(integrationId, rewardAmount, rewardType));
//        if(!enabled) return;
//        try {
//            String body = new JSONObject()
//                    .put("userIntegrationId", integrationId)
//                    .put("adminId", adminId)
//                    .put("badgeId", badgeType.getId()).toString();
//            Request request = new Request.Builder()
//                    .url(apiEndpoint)
//                    .addHeader("Authorization", token)
//                    .post(RequestBody.create(body, MediaType.parse("application/json")))
//                    .build();
//            Response response = client.newCall(request).execute();
//        }catch (IOException e){ e.printStackTrace(); }

    }
}
