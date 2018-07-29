package com.GlitchyDev.Networking;

import com.GlitchyDev.Networking.Packets.BasicPackets.GoodbyePacket;
import com.GlitchyDev.Networking.Packets.NetworkDisconnectType;
import com.GlitchyDev.Networking.Packets.Packet;
import com.GlitchyDev.Networking.Packets.PacketType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class BasicSocket {
    private PacketReadThread packetReadThread;

    private Socket socket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    private List<Packet> unproccesedPackets;


    public BasicSocket(Socket socket)
    {
        this.socket = socket;
        try {
            socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOutput = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        unproccesedPackets = Collections.synchronizedList(new ArrayList<>());

        packetReadThread = new PacketReadThread();
        packetReadThread.start();
    }

    public void sendPacket(Packet packet)
    {
        socketOutput.println(packet.getRawPacket());
        socketOutput.flush();
    }

    public void sendPackets(Collection<Packet> packets)
    {
        for(Packet packet : packets)
        {
            socketOutput.println(packet.getRawPacket());
        }
        socketOutput.flush();
    }

    public ArrayList<Packet> getUnprocessedPackets()
    {
        ArrayList<Packet> packets = new ArrayList<>();
        Iterator<Packet> packetIterator = unproccesedPackets.iterator();
        while(packetIterator.hasNext())
        {
            packets.add(packetIterator.next());
        }
        return packets;
    }

    public boolean hasUnprocessedPackets()
    {
        return unproccesedPackets.size() != 0;
    }

    public void disconnect(NetworkDisconnectType reason)
    {
        sendPacket(new GoodbyePacket(reason));
        disconnect();
    }

    public void disconnect()
    {
        packetReadThread.killThread();
        try {
            socket.close();
            socketInput.close();
            socketOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class PacketReadThread extends Thread {
        private AtomicBoolean keepThreadAlive;


        public PacketReadThread()
        {
            keepThreadAlive = new AtomicBoolean(true);
        }

        @Override
        public void run() {
            try {
                while(keepThreadAlive.get())
                {
                    if(socketInput.ready())
                    {
                        Packet packet = new Packet(socketInput.readLine());
                        if(packet.getPacketType() == PacketType.A_GOODBYE)
                        {
                            disconnect();
                        }
                        unproccesedPackets.add(packet);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void killThread()
        {
            keepThreadAlive.set(false);
        }
    }
}
