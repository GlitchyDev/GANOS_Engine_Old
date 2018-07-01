package com.GlitchyDev.Networking;

import com.GlitchyDev.Networking.Packets.BasicPackets.GoodbyePacket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameSocket {
    //private
    private NetworkType networkType;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Collection<PacketBase> unproccessedPackets;
    private SocketInputThread socketInputThread;
    private AtomicBoolean isConnected = new AtomicBoolean(true);

    public GameSocket(NetworkType networkType, Socket socket)
    {
        this.networkType = networkType;
        this.socket = socket;

        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        unproccessedPackets = Collections.synchronizedCollection(new ArrayList<>());
        socketInputThread = new SocketInputThread(in,unproccessedPackets,isConnected);
        socketInputThread.start();
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void sendPacket(PacketBase packet) {
        out.println(packet.getRawPacket());
        out.flush();
    }

    public void sendPackets(Collection<PacketBase> packets)
    {
        for(PacketBase packet: packets) {
            out.println(packet.getRawPacket());
        }
        out.flush();
    }

    public void disconnect(NetworkDisconnectType reason)
    {
        sendPacket(new GoodbyePacket(reason));
        disconnect();
    }

    public void disconnect()
    {
        isConnected.set(false);
        socketInputThread.stopThread();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected()
    {
        return isConnected.get();
    }

    public Collection<PacketBase> getUnprocessedPackets()
    {
        return unproccessedPackets;
    }


    private class SocketInputThread extends Thread {
        private BufferedReader in;
        private Collection<PacketBase> unproccessedPackets;
        private AtomicBoolean isConnected;

        public SocketInputThread(BufferedReader in, Collection<PacketBase> unproccessedPackets,  AtomicBoolean isConnected)
        {
            this.in = in;
            this.unproccessedPackets = unproccessedPackets;
            this.isConnected = isConnected;
        }

        @Override
        public void run() {
            while(isConnected.get())
            {
                try {
                    String rawPacket = in.readLine();
                    PacketBase packet = new PacketBase(rawPacket);
                    if(packet.getPacketType() == PacketType.A_GOODBYE)
                    {
                        System.out.println("GameSocket: Connection lost for reason " + new GoodbyePacket(packet).getDisconnectType());
                        socket.close();
                        isConnected.set(false);
                    }
                    else {
                        unproccessedPackets.add(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    stopThread();
                }
            }
        }

        public void stopThread()
        {
            isConnected.set(false);
        }
    }






}
