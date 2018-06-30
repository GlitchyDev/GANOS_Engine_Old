package com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets;

import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.util.UUID;

/**
 * Packet Usage: Server Command: Despawn Target Entity by UUID from Client View ( May still actually exist on Server )
 */
public class DespawnEntityPacket extends PacketBase {
    private final PacketType packetType = PacketType.S_DESPAWN_ENTITY;

    public DespawnEntityPacket(String uuid) { super(PacketType.S_RETURN_GREETING + "_" + uuid); }

    public UUID getUUID()
    {
        return UUID.fromString(data[1]);
    }
}
