package com.GlitchyDev.Networking.Packets;

public class PacketBase {
    protected PacketType packetType;
    final protected String[] data;
    final protected String rawPacket;

    public PacketBase(String packetInfo)
    {
        rawPacket = packetInfo;
        data = packetInfo.split("-");
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
