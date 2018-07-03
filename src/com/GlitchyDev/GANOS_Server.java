package com.GlitchyDev;

import com.GlitchyDev.GameStates.NetworkServerState;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Networking.GameSocket;
import com.GlitchyDev.Networking.NetworkDisconnectType;
import com.GlitchyDev.Networking.ServerNetworkConnection;
import com.GlitchyDev.Utility.GameController;
import com.GlitchyDev.Utility.GameType;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GANOS_Server extends StateBasedGame {

    // Application Properties
    public static final int WIDTH = 850;
    public static final int HEIGHT = 700;
    public static final int FPS_TARGET = 60;
    //public static final int SCALE = 1;
    private ServerNetworkConnection serverNetworkConnection;




    // Class Constructor
    public GANOS_Server(String appName)
    {
        super(appName);
    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) {
        AssetLoader.loadAssets();
        serverNetworkConnection = new ServerNetworkConnection();
        this.addState(new NetworkServerState(serverNetworkConnection));
    }

    // Main Method
    public static void main(String[] args) {

        try {
            AppGameContainer app = new AppGameContainer(new GANOS_Server("GANOS Server Test"));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS_TARGET);
            app.setShowFPS(false);
            app.setAlwaysRender(true);

            Display.setLocation(500,0);


            SaveLoader.loadSave(GameType.SERVER);
            GameController.linkControls(app);


            //AssetLoader.setDefaultIconss(app,"GameAssets/Sprites/Pet_Icon/PET_1.png","GameAssets/Sprites/Pet_Icon/PET_3.png");
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean closeRequested() {
        serverNetworkConnection.endConnection(NetworkDisconnectType.SERVER_WINDOW_CLOSED);
        return super.closeRequested();
    }
}
