package com.example.system.enums;

public enum MESSAGE_BROKER_REWARD_TYPE {
    PARTICIPATION(51),
    CREATED_TREASURE(61),
    FOUND_TREASURE(58),
    SOMEONE_FOUND_CREATED_TREASURE(57);
    private int id; public int getId(){return id;}
    MESSAGE_BROKER_REWARD_TYPE(int id){this.id = id;}
}
