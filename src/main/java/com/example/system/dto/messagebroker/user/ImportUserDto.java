package com.example.system.dto.messagebroker.user;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportUserDto {
    private String username;
    private String department;
    private String password;
    private String integrationId;
    private String imageUrl;
}
