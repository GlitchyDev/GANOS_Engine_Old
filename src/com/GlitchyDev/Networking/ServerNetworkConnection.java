package com.GlitchyDev.Networking;

import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientIntroductionPacket;
import com.GlitchyDev.Networking.Packets.ServerPackets.General.ServerReturnGreetingPacket;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerNetworkConnection {
    private ConcurrentHashMap<String,GameSocket> connectedClients;
    private Collection<String> connectedUsers;
    private Collection<String> approvedUsers;

    private AcceptingClientThread acceptingClientThread;
    private AtomicBoolean acceptingClients = new AtomicBoolean(false);

    private final String SERVERCONFIGPATH = "GameAssets/Saves/ServerConfigs/";


    public ServerNetworkConnection()
    {
        connectedClients = new ConcurrentHashMap<>();
        connectedUsers = Collections.synchronizedCollection(new ArrayList<>());
        approvedUsers = Collections.synchronizedCollection(new ArrayList<>());

        System.out.println("ServerNetwork: Loading Approved Users");
        approvedUsers.addAll(SaveLoader.getConfigList(new File(SERVERCONFIGPATH + "ApprovedUsers.Dev")));
        enableAcceptingClients();
    }

    public void updateConnections()
    {
        Iterator<String> userIterator = connectedUsers.iterator();
        while(userIterator.hasNext())
        {
            String username = userIterator.next();
            GameSocket socket = connectedClients.get(username);
            if(!socket.isActive())
            {
                userIterator.remove();
                connectedClients.remove(username);
                System.out.println("ServerNetwork: User " + username + " has been removed");
            }
        }

    }


    public void enableAcceptingClients()
    {
        System.out.println("ServerNetwork: Enabled Login for Users");
        acceptingClientThread = new AcceptingClientThread();
        acceptingClients.set(true);
        acceptingClientThread.start();

    }

    public void disableAcceptingClients()
    {
        acceptingClients.set(false);
        acceptingClientThread = null;
    }

    public void disconnectUser(String name, NetworkDisconnectType reason)
    {
        System.out.println("ServerNetwork: Disconnected User " + name);

        connectedClients.get(name).disconnect(reason);
        connectedClients.remove(name);
        connectedUsers.remove(name);
    }

    public void disconnectAll(NetworkDisconnectType reason)
    {
        System.out.println("ServerNetwork: Disconnected All Users");

        Iterator<String> userIterator = connectedUsers.iterator();
        while(userIterator.hasNext()) {
            String name = userIterator.next();
            System.out.println("ServerNetwork: Disconnected User " + name);
            connectedClients.get(name).disconnect(reason);
            connectedClients.remove(name);
            userIterator.remove();
        }
    }

    public Collection<String> getApprovedUsers()
    {
        return approvedUsers;
    }

    public Collection<String> getConnectedUsers()
    {
        return connectedUsers;
    }


    private class AcceptingClientThread extends Thread {
        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(GameSocket.PORTNUM);
                while(acceptingClients.get())
                {
                    Socket possibleSocket = serverSocket.accept();
                    AuthenticatingClientThread authenticatingClientThreat = new AuthenticatingClientThread(new GameSocket(possibleSocket));
                    authenticatingClientThreat.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class AuthenticatingClientThread extends Thread {
        private GameSocket gameSocket;
        public AuthenticatingClientThread(GameSocket gameSocket)
        {
            this.gameSocket = gameSocket;
        }

        @Override
        public void run() {
            System.out.println("ServerNetwork: Processing Incoming User");

            while(!gameSocket.hasUnprocessedPackets())
            {
                Thread.yield();
            }
            ClientIntroductionPacket clientIntroductionPacket = new ClientIntroductionPacket(gameSocket.getUnprocessedPackets().get(0));
            if(approvedUsers.contains(clientIntroductionPacket.getUUID()))
            {
                if(!connectedUsers.contains(clientIntroductionPacket.getUUID())) {
                    gameSocket.sendPacket(new ServerReturnGreetingPacket());
                    connectedClients.put(clientIntroductionPacket.getUUID(), gameSocket);
                    connectedUsers.add(clientIntroductionPacket.getUUID());
                    System.out.println("ServerNetwork: User " + clientIntroductionPacket.getUUID() + " Authenticated");
                }
                else
                {
                    gameSocket.disconnect(NetworkDisconnectType.USER_ALREADY_LOGGED_IN);
                    System.out.println("ServerNetwork: User " + clientIntroductionPacket.getUUID() + " Is already Logged in");
                }
            }
            else
            {
                gameSocket.disconnect(NetworkDisconnectType.INCORRECT_LOGIN_CREDENTIALS);
                System.out.println("ServerNetwork: User " + clientIntroductionPacket.getUUID() + " Denied Login");
            }
        }
    }

}