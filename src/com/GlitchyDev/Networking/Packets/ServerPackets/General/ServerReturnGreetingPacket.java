package com.GlitchyDev.Networking.Packets.ServerPackets.General;

import com.GlitchyDev.Networking.Packets.Packet;
import com.GlitchyDev.Networking.Packets.PacketType;

// Send back SecretUUID
public class ServerReturnGreetingPacket extends Packet {
    private final PacketType packetType = PacketType.S_RETURN_GREETING;

    public ServerReturnGreetingPacket(Packet packet) {
        super(packet.getRawPacket());
    }

    public ServerReturnGreetingPacket()
    {
        super(PacketType.S_RETURN_GREETING + "");
    }

}
