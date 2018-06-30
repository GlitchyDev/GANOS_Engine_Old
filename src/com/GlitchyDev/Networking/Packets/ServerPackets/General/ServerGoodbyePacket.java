package com.GlitchyDev.Networking.Packets.ServerPackets.General;

import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

// Reason for kick
public class ServerGoodbyePacket extends PacketBase {
    private final PacketType packetType = PacketType.S_GOODBYE;

    public ServerGoodbyePacket(String reason) { super(PacketType.S_GOODBYE + "_" + reason); }

    public String getDisconnectReason()
    {
        return data[1];
    }
}
