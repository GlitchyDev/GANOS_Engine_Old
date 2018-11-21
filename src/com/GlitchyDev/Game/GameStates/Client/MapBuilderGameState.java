package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.Abstract.InputGameStateBase;
import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.GameInput.XBox360Controller;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.Fonts.CustomFontTexture;
import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.WorldElements.Camera;
import com.GlitchyDev.Rendering.Assets.WorldElements.GameItem;
import com.GlitchyDev.Rendering.Assets.WorldElements.TextItem;
import com.GlitchyDev.Utility.GlobalGameData;
import com.GlitchyDev.World.Blocks.PartialCubicBlock;
import com.GlitchyDev.World.Location;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

/**
 * A gamestate designed to aid in Map Design
 *
 * Features should include
 * - Creating Map Files
 * - Loading Map Files
 * - Editing Map Files
 * - Saving Map Files
 *
 */
public class MapBuilderGameState extends InputGameStateBase {
    private Camera camera;
    private ArrayList<GameItem> gameItems = new ArrayList<>();
    private ArrayList<TextItem> hudItems = new ArrayList<>();
    private HashMap<String,Mesh> activeMeshes = new HashMap<>();

    private XBox360Controller controller;


    public MapBuilderGameState(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.MAPBUILDER);
        init();
    }

    @Override
    public void init() {
        camera = new Camera();

        activeMeshes.put("Floor", AssetLoader.getMeshAsset("DefaultSquare"));
        activeMeshes.get("Floor").setTexture(AssetLoader.getTextureAsset("Test_Floor"));

        activeMeshes.put("Wall", AssetLoader.getMeshAsset("flatWall1"));
        activeMeshes.get("Wall").setTexture(AssetLoader.getTextureAsset("Test_Floor"));

        CustomFontTexture customTexture = new CustomFontTexture("DebugFont");

        final int NUM_TEX_ITEMS = 15;
        for(int i = 0; i < NUM_TEX_ITEMS; i++)
        {
            TextItem item = new TextItem("",customTexture);
            item.setPosition(0,i*10,0);
            hudItems.add(item);
        }


        int width = 50;
        int height = 1;
        int length = 50;


        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                for(int z = 0; z < length; z++)
                {
                    GameItem gameItem = new GameItem(activeMeshes.get("Floor"));
                    gameItem.setPosition(x*2,y,z*2);

                    gameItem.setScale(1.0f);
                    gameItems.add(gameItem);
                }
            }
        }
        Boolean[] t = new Boolean[]{true,true,true,true,true,true};
        String[] tt = new String[]{"Icon16x16","Icon24x24","Icon32x32","Icon16x16","Icon24x24","Icon32x32"};

        File file = new File("GameAssets/Configs/PartialCubicBlockDebug.configList");
        PartialCubicBlock c = null;

        
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
            c = new PartialCubicBlock(new Location(0,0,0),stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //c = new PartialCubicBlock(new Location(0,0,0),t,tt,new ArrayList<>());
        //c.save();
        gameItems.set(0,c);
        Mesh m = AssetLoader.getMeshAsset("DefaultCube");
        m.setTexture(AssetLoader.getTextureAsset("UVMapCubeTexture"));
        gameItems.set(1,new GameItem(m));
        gameItems.get(1).setPosition(0,0,2);

        /*
               File file = new File("GameAssets/Configs/PartialCubicBlockDebug.configList");
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
            c.writeObject(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
         */


        controller = new XBox360Controller(0);
    }


    NumberFormat formatter = new DecimalFormat("#0.00");
    @Override
    public void logic() {
        controller.tick();
        hudItems.get(0).setText("Render: " + formatter.format(getRenderUtilization()) + " Logic: " + formatter.format(getLogicUtilization()));
        hudItems.get(1).setText("FPS:" + getCurrentFPS());
        hudItems.get(2).setText("Pos:" + formatter.format(camera.getPosition().x) + "," + formatter.format(camera.getPosition().y) + "," + formatter.format(camera.getPosition().z)+ " Rot:" + camera.getRotation().x + "," + camera.getRotation().y + "," + camera.getRotation().x);



        if(controller.isCurrentlyActive()) {

            hudItems.get(3).setText("Controller Name " + controller.getName() + " Num_" + controller.getControllerID());
            hudItems.get(4).setText("Direction Pad " + controller.getDirectionPad());
            hudItems.get(5).setText("Directional Buttons " + controller.getNorthButton() + " " + controller.getEastButton() + " " + controller.getSouthButton() + " " + controller.getWestButton());
            hudItems.get(6).setText("Trigger Buttons " + controller.getLeftTrigger() + " " + controller.getRightTrigger());
            hudItems.get(7).setText("Bumper Buttons " + controller.getLeftBumperButton() + " " + controller.getRightBumperButton());
            hudItems.get(8).setText("JoyStick Buttons " + controller.getLeftJoyStickButton() + " " + controller.getRightJoyStickButton());
            hudItems.get(9).setText("JoySticks " + controller.getLeftJoyStickX() + " " + controller.getLeftJoyStickY() + " " + controller.getRightJoyStickX() + " " + controller.getRightJoyStickY());
            hudItems.get(10).setText("Home Buttons " + controller.getLeftHomeButton() + " " + controller.getRightHomeButton());
        }
        else
        {
            for(int i = 3; i <= 10; i++)
            {
                hudItems.get(i).setText("");
            }
        }




        logicCamera();
    }

    public void logicCamera()
    {
        final double movementSpeed = 0.3;
        if(gameInput.getKeyValue(GLFW_KEY_W) != 0)
        {
            camera.getPosition().x += movementSpeed * (float)Math.sin(Math.toRadians(camera.getRotation().y));
            camera.getPosition().z -= movementSpeed * (float)Math.cos(Math.toRadians(camera.getRotation().y));
        }
        if(gameInput.getKeyValue(GLFW_KEY_S) != 0)
        {
            camera.getPosition().x -= movementSpeed * (float)Math.sin(Math.toRadians(camera.getRotation().y));
            camera.getPosition().z += movementSpeed * (float)Math.cos(Math.toRadians(camera.getRotation().y));
        }

        if(gameInput.getKeyValue(GLFW_KEY_A) != 0)
        {
            camera.getPosition().x -= movementSpeed * (float)Math.sin(Math.toRadians(camera.getRotation().y + 90));
            camera.getPosition().z += movementSpeed * (float)Math.cos(Math.toRadians(camera.getRotation().y + 90));
        }
        if(gameInput.getKeyValue(GLFW_KEY_D) != 0)
        {
            camera.getPosition().x -= movementSpeed * (float)Math.sin(Math.toRadians(camera.getRotation().y - 90));
            camera.getPosition().z += movementSpeed * (float)Math.cos(Math.toRadians(camera.getRotation().y - 90));
        }


        final double rotationSpeed = 3.0;
        if(gameInput.getKeyValue(GLFW_KEY_RIGHT_SHIFT) != 0)
        {
            camera.getPosition().y -= movementSpeed;
        }
        if(gameInput.getKeyValue(GLFW_KEY_SPACE) != 0)
        {
            camera.getPosition().y += movementSpeed;
        }


        if(gameInput.getKeyValue(GLFW_KEY_LEFT) != 0)
        {
            camera.getRotation().y -= rotationSpeed;
        }
        if(gameInput.getKeyValue(GLFW_KEY_RIGHT) != 0)
        {
            camera.getRotation().y += rotationSpeed;
        }

        if(gameInput.getKeyValue(GLFW_KEY_UP) != 0)
        {
            camera.getRotation().x -= rotationSpeed;
        }
        if(gameInput.getKeyValue(GLFW_KEY_DOWN) != 0)
        {
            camera.getRotation().x += rotationSpeed;
        }
        camera.updateViewMatrix();

        // Can properly detect shit :D

    }

    @Override
    public void render() {
        renderer.prepRender(globalGameData.getGameWindow());
        renderer.render3DElements(globalGameData.getGameWindow(),"Default3D",camera,gameItems);
        renderer.renderHUD(globalGameData.getGameWindow(),"Default2D",hudItems);

    }


    @Override
    public void enterState(GameStateType previousGameState) {

    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }

}
