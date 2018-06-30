package com.GlitchyDev.Networking.Packets.ServerPackets.General;

import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;


// Welcome/Denied,
public class ServerIntroductionPacket extends PacketBase {
    private final PacketType packetType = PacketType.S_INTRODUCTION;

    public ServerIntroductionPacket(String reply) {
        super(PacketType.S_INTRODUCTION + "_" + reply);
    }

    public String getReply()
    {
        return data[1];
    }


}
