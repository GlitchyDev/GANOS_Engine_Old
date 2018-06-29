package com.GlitchyDev.GameStates;

import com.GlitchyDev.GANOS_Client;
import com.GlitchyDev.Networking.GANOSClientReader;
import com.GlitchyDev.Utility.BasicControllerGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class NetworkClientState extends BasicControllerGameState {
    private Collection<String> commands;
    private int x = 0;
    private int y = 0;


    public NetworkClientState()
    {
        try {
            Socket socket = new Socket("192.168.1.3",9001);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            commands = Collections.synchronizedCollection(new ArrayList<>());
            new GANOSClientReader(commands,in).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //int width = GANOS_Client.WIDTH / 50;
        //int height = GANOS_Client.HEIGHT / 50;

        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                double random = Math.random();
                graphics.setColor(new Color((int)(random * 255),(int)(random * 255),(int)(random * 255)));
                graphics.fillRect(x * 50, y * 50, 50,50);
            }
        }

        graphics.setColor(Color.red);
        graphics.fillRect(this.x * 50, this.y * 50, 50, 50);
    }

    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (commands.size() > 0) {
            for (String command : commands) {
                switch (command) {
                    case "MOVEUP":
                        y--;
                        y = y <= 0 ? 0 : y;
                        break;
                    case "MOVEDOWN":
                        y++;
                        y = y > 10 ? 10 : y;
                        break;
                    case "MOVERIGHT":
                        x++;
                        x = x > 10 ? 10 : x;
                        break;
                    case "MOVELEFT":
                        x--;
                        x = x <= 0 ? 0 : x;
                        break;
                }
            }
            commands.clear();
        }


    }

    @Override
    public int getID() {
        return 0;
    }
}
