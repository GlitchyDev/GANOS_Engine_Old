package com.GlitchyDev.OldClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class GANOS_NetworkServer {


    public static void main(String[] args)
    {
        try {
            System.out.println("Server: Binded to port 9001");
            ServerSocket serverSocket = new ServerSocket(9001);
            while(true)
            {
                Socket incommingConnection = serverSocket.accept();
                System.out.println("Server: Accepted Incoming Connection");
                BufferedReader in = new BufferedReader(new InputStreamReader(incommingConnection.getInputStream()));
                PrintWriter out = new PrintWriter(incommingConnection.getOutputStream());

                Scanner scanner = new Scanner(System.in);
                while(true)
                {
                    out.println(scanner.nextLine());
                    out.flush();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
