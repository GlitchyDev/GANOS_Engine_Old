package com.GlitchyDev.Networking.Packets;

import java.lang.reflect.Array;

public class PacketBase {
    protected PacketType packetType;
    final protected String[] data;
    final protected String rawPacket;

    public PacketBase(String packetInfo)
    {
        rawPacket = packetInfo;
        data = packetInfo.split("_");
        packetType = PacketType.valueOf(data[0]);
    }


    public PacketType getPacketType()
    {
        return packetType;
    }

    public String getRawPacket()
    {
        return rawPacket;
    }
}
