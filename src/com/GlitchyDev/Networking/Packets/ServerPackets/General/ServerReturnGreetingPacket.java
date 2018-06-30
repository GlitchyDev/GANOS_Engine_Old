package com.GlitchyDev.Networking.Packets.ServerPackets.General;

import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

// Send back SecretUUID
public class ServerReturnGreetingPacket extends PacketBase {
    private final PacketType packetType = PacketType.S_RETURN_GREETING;

    public ServerReturnGreetingPacket(String greeting) { super(PacketType.S_RETURN_GREETING + "_" + greeting); }

    public String getReturnedGreeting()
    {
        return data[1];
    }
}
