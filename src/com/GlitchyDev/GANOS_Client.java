package com.GlitchyDev;

import com.GlitchyDev.GameStates.NetworkClientState;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Networking.GANOSClientReader;
import com.GlitchyDev.Utility.GameController;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GANOS_Client extends StateBasedGame {

    // Application Properties
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int FPS_TARGET = 60;
    public static final int SCALE = 1;


    // Class Constructor
    public GANOS_Client(String appName)
    {
        super(appName);
    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) {
        AssetLoader.loadAssets();

        this.addState(new NetworkClientState());
    }

    // Main Method
    public static void main(String[] args) {

        try {
            AppGameContainer app = new AppGameContainer(new GANOS_Client("GANOS Client Test"));
            app.setDisplayMode(WIDTH * SCALE, HEIGHT * SCALE, false);
            app.setTargetFrameRate(FPS_TARGET);
            app.setShowFPS(false);
            app.setAlwaysRender(true);

            SaveLoader.loadSave();
            GameController.linkControls(app);




            AssetLoader.setDefaultIconss(app,"GameAssets/Sprites/Pet_Icon/PET_1.png","GameAssets/Sprites/Pet_Icon/PET_3.png");
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }
}