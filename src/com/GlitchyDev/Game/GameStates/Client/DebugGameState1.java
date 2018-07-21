package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.General.MonitoredGameState;
import com.GlitchyDev.Utility.GlobalDataBase;

import java.text.DecimalFormat;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class DebugGameState1 extends MonitoredGameState {
    DecimalFormat df = new DecimalFormat();


    public DebugGameState1(GlobalDataBase globalDataBase) {
        super(globalDataBase);
        init();
    }

    @Override
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void logic() {

        System.out.println("FPS : " + getCurrentFPS() + " LOGIC: " + df.format(getLogicUtilization()) + " RENDER: " + df.format(getRenderUtilization()));
        globalDataBase.getGameWindow().setClearColor((float)(Math.random()), (float)(Math.random()), (float)(Math.random()), 1.0f);
    }

    @Override
    public void init() {
        df.setMaximumFractionDigits(3);
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }
}
