package com.GlitchyDev.GameStates;

import com.GlitchyDev.Networking.GANOSServerHost;
import com.GlitchyDev.Utility.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class NetworkServerState extends BasicMonitoredGameState {

    private Collection<Socket> connections;
    private HashMap<Socket,PrintWriter> printWriterHashMap;
    private int x = 0;
    private int y = 0;


    public NetworkServerState()
    {
        connections = Collections.synchronizedCollection(new ArrayList<>());
        new GANOSServerHost(connections).start();
        printWriterHashMap = new HashMap<>();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
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
        for(GButtons button: GButtons.getDirections()) {
            if (GameController.isButtonPressed(button)) {
                switch(button)
                {
                    case UP:
                        y--;
                        break;
                    case RIGHT:
                        x++;
                        break;
                    case DOWN:
                        y++;
                        break;
                    case LEFT:
                        x--;
                        break;
                }
                try {
                    for (Socket socket : connections) {
                        if (!printWriterHashMap.containsKey(socket)) {
                            printWriterHashMap.put(socket, new PrintWriter(socket.getOutputStream()));
                        }
                        System.out.println(button.toString());
                        printWriterHashMap.get(socket).println("MOVE" + button.toString());
                        printWriterHashMap.get(socket).flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

    }

    @Override
    public int getID() {
        return 0;
    }

}
