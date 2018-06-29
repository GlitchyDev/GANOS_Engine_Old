package com.GlitchyDev.Networking.Packets;

public enum PacketType {
    SPAWN_ENTITY, // EntityType, UUID, X, Y
    MOVE_ENTITY, // Entity UUID, X, Y
    DESPAWN_ENTITY, // Entity UUID, X, Y
    CHANGE_STATE, // STATE
    CHANGE_INFO, // INFOTYPE, AMOUNT
    SEND_MESSAGE, // CHANNEL, USER, TEXT
}
