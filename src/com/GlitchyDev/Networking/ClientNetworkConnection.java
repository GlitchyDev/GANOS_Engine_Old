package com.GlitchyDev.Networking;

import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientIntroductionPacket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;
import com.GlitchyDev.Utility.GameType;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientNetworkConnection {
    protected GameSocket gameSocket;
    private final int portNum = 9001;
    protected AtomicBoolean hasServerConnection = new AtomicBoolean(false);


    public void createConnection(String ip, String suuid)
    {
        new CreateSocketThread(gameSocket,ip,suuid).start();

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

    private class CreateSocketThread extends Thread {
        private GameSocket gameSocket;
        private String ip;
        private String suuid;

        public CreateSocketThread(GameSocket gameSocket, String ip, String suuid)
        {
            this.gameSocket = gameSocket;
            this.ip = ip;
            this.suuid = suuid;
        }

        @Override
        public void run() {
            try {

                Socket socket = new Socket(ip,portNum);
                gameSocket = new GameSocket(GameType.CLIENT,socket);
                AttemptConnectThread attemptConnectThread = new AttemptConnectThread(gameSocket, hasServerConnection,suuid);
                attemptConnectThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class AttemptConnectThread extends Thread {
        private GameSocket gameSocket;
        private AtomicBoolean isConnected;
        private String suuid;
        public AttemptConnectThread(GameSocket gameSocket, AtomicBoolean isConnected, String suuid)
        {
            this.gameSocket = gameSocket;
            this.isConnected = isConnected;
            this.suuid = suuid;
        }

        @Override
        public void run() {
            System.out.println("ClientNetwork: Attempting Connection with the Server");
            gameSocket.sendPacket(new ClientIntroductionPacket(suuid));
            while(gameSocket.getSocket().isConnected() && gameSocket.getUnprocessedPackets().size() == 0)
            {

            }
            PacketBase responce = gameSocket.getUnprocessedPackets().iterator().next();
            if(responce.getPacketType() == PacketType.S_RETURN_GREETING)
            {
                System.out.println("ClientNetwork: Connection Founded");
                isConnected.set(true);
                gameSocket.getUnprocessedPackets().clear();
            }
            else
            {
                System.out.println("ClientNetwork: Connection Failed");
                gameSocket.disconnect();
                gameSocket.getUnprocessedPackets().clear();
            }

            // Wait for a Server Return Reply if
        }
    }
}
