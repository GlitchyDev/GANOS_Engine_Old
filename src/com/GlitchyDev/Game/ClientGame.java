package com.GlitchyDev.Game;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalClientGameData;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;

public class ClientGame extends BasicGame {


    public ClientGame() throws Exception {

        gameWindow = new GameWindow("Client GANOS",1000,1000,true);
        globalGameData = new GlobalClientGameData(gameWindow);
        gameWindow.init();

        AssetLoader.loadAssets();
        globalGameData.initGameStates();
        gameWindow.showWindow();

    }


}
