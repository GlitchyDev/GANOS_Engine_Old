package com.GlitchyDev.Networking.Packets.BasicPackets;

import com.GlitchyDev.Networking.NetworkDisconnectType;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

public class GoodbyePacket extends PacketBase {
    private final PacketType packetType = PacketType.A_GOODBYE;

    public GoodbyePacket(PacketBase packet) {

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
