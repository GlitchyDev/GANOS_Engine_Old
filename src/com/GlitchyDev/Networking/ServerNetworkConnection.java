package com.GlitchyDev.Networking;

import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientIntroductionPacket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.ServerPackets.General.ServerReturnGreetingPacket;
import com.GlitchyDev.Utility.GameType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ServerNetworkConnection {
    // Tag this with Players, or give Players the SUUID to tag over here
    protected ConcurrentHashMap<String, GameSocket> allConnectedClients;
    protected Collection<String> approvedUsers = Arrays.asList("James");
    private final int portNum = 9001;
    private ServerClientConnectingThread serverClientConnectingThread;

    public ServerNetworkConnection() {
        allConnectedClients = new ConcurrentHashMap<>();
        serverClientConnectingThread = new ServerClientConnectingThread(allConnectedClients, approvedUsers);
        serverClientConnectingThread.start();
    }

    public void endConnection(NetworkDisconnectType reason) {
        serverClientConnectingThread.stopThread();
        for (GameSocket gameSocket : allConnectedClients.values()) {
            gameSocket.disconnect(reason);
        }
    }

    public ConcurrentHashMap<String, GameSocket> getAllConnectedClients() {
        return allConnectedClients;
    }


    private class ServerClientConnectingThread extends Thread {
        private ServerSocket serverSocket;
        private ConcurrentHashMap<String, GameSocket> allConnectedClients;
        private Collection<String> approvedUsers;
        private boolean doStopThread = false;

        public ServerClientConnectingThread(ConcurrentHashMap<String, GameSocket> allConnectedClients, Collection<String> approvedUsers) {
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
            int connectionAttemptCount = 0;
            while (!doStopThread) {
                try {
                    new ConnectionAttemptThread(allConnectedClients, approvedUsers, serverSocket.accept()).start();
                    System.out.println("Attempt " + connectionAttemptCount);

                    // Resolve Multiple Login in issue?
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    connectionAttemptCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                    stopThread();
                }
            }
        }

        public void stopThread() {
            doStopThread = true;
        }
    }


    private class ConnectionAttemptThread extends Thread {
        private ConcurrentHashMap<String, GameSocket> allConnectedClients;
        private Collection<String> approvedUsers;
        private Socket socket;

        public ConnectionAttemptThread(ConcurrentHashMap<String, GameSocket> allConnectedClients, Collection<String> approvedUsers, Socket socket) {
            this.allConnectedClients = allConnectedClients;
            this.approvedUsers = approvedUsers;
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("ServerNetwork: A client is attempting a connection");
            GameSocket gameSocket = new GameSocket(GameType.SERVER, socket);
            while (socket.isConnected() && gameSocket.getUnprocessedPackets().size() == 0) {

            }
            ClientIntroductionPacket introduction = new ClientIntroductionPacket((PacketBase) gameSocket.getUnprocessedPackets().toArray()[0]);
            System.out.println("ServerNetwork: Recieved Authetnication UUID" + introduction.getUUID());

            if (!approvedUsers.contains(introduction.getUUID())) {
                System.out.println("ServerNetwork: User " + introduction.getUUID() + " Denied");
                gameSocket.disconnect(NetworkDisconnectType.INCORRECT_LOGIN_CREDENTIALS);
            } else {
                System.out.println("Client " + introduction.getUUID() + " Logged in!");
                gameSocket.sendPacket(new ServerReturnGreetingPacket());
                allConnectedClients.put(introduction.getUUID(), gameSocket);
            }
            gameSocket.getUnprocessedPackets().clear();
        }
    }
}