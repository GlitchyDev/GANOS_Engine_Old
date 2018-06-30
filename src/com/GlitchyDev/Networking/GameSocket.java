package com.GlitchyDev.Networking;

import com.GlitchyDev.Networking.Packets.ClientPackets.General.ClientGoodByePacket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.ServerPackets.General.ServerGoodbyePacket;
import com.GlitchyDev.Networking.Packets.ServerPackets.OverworldPackets.MoveEntityPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameSocket {
    //private
    private NetworkType networkType;
    private PrintWriter out;
    private BufferedReader in;
    private Collection<PacketBase> unproccessedPackets;
    private SocketInputThread socketInputThread;

    public GameSocket(NetworkType networkType, Socket socket)
    {
        this.networkType = networkType;

        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        unproccessedPackets = Collections.synchronizedCollection(new ArrayList<>());
        socketInputThread = new SocketInputThread(in,unproccessedPackets);
        socketInputThread.start();
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

    public void disconnect(String reason)
    {
        socketInputThread.stopThread();
        if(networkType == NetworkType.SERVER)
        {
            sendPacket(new ServerGoodbyePacket(reason));
        }
        else
        {
            sendPacket(new ClientGoodByePacket(reason));
        }
    }

    public Collection<PacketBase> getUnproccessedPackets()
    {
        return unproccessedPackets;
    }


    private class SocketInputThread extends Thread {
        private BufferedReader in;
        private Collection<PacketBase> unproccessedPackets;
        private boolean doStopThread = false;

        public SocketInputThread(BufferedReader in, Collection<PacketBase> unproccessedPackets)
        {
            this.in = in;
            this.unproccessedPackets = unproccessedPackets;
        }

        @Override
        public void run() {
            while(!doStopThread)
            {
                try {
                    String rawPacket = in.readLine();
                    PacketBase packet = new PacketBase(rawPacket);
                    unproccessedPackets.add(packet);
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
