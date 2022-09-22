package com.example.system.services.listeners;

import com.example.system.dto.messagebroker.user.ImportUserDto;

import java.io.IOException;

public interface MessageBrokerConsumer {
    void importUser(ImportUserDto importUserDto) throws IOException;
}
