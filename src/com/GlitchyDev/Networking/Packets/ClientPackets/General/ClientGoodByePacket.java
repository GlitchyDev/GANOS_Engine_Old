package com.GlitchyDev.Networking.Packets.ClientPackets.General;

import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

// Disconnect Reason
public class ClientGoodByePacket extends PacketBase {
    private final PacketType packetType = PacketType.C_GOODBYE;

    public ClientGoodByePacket(String reason) { super(PacketType.C_GOODBYE + "_" + reason); }

    public String getDisconnectReason()
    {
        return data[1];
    }
}
