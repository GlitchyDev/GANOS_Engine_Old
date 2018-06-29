package com.GlitchyDev.Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;

public class GANOSServerHost extends Thread {
    private Collection<Socket> sockets;

    public GANOSServerHost(Collection<Socket> sockets) {
        this.sockets = sockets;


    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9001);
            while(isAlive())
            {
                Socket incommingConnection = serverSocket.accept();
                sockets.add(incommingConnection);
                System.out.println("Server: Accepted Incoming Connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
