package com.GlitchyDev.Networking;

import com.GlitchyDev.Networking.Packets.BasicPackets.GoodbyePacket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameSocket {

    //private Socket socket;
    public static final int PORTNUM = 8001;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private AtomicInteger unprocessedPacketCount = new AtomicInteger(0);
    private AtomicBoolean isActive = new AtomicBoolean(false);
    private Collection<PacketBase> unprocessedPackets;
    private ReceivePacketThread receivePacketThread;

    public GameSocket(Socket socket)
    {
        try {
            this.socket = socket;
            isActive.set(true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            unprocessedPackets = Collections.synchronizedCollection(new ArrayList<>());
            receivePacketThread = new ReceivePacketThread();
            receivePacketThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isActive()
    {
        return isActive.get();
    }


    public void sendPacket(PacketBase packet)
    {
        out.println(packet.getRawPacket());
        out.flush();
    }

    public void sendPackets(Collection<PacketBase> packets)
    {
        for(PacketBase packet : packets)
        {
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
        isActive.set(false);
        try {
            if(socket == null)
            {
                System.out.println("fuck");
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        receivePacketThread = null;
    }

    public ArrayList<PacketBase> getUnprocessedPackets()
    {
        ArrayList<PacketBase> packets = new ArrayList<>();
        Iterator<PacketBase> packetIterator = unprocessedPackets.iterator();
        while(packetIterator.hasNext())
        {
            packets.add(packetIterator.next());
        }
        unprocessedPacketCount.set(0);
        return packets;
    }

    public boolean hasUnprocessedPackets()
    {
        return unprocessedPacketCount.get() != 0;
    }

    private class ReceivePacketThread extends Thread
    {
        @Override
        public void run() {
            while(isActive.get())
            {
                try {
                    if(in.ready())
                    {
                        PacketBase packet = new PacketBase(in.readLine());
                        if(packet.getPacketType() == PacketType.A_GOODBYE)
                        {
                            System.out.println("GameSocket: Disconnected! Reason " + new GoodbyePacket(packet).getDisconnectType());
                            isActive.set(false);
                            disconnect();
                        }

                        unprocessedPackets.add(packet);
                        unprocessedPacketCount.incrementAndGet();

                    }
                    Thread.yield();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
