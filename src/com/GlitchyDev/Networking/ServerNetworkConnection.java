package com.GlitchyDev.Networking;


import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientIntroductionPacket;
import com.GlitchyDev.Networking.Packets.NetworkDisconnectType;
import com.GlitchyDev.Networking.Packets.ServerPackets.General.ServerReturnGreetingPacket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerNetworkConnection {
    private ConcurrentHashMap<String,BasicSocket> connectedClients;
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
        approvedUsers.addAll(getConfigList(new File(SERVERCONFIGPATH + "ApprovedUsers.Dev")));
        enableAcceptingClients();
    }

    public ArrayList<String> getConfigList(File file)
    {
        ArrayList<String> configList = new ArrayList<>();
        System.out.println("ServerNetwork: Loading File " + file.getName());

        try {
            Scanner scanner = new  Scanner(file);
            while(scanner.hasNext())
            {
                configList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return configList;
    }

    public HashMap<String,String> getConfigSettings(File file)
    {
        HashMap<String,String> configSettings = new HashMap<>();
        System.out.println("ServerNetwork: Loading File " + file.getName());

        try {
            Scanner scanner = new  Scanner(file);
            while(scanner.hasNext())
            {
                String configLine = scanner.nextLine();
                configSettings.put(configLine.split(": ")[0],configLine.split(": ")[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return configSettings;
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
                ServerSocket serverSocket = new ServerSocket(8001);
                while(acceptingClients.get())
                {
                    Socket possibleSocket = serverSocket.accept();
                    AuthenticatingClientThread authenticatingClientThreat = new AuthenticatingClientThread(new BasicSocket(possibleSocket));
                    authenticatingClientThreat.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class AuthenticatingClientThread extends Thread {
        private BasicSocket gameSocket;

        public AuthenticatingClientThread(BasicSocket gameSocket) {
            this.gameSocket = gameSocket;
        }

        @Override
        public void run() {
            System.out.println("ServerNetwork: Processing Incoming User");

            while (!gameSocket.hasUnprocessedPackets()) {
                Thread.yield();
            }
            ClientIntroductionPacket clientIntroductionPacket = new ClientIntroductionPacket(gameSocket.getUnprocessedPackets().get(0));
            if (approvedUsers.contains(clientIntroductionPacket.getUUID())) {
                gameSocket.sendPacket(new ServerReturnGreetingPacket());
                connectedClients.put(clientIntroductionPacket.getUUID(), gameSocket);
                connectedUsers.add(clientIntroductionPacket.getUUID());
                System.out.println("ServerNetwork: User " + clientIntroductionPacket.getUUID() + " Authenticated");
            } else {
                gameSocket.disconnect(NetworkDisconnectType.KICKED);
                System.out.println("ServerNetwork: User " + clientIntroductionPacket.getUUID() + " Denied Login");
            }
        }
    }
}

