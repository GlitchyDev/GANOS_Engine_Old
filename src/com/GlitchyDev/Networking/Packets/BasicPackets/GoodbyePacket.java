package com.GlitchyDev.Networking.Packets.BasicPackets;

import com.GlitchyDev.Networking.Packets.NetworkDisconnectType;
import com.GlitchyDev.Networking.Packets.Packet;
import com.GlitchyDev.Networking.Packets.PacketType;

public class GoodbyePacket extends Packet {
    private final PacketType packetType = PacketType.A_GOODBYE;

    public GoodbyePacket(Packet packet) {

        super(packet.getRawPacket());
    }

    public GoodbyePacket(NetworkDisconnectType networkDisconnectType)
    {
        super(PacketType.A_GOODBYE + "-" + networkDisconnectType);
    }


    public NetworkDisconnectType getDisconnectType()
    {
        return NetworkDisconnectType.valueOf(data[1]);
    }
}
