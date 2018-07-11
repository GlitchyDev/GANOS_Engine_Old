package com.GlitchyDev.Utility;

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
        final long startRender = System.nanoTime();
        doRender(gameContainer,stateBasedGame,graphics);
        final long endRender = System.nanoTime();
        final long length = endRender-startRender;

        final int frameLength = 1000000000;
        final double total = (100.0/(frameLength/60))*length;

        renderingUtilization = total;

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        final long startRender = System.nanoTime();
        doUpdate(gameContainer,stateBasedGame,i);
        final long endRender = System.nanoTime();
        final long length = endRender-startRender;

        final int frameLength = 1000000000;
        final double total = (100.0/(frameLength/60))*length;

        updateUtilization = total;
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
