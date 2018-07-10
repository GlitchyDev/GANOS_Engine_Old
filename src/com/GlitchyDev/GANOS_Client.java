package com.GlitchyDev;

import com.GlitchyDev.GameStates.NetworkClientState;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Networking.ClientNetworkConnection;
import com.GlitchyDev.Networking.NetworkDisconnectType;
import com.GlitchyDev.Utility.GameController;
import com.GlitchyDev.Utility.GameType;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GANOS_Client extends StateBasedGame {

    // Application Properties
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int FPS_TARGET = 60;
    public static final int SCALE = 1;

    private ClientNetworkConnection clientNetworkConnection;


    // Class Constructor
    public GANOS_Client(String appName)
    {
        super(appName);
    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) {
        AssetLoader.loadAssets();


        clientNetworkConnection = new ClientNetworkConnection();
        this.addState(new NetworkClientState(clientNetworkConnection));
    }

    // Main Method
    public static void main(String[] args) {

        try {
            AppGameContainer app = new AppGameContainer(new GANOS_Client("GANOS Client Test"));
            app.setDisplayMode(WIDTH * SCALE, HEIGHT * SCALE, false);
            app.setTargetFrameRate(FPS_TARGET);
            app.setShowFPS(false);
            app.setAlwaysRender(true);

            SaveLoader.loadSave(GameType.CLIENT);
            GameController.linkControls(app);

            Display.setLocation(0,0);


            //AssetLoader.setDefaultIconss(app,"GameAssets/Sprites/Pet_Icon/PET_1.png","GameAssets/Sprites/Pet_Icon/PET_3.png");
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean closeRequested() {
        if(clientNetworkConnection.isAuthenticated() || clientNetworkConnection.isCurrentlyAuthenticating()) {
            clientNetworkConnection.disconnect(NetworkDisconnectType.CLIENT_WINDOW_CLOSED);
        }
        return super.closeRequested();
    }
}