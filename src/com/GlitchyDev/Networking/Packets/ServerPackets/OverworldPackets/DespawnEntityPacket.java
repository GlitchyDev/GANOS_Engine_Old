package com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets;

import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.util.UUID;

/**
 * Packet Usage: Server Command: Despawn Target Entity by UUID from Client View ( May still actually exist on Server )
 */
public class DespawnEntityPacket extends PacketBase {
    private final PacketType packetType = PacketType.S_DESPAWN_ENTITY;

    public DespawnEntityPacket(PacketBase packet) {
        super(packet.getRawPacket());
    }

    public DespawnEntityPacket(UUID uuid)
    {
        super(PacketType.S_DESPAWN_ENTITY + "-" + uuid);
    }

    public UUID getUUID()
    {
        return UUID.fromString(data[1]);
    }
}
