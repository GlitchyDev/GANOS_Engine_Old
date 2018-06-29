package com.GlitchyDev.Utility;

import com.GlitchyDev.GANOS_Client;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class BasicMonitoredGameState extends BasicGameState {
    private double renderingUtilization = 0.0;
    private double updateUtilization = 0.0;


    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        long startRender = gameContainer.getTime();
        doRender(gameContainer,stateBasedGame,graphics);
        long endRender = gameContainer.getTime();

        long length = endRender-startRender;
        renderingUtilization = (100.0/(1000.0/GANOS_Client.FPS_TARGET))* length;

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        long startRender = gameContainer.getTime();
        doUpdate(gameContainer,stateBasedGame,i);
        long endRender = gameContainer.getTime();

        long length = endRender-startRender;
        updateUtilization = (100.0/(1000.0/GANOS_Client.FPS_TARGET))* length;
    }

    protected abstract void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException;
    protected abstract void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException;




    protected double getRenderingUtilization()
    {
        return renderingUtilization;
    }

    protected double getUpdateUtilization()
    {
        return updateUtilization;
    }

    protected double getTotalUtilization()
    {
        return (renderingUtilization+updateUtilization);
    }

}
