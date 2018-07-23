package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.General.InputGameState;
import com.GlitchyDev.Game.GameStates.General.MonitoredGameState;
import com.GlitchyDev.Utility.GlobalDataBase;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class DebugGameState1 extends InputGameState {


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
        float percentageRed = (float) ((1.0/globalDataBase.getGameWindow().getWidth()) * gameInput.getMouseX());
        float percentageGreen = (float) ((1.0/globalDataBase.getGameWindow().getHeight()) * gameInput.getMouseY());
        //System.out.println("FPS : " + getCurrentFPS() + " LOGIC: " + df.format(getLogicUtilization()) + " RENDER: " + df.format(getRenderUtilization()));
        if(gameInput.isMouseInWindow()) {
            globalDataBase.getGameWindow().setClearColor(percentageRed, percentageGreen, 0.0f, 1.0f);
        }
        else
        {
            globalDataBase.getGameWindow().setClearColor(0.0f,0.0f, 1.0f, 1.0f);

        }




    }

    @Override
    public void init() {


    }

    @Override
    public void enterState(GameStateType previousGameState) {
        super.enterState(previousGameState);
        globalDataBase.getGameWindow().setCursor(new File("GameAssets/Textures/Icon/Icon16x16.png"));
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }
}
