package com.GlitchyDev.Networking;

import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientIntroductionPacket;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkConnection {
    protected GameSocket gameSocket;
    private final int portNum = 9001;
    protected boolean isConnected = false;


    public void createConnection(String ip)
    {
        try {
            Socket socket = new Socket(ip,portNum);
            isConnected = true;
            gameSocket = new GameSocket(NetworkType.CLIENT,socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        gameSocket.sendPacket(new ClientIntroductionPacket(SaveLoader.getSaveValue("SUUID")));
    }

    public GameSocket getGameSocket()
    {
        return gameSocket;
    }

    public boolean isConnected()
    {
        return isConnected;
    }
}
