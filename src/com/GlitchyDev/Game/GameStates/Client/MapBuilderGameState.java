package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.Abstract.InputGameStateBase;
import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.GameInput.XBox360Controller;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.*;
import com.GlitchyDev.Rendering.Assets.Fonts.CustomFontTexture;
import com.GlitchyDev.Rendering.Assets.WorldElements.Camera;
import com.GlitchyDev.Rendering.Assets.WorldElements.GameItem;
import com.GlitchyDev.Rendering.Assets.WorldElements.TextItem;
import com.GlitchyDev.Utility.GlobalGameData;
import com.GlitchyDev.World.Blocks.PartialCubicBlock;
import com.GlitchyDev.World.Location;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFinish;

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
    private Camera camera2;
    private Texture renderTexture;
    private RenderBuffer renderBuffer;
    private ArrayList<GameItem> gameItems = new ArrayList<>();
    //private ArrayList<GameItem> instancedGameItems = new ArrayList<>();
    private ArrayList<PartialCubicBlock> cubicBlocks = new ArrayList<>();
    private ArrayList<TextItem> hudItems = new ArrayList<>();
    private HashMap<String,Mesh> activeMeshes = new HashMap<>();
    private PartialCubicBlock cursor;

    private XBox360Controller controller;
    private InstancedMesh instancedMesh;


    public MapBuilderGameState(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.MAPBUILDER);
        init();
    }

    @Override
    public void init() {
        camera = new Camera();
        camera.setPosition(0,12,0);
        camera.setRotation(0,-222, 0);

        camera2 = new Camera();
        camera2.setPosition(0,12,0);
        camera2.setRotation(0,-222, 0);

        renderBuffer = new RenderBuffer(500,500);
        renderTexture = new Texture(renderBuffer);





        activeMeshes.put("Floor", AssetLoader.getMeshAsset("CubicMesh1"));
        activeMeshes.get("Floor").setTexture(renderTexture);

        activeMeshes.put("Sky", AssetLoader.getMeshAsset("skyblock"));
        activeMeshes.get("Sky").setTexture(AssetLoader.getTextureAsset("DefaultTexture"));

        gameItems.add(new GameItem(activeMeshes.get("Floor")));
        gameItems.get(0).setPosition(0,10,10);
        gameItems.get(0).setRotation(0,0,0);
        gameItems.get(0).setScale(1);

        /*
        gameItems.add(new GameItem(activeMeshes.get("Sky")));
        gameItems.get(1).setPosition(59.25f,-30,59.25f);
        gameItems.get(1).setRotation(0,0,0);
        gameItems.get(1).setScale(60);
        */                


        CustomFontTexture customTexture = new CustomFontTexture("DebugFont");
        //instancedMesh = new InstancedMesh(activeMeshes.get("Floor"));

        final int NUM_TEX_ITEMS = 15;
        for(int i = 0; i < NUM_TEX_ITEMS; i++)
        {
            TextItem item = new TextItem("",customTexture);
            item.setPosition(0,i*10,0);
            hudItems.add(item);
        }





        boolean[] t = new boolean[]{true,true,true,true,true,true};
        int[] ttt = new int[]{0,1,2,3,4,5};

        InstancedGridTexture instancedGridTexture = new InstancedGridTexture(AssetLoader.getTextureAsset("UVMapCubeTexture"),2,3);
        instancedMesh = new InstancedMesh(AssetLoader.getMeshAsset("CubicMesh1"), instancedGridTexture);

        cursor = new PartialCubicBlock(new Location(0,0,0),instancedGridTexture,"UVMapCubeTexture",t,ttt,new ArrayList<>());
        cursor.setScale(1.1f);

        int width = 60;
        int height = 1;
        int length = 60;



        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                for(int z = 0; z < length; z++)
                {
                    Location location = new Location(x,y,z);
                    boolean[] faceStates = new boolean[6];
                    ArrayList<Integer> textureCode = new ArrayList<>();
                    for(int i = 0; i < 6; i++)
                    {
                        switch(i)
                        {
                            case 0:
                                faceStates[0] = true;
                                break;
                            case 1:
                                faceStates[1] = true;
                                break;
                            case 2:
                                faceStates[2] = x==(width-1);
                                break;
                            case 3:
                                faceStates[3] = z==0;
                                break;
                            case 4:
                                faceStates[4] = x==0;
                                break;
                            case 5:
                                faceStates[5] = z==(length-1);
                                break;
                        }
                        if(faceStates[i])
                        {
                            textureCode.add(i);
                        }
                    }
                    int[] a = new int[textureCode.size()];
                    for(int b = 0; b < a.length; b++)
                    {
                        a[b] = textureCode.get(b);
                    }
                    PartialCubicBlock b = new PartialCubicBlock(location,instancedGridTexture,"UVMapCubeTexture",faceStates,a,new ArrayList<>());
                    cubicBlocks.add(b);
                }
            }
        }
        /*
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                for(int z = 0; z < length; z++)
                {
                    GameItem gameItem = new GameItem(instancedMesh);
                    gameItem.setPosition(x*2,y*2,z*2);
                    gameItem.setScale(1.0f);
                    instancedGameItems.add(gameItem);
                }
            }
        }
        */


        //File file = new File("GameAssets/Configs/PartialCubicBlockDebug.configList");


        //c = new PartialCubicBlock(new Location(0,0,0),t,tt,new ArrayList<>());
        //c.save();
        /*
        gameItems.set(0,c);
        Mesh m = AssetLoader.getMeshAsset("DefaultCube");
        m.setTexture(AssetLoader.getTextureAsset("UVMapCubeTexture"));
        gameItems.set(1,new GameItem(m));
        gameItems.get(1).setPosition(0,0,2);
        */

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
            hudItems.get(11).setText("Editor Mode: " + selectionMode);

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

    int x = 0;


    boolean selectionMode = false;
    boolean cursorEnabled = false;
    int selectionX = 0;
    int selectionY = 0;
    int selectionZ = 0;


    private final float CAMERA_MOVEMENT_AMOUNT = 0.3f;
    private final float CAMERA_ROTATION_AMOUNT = 3.0f;
    private final float JOYSTICK_THRESHHOLD = 0.2f;
    public void logicCamera()
    {
        if(controller.isCurrentlyActive()) {
            if(controller.getToggleLeftHomeButton()) {
                selectionMode = !selectionMode;
            }
            if(controller.getToggleRightHomeButton())
            {
                cursorEnabled = !cursorEnabled;
            }

            cameraMovement();

            if(selectionMode)
            {
                cursorMovement();
            }


        }

    }


    public void cameraMovement()
    {
        if(!controller.getLeftJoyStickButton())
        {
            if(controller.getLeftJoyStickY() < -JOYSTICK_THRESHHOLD)
            {
                camera.moveForward(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
            }
            if(controller.getLeftJoyStickY() > JOYSTICK_THRESHHOLD)
            {
                camera.moveBackwards(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
            }
            if(controller.getLeftJoyStickX() > JOYSTICK_THRESHHOLD)
            {
                camera.moveRight(controller.getLeftJoyStickX() * CAMERA_MOVEMENT_AMOUNT);
            }
            if(controller.getLeftJoyStickX() < -JOYSTICK_THRESHHOLD)
            {
                camera.moveLeft(controller.getLeftJoyStickX() * CAMERA_MOVEMENT_AMOUNT);
            }
        }
        else
        {
            if(controller.getLeftJoyStickY() > JOYSTICK_THRESHHOLD)
            {
                camera.moveDown(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
            }
            if(controller.getLeftJoyStickY() < -JOYSTICK_THRESHHOLD)
            {
                camera.moveUp(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
            }

        }

        if(controller.getRightJoyStickX() > JOYSTICK_THRESHHOLD || controller.getRightJoyStickX() < -JOYSTICK_THRESHHOLD)
        {
            camera.moveRotation(0, controller.getRightJoyStickX() * CAMERA_ROTATION_AMOUNT,0);
        }
        if(controller.getRightJoyStickY() > JOYSTICK_THRESHHOLD || controller.getRightJoyStickY() < -JOYSTICK_THRESHHOLD)
        {
            camera.moveRotation(controller.getRightJoyStickY() * CAMERA_ROTATION_AMOUNT,0,0);

        }

        if(controller.getToggleEastButton())
        {
            Camera temp = camera;
            camera = camera2;
            camera2 = temp;
        }
    }

    public void cursorMovement()
    {
        if(controller.getToggleNorthButton())
        {
            selectionX++;
        }
        if(controller.getToggleSouthButton())
        {
            selectionX--;
        }
        if(controller.getToggleWestButton())
        {
            selectionZ++;
        }
        if(controller.getToggleEastButton())
        {
            selectionZ--;
        }
        if(controller.getToggleRightBumperButton())
        {
            selectionY++;
        }
        if(controller.getToggleRightTrigger())
        {
            selectionY--;
        }
        cursor.setPosition(selectionX*2,selectionY*2,selectionZ*2);
    }




    boolean toggle = false;
    @Override
    public void render() {
        renderer.prepRender(globalGameData.getGameWindow());

        renderBuffer.bindToRender();
        renderer.clear();
        renderer.renderInstancedPartialCubic(globalGameData.getGameWindow(),"Instance3D", camera2, instancedMesh, cubicBlocks);
        renderBuffer.unbindToRender(globalGameData.getGameWindow().getWidth(),globalGameData.getGameWindow().getHeight());


        renderer.render3DElements(globalGameData.getGameWindow(),"FlipDefault3D",camera,gameItems);
        renderer.renderInstancedPartialCubic(globalGameData.getGameWindow(),"Instance3D", camera, instancedMesh, cubicBlocks);
        renderer.renderHUD(globalGameData.getGameWindow(),"Default2D",hudItems);
        if((selectionMode || cursorEnabled) && toggle)
        {
            ArrayList<PartialCubicBlock> cc = new ArrayList<>();
            cc.add(cursor);
            renderer.renderInstancedPartialCubic(globalGameData.getGameWindow(),"Instance3D", camera, instancedMesh, cc);
        }
        toggle = !toggle;
        glFinish();
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
