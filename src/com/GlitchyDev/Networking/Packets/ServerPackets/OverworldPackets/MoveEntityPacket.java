package com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets;

import com.GlitchyDev.Networking.Packets.PacketBase;

import java.util.UUID;

public class MoveEntityPacket extends PacketBase {

    public MoveEntityPacket(String packetInfo) {
        super(packetInfo);
    }

    public UUID getUUID()
    {
        return UUID.fromString(data[0]);
    }

    public int getX()
    {
        return Integer.valueOf(data[1]);
    }

    public int getY()
    {
        return Integer.valueOf(data[2]);
    }
}

