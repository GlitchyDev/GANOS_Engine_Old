package com.GlitchyDev.Networking;

import com.GlitchyDev.Networking.Packets.PacketBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServerNetworkConnection {
    protected ConcurrentHashMap<String,GameSocket> allConnectedClients;
    protected Collection<String> approvedUsers = Arrays.asList("James");
    private final int portNum = 9001;

    public ServerNetworkConnection()
    {
        allConnectedClients = new ConcurrentHashMap<>();
    }


    private class ServerClientConnectingThread extends Thread
    {
        private ServerSocket serverSocket;
        private ConcurrentHashMap<String,GameSocket> allConnectedClients;
        private Collection<String> approvedUsers;
        private boolean doStopThread = false;

        public ServerClientConnectingThread(ConcurrentHashMap<String,GameSocket> allConnectedClients, Collection<String> approvedUsers)
        {
            this.allConnectedClients = allConnectedClients;
            try {
                serverSocket = new ServerSocket(portNum);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.approvedUsers = approvedUsers;
        }

        @Override
        public void run() {
            while(!doStopThread)
            {
                try {
                    Socket socket = serverSocket.accept();
                    GameSocket gameSocket = new GameSocket(NetworkType.SERVER, socket);
                    


                } catch (IOException e) {
                    e.printStackTrace();
                    stopThread();
                }
            }
        }

        public void stopThread()
        {
            doStopThread = true;
        }
    }

}
