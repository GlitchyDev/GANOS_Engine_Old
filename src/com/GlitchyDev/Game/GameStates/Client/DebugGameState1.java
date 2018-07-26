package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.General.InputGameState;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Utility.GlobalDataBase;
import com.GlitchyDev.graph.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;


public class DebugGameState1 extends InputGameState {
    private Renderer renderer;
    private Camera camera;
    private GameItem[] gameItems;
    private TextItem[] hudItems;
    private SpriteItem[] spriteItems;


    public DebugGameState1(GlobalDataBase globalDataBase) {
        super(globalDataBase);
        init();
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

        spriteItems = new SpriteItem[]{new SpriteItem(AssetLoader.getTextureAsset("Icon16x16"))};


    }

    @Override
    public void render() {



        renderer.render(globalDataBase.getGameWindow(),camera,gameItems,hudItems,spriteItems);
    }

    @Override
    public void logic() {


        double length = 5000;
        camera.setRotation(0, (float) (30*Math.sin((Math.PI/(length/2)) * (System.currentTimeMillis()%length))),0);

        gameItems[0].setPosition((float) (4*Math.sin((Math.PI/(length/2)) * (System.currentTimeMillis()%length))), 0,-5);

        if(Math.random() > 0.5) {
            spriteItems[0].getPosition().x = (spriteItems[0].getPosition().x + 5) % (globalDataBase.getGameWindow().getWidth() + 1);
        }
        else
        {
            spriteItems[0].getPosition().y = (spriteItems[0].getPosition().y + 5) % (globalDataBase.getGameWindow().getHeight() + 1);

        }
        hudItems[0].setText(getCurrentFPS() + " " + spriteItems[0].getPosition().x + " " + spriteItems[0].getPosition().y);


    }

    @Override
    public void enterState(GameStateType previousGameState) {
        super.enterState(previousGameState);
        globalDataBase.getGameWindow().setCursor(AssetLoader.getGeneralAsset("Icon24x24.png"),0,0);
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }
}
