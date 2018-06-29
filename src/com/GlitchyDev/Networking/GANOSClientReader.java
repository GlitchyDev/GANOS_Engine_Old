package com.GlitchyDev.Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.GlitchyDev.Networking.PacketCommands.*;

public class GANOSClientReader extends Thread {
    private BufferedReader in;
    private Collection<String> commands;

    public GANOSClientReader(Collection<String> commands, BufferedReader in) {
        this.in = in;
        this.commands = commands;
    }

    @Override
    public void run() {
        while(isAlive())
        {
            try {
                String input = in.readLine();
                commands.add(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
