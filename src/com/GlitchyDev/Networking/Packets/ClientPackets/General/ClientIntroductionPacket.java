package com.GlitchyDev.Networking.Packets.ClientPackets.General;

import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

// Give SecretUUID || ( Username/Password)
public class ClientIntroductionPacket extends PacketBase {
    private final PacketType packetType = PacketType.C_INTROUCTION;

    public ClientIntroductionPacket(String uuid) {
        super(PacketType.C_INTROUCTION + "_" + uuid);
    }

    public String getUUID()
    {
        return data[1];
    }
}
