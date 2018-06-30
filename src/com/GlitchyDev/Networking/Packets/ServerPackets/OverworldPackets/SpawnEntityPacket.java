package com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets;

import com.GlitchyDev.Networking.Packets.EntityType;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.util.UUID;

/**
 * Packet Usage: Server Command: Spawn Target Entity by UUID. Spawnes an Entity at the selected Location  ( Entity may have existed Server side before spawn on Client )
 */
public class SpawnEntityPacket extends PacketBase {
    private final PacketType packetType = PacketType.S_SPAWN_ENTITY;

    public SpawnEntityPacket(EntityType entityType, UUID uuid, int x, int y) {
        super(PacketType.S_SPAWN_ENTITY + "_" + uuid + "_" + x + "_" + y);
    }

    public EntityType getEntityType()
    {
        return EntityType.valueOf(data[1]);
    }

    public UUID getUUID()
    {
        return UUID.fromString(data[2]);
    }

    public int getX()
    {
        return Integer.valueOf(data[3]);
    }

    public int getY()
    {
        return Integer.valueOf(data[4]);
    }
}
