package com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets;

import com.GlitchyDev.Networking.Packets.Packet;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.util.UUID;

/**
 * Packet Usage: Server Command: Move Target Entity by UUID. Is an absolute location ( May upgrade to Long Measurements later
 */
public class MoveEntityPacket extends Packet {
    private final PacketType packetType = PacketType.S_SPAWN_ENTITY;

    public MoveEntityPacket(Packet packet) {
        super(packet.getRawPacket());
    }

    public MoveEntityPacket(UUID uuid, int x, int y)
    {
        super(PacketType.S_SPAWN_ENTITY + "-" + uuid + "-" + x + "-" + y);
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

