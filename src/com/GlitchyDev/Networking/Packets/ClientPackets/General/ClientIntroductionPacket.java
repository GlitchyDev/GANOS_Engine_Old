package com.GlitchyDev.Networking.Packets.ClientPackets.General;

import com.GlitchyDev.Networking.Packets.Packet;
import com.GlitchyDev.Networking.Packets.PacketType;

// Give SecretUUID || ( Username/Password)
public class ClientIntroductionPacket extends Packet {
    private final PacketType packetType = PacketType.C_INTRODUCTION;

    public ClientIntroductionPacket(Packet packet) {
            super(packet.getRawPacket());
    }

    public ClientIntroductionPacket(String uuid)
    {
        super(PacketType.C_INTRODUCTION + "-" + uuid);
    }

    public String getUUID()
    {
        return data[1];
    }
}
