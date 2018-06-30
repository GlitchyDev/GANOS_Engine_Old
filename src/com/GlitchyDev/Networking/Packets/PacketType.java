package com.GlitchyDev.Networking.Packets;

public enum PacketType {
    // Server *****

    // OverworldPacket
    S_SPAWN_ENTITY, // EntityType, UUID, X, Y
    S_MOVE_ENTITY, // Entity UUID, X, Y
    S_DESPAWN_ENTITY, // Entity UUID, X, Y

    // General
    S_INTRODUCTION,
    S_RETURN_GREETING,
    S_GOODBYE,

    // Client *****

    // General
    C_INTROUCTION,
    C_GOODBYE,


}
