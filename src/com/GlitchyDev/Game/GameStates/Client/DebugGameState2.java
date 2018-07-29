package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.General.InputGameState;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Networking.ServerNetworkConnection;
import com.GlitchyDev.Utility.GlobalGameDataBase;
import com.GlitchyDev.graph.*;

import java.awt.*;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class DebugGameState2 extends InputGameState {
    private Renderer renderer;
    private Camera camera;
    private GameItem[] gameItems;
    private TextItem[] hudItems;
    private SpriteItem[] spriteItems;

    private ServerNetworkConnection serverNetworkConnection;

    public DebugGameState2(GlobalGameDataBase globalGameDataBase) {
        super(globalGameDataBase);
        init();

        serverNetworkConnection = new ServerNetworkConnection();
        serverNetworkConnection.enableAcceptingClients();
    }

    @Override
    public void init() {
        renderer = new Renderer();
        renderer.init();
        camera = new Camera();

        Mesh mesh = AssetLoader.getMeshAsset("cube");
        Texture texture = AssetLoader.getTextureAsset("grassblock");
        mesh.setTexture(texture);

        GameItem gameItem = new GameItem(mesh);
        gameItem.setScale(0.5f);
        gameItem.setPosition(0, 0, -2);

        GameItem gameItem2 = new GameItem(mesh);
        gameItem2.setScale(0.5f);
        gameItem2.setPosition(0, 1, -2);


        gameItems = new GameItem[]{gameItem,gameItem2};


        final Font FONT = new Font("Arial", Font.PLAIN, 20);
        final String CHARSET = "ISO-8859-1";

        FontTexture fontTexture = new FontTexture(FONT,CHARSET);
        TextItem item = new TextItem("0",fontTexture);
        hudItems = new TextItem[]{item};

        spriteItems = new SpriteItem[1];
        for(int i = 0; i < spriteItems.length; i++) {
            spriteItems[i] = new SpriteItem(AssetLoader.getTextureAsset("Tomo"));
            spriteItems[i].setPosition(0, 0, (float) (0.000001 * i));
            spriteItems[i].setScale(1.0f);
        }


    }

    @Override
    public void render() {
        renderer.render(globalGameData.getGameWindow(),camera,gameItems,hudItems,spriteItems);
    }

    @Override
    public void logic() {
        if (gameInput.getKeyValue(GLFW_KEY_ESCAPE) == 1) {
            globalGameData.getGameWindow().makeWindowClose();
            System.out.println("DebugGameState: CLOSING WINDOW");
        }
        String total = "ConnectedUsers: ";
        Iterator iterator = serverNetworkConnection.getConnectedUsers().iterator();
        while(iterator.hasNext())
        {
            total += " " + iterator.next();
        }
        hudItems[0].setText(total);





    }

    @Override
    public void enterState(GameStateType previousGameState) {
        super.enterState(previousGameState);
        globalGameData.getGameWindow().setCursor(AssetLoader.getGeneralAsset("Icon16x16.png"),0,0);
        globalGameData.getGameWindow().setIcon(getWindowHandle(),AssetLoader.getGeneralAsset("Icon24x24.png"),AssetLoader.getGeneralAsset("Icon32x32.png"));
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }
}
