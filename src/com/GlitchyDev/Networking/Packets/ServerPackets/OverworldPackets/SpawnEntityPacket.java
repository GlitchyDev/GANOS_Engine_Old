package com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets;

import com.GlitchyDev.Networking.Packets.EntityType;
import com.GlitchyDev.Networking.Packets.PacketBase;

import java.util.UUID;

public class SpawnEntityPacket extends PacketBase {
    public SpawnEntityPacket(String packetInfo) {
        super(packetInfo);
    }

    public EntityType getEntityType()
    {
        return EntityType.valueOf(data[0]);
    }

    public UUID getUUID()
    {
        return UUID.fromString(data[1]);
    }

    public int getX()
    {
        return Integer.valueOf(data[2]);
    }

    public int getY()
    {
        return Integer.valueOf(data[3]);
    }
}
