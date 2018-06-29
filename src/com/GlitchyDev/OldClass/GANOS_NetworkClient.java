package com.GlitchyDev.OldClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GANOS_NetworkClient {



    public static void main(String[] args)
    {
        try {
            Socket socket = new Socket("192.168.1.3",9001);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());


            out.println("YEET");
            out.flush();
            System.out.println(in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
