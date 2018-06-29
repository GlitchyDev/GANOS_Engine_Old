package com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets;

import com.GlitchyDev.Networking.Packets.PacketBase;

import java.util.UUID;

public class DespawnEntityPacket extends PacketBase {
    public DespawnEntityPacket(String packetInfo) {
        super(packetInfo);
    }

    public UUID getUUID()
    {
        return UUID.fromString(data[0]);
    }
}
