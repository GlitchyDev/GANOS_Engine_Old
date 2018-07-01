package com.GlitchyDev.GameStates;

import com.GlitchyDev.Networking.ClientNetworkConnection;
import com.GlitchyDev.Utility.BasicMonitoredGameState;
import com.GlitchyDev.Utility.GButtons;
import com.GlitchyDev.Utility.GameController;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Collection;

public class NetworkClientState extends BasicMonitoredGameState {
    private int x = 0;
    private int y = 0;
    private ClientNetworkConnection clientNetworkConnection;


    public NetworkClientState(ClientNetworkConnection clientNetworkConnection)
    {
        this.clientNetworkConnection = clientNetworkConnection;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

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

        if(clientNetworkConnection.isConnected()) {
            graphics.setColor(Color.red);
        }
        else
        {
            graphics.setColor(Color.red);
        }
        graphics.fillRect(this.x * 50, this.y * 50, 50, 50);

        graphics.setColor(Color.blue);
        graphics.drawString(String.valueOf(this.getTotalUtilization()),0,0);
    }

    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(GameController.isButtonPressed(GButtons.START) && !clientNetworkConnection.isConnected())
        {
            clientNetworkConnection.createConnection("192.168.1.3");
        }


    }

    @Override
    public int getID() {
        return 0;
    }


}
