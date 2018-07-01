package com.GlitchyDev.Networking;

import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientIntroductionPacket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientNetworkConnection {
    protected GameSocket gameSocket;
    private final int portNum = 9001;
    protected AtomicBoolean hasServerConnection = new AtomicBoolean(false);


    public void createConnection(String ip)
    {
        try {

            Socket socket = new Socket(ip,portNum);
            gameSocket = new GameSocket(NetworkType.CLIENT,socket);
            AttemptConnectThread attemptConnectThread = new AttemptConnectThread(gameSocket, hasServerConnection);
            attemptConnectThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // In the event you don't have an SUUID, send NA

    }

    public GameSocket getGameSocket()
    {
        return gameSocket;
    }

    public boolean isConnected()
    {
        return hasServerConnection.get();
    }

    private class AttemptConnectThread extends Thread {
        private GameSocket gameSocket;
        private AtomicBoolean isConnected;
        public AttemptConnectThread(GameSocket gameSocket, AtomicBoolean isConnected)
        {
            this.gameSocket = gameSocket;
            this.isConnected = isConnected;
        }

        @Override
        public void run() {
            System.out.println("ClientNetwork: Attempting Connection with the Server");
            gameSocket.sendPacket(new ClientIntroductionPacket(SaveLoader.getSaveValue("SUUID")));
            while(gameSocket.getSocket().isConnected() && gameSocket.getUnprocessedPackets().size() == 0)
            {

            }
            PacketBase responce = gameSocket.getUnprocessedPackets().iterator().next();
            if(responce.getPacketType() == PacketType.S_RETURN_GREETING)
            {
                System.out.println("ClientNetwork: Connection Founded");
                isConnected.set(true);
            }
            else
            {
                System.out.println("ClientNetwork: Connection Failed");
                gameSocket.disconnect();
            }
            gameSocket.getUnprocessedPackets().clear();

            // Wait for a Server Return Reply if
        }
    }
}
