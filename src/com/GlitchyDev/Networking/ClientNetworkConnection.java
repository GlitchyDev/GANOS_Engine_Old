package com.GlitchyDev.Networking;

import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientIntroductionPacket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientNetworkConnection {
    private GameSocket gameSocket;
    private AttemptConnectionThread attemptConnectionThread;
    private AtomicBoolean isAuthenticated = new AtomicBoolean(false);
    private AtomicBoolean currentlyAuthenticating = new AtomicBoolean(false);



    public void connect(String ip, String username)
    {
        if(!currentlyAuthenticating.get()) {
            currentlyAuthenticating.set(true);
            System.out.println("ClientNetwork: Attempting Login to " + ip + " under name " + username);
            attemptConnectionThread = new AttemptConnectionThread(ip, username);
            attemptConnectionThread.start();
        }
    }

    public GameSocket getGameSocket()
    {
        return gameSocket;
    }

    public boolean isAuthenticated()
    {
        if(gameSocket != null && !gameSocket.isActive())
        {
            isAuthenticated.set(false);
        }
        return isAuthenticated.get();
    }

    public void disconnect(NetworkDisconnectType reason)
    {
        gameSocket.disconnect(reason);
    }





    private class AttemptConnectionThread extends Thread
    {
        private String ip;
        private String username;


        public AttemptConnectionThread(String ip, String username)
        {
            this.ip = ip;
            this.username = username;
        }

        public void run() {
            try {


                gameSocket = new GameSocket(new Socket(ip,GameSocket.PORTNUM));
                gameSocket.sendPacket(new ClientIntroductionPacket(username));

                while(!gameSocket.hasUnprocessedPackets())
                {
                    Thread.yield();
                }

                PacketBase packet = gameSocket.getUnprocessedPackets().get(0);
                if(packet.getPacketType() == PacketType.S_RETURN_GREETING)
                {
                    System.out.println("ClientNetwork: Connected to Server");
                    isAuthenticated.set(true);
                }
                else
                {
                    System.out.println("ClientNetwork: Connection Denied! Unknown Username");
                }
                currentlyAuthenticating.set(false);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
