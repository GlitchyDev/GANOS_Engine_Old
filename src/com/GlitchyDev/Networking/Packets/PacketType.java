package com.GlitchyDev.Networking.Packets;

public enum PacketType {

    // Basic
    A_GOODBYE,

    // Server *****

    // OverworldPacket
    S_SPAWN_ENTITY, // EntityType, UUID, X, Y
    S_MOVE_ENTITY, // Entity UUID, X, Y
    S_DESPAWN_ENTITY, // Entity UUID, X, Y

    // General
    S_RETURN_GREETING,

    // Client *****

    // General
    C_INTRODUCTION,


}
