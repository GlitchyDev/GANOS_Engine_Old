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
import com.GlitchyDev.World.*;
import com.GlitchyDev.World.Blocks.PartialCubicBlock;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

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
    private ArrayList<TextItem> hudItems = new ArrayList<>();
    //private HashMap<String,Mesh> activeMeshes = new HashMap<>();
    private PartialCubicBlock cursor;

    private XBox360Controller controller;
    private PartialCubicInstanceMesh instancedMesh;
    private InstancedGridTexture instancedGridTexture;
    private PartialCubicInstanceMesh cursorInstancedMesh;

    //private World world;
    private World world;

    public MapBuilderGameState(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.MAPBUILDER);
        init();
    }

    @Override
    public void init() {
        camera = new Camera();
        camera.setPosition(0,10,10);
        camera.setRotation(45,0, 0);

        camera2 = new Camera();
        camera2.setPosition(-5,10,-5);
        camera2.setRotation(30,135, 0);

        renderBuffer = new RenderBuffer(500,500);
        renderTexture = new Texture(renderBuffer);



        Mesh instanceMesh = AssetLoader.getMeshAsset("CubicMesh1");
        instanceMesh.setTexture(renderTexture);

        gameItems.add(new GameItem(instanceMesh));
        gameItems.get(0).setPosition(0,1,2);
        gameItems.get(0).setRotation(0,0,0);
        gameItems.get(0).setScale(1);



        CustomFontTexture customTexture = new CustomFontTexture("DebugFont");

        final int NUM_TEX_ITEMS = 15;
        for(int i = 0; i < NUM_TEX_ITEMS; i++)
        {
            TextItem item = new TextItem("",customTexture);
            item.setPosition(0,i*10,0);
            hudItems.add(item);
        }





        boolean[] defaultOrientation = new boolean[]{true,true,true,true,true,true};
        int[] defaultTextureMapping = new int[]{0,1,2,3,4,5};

        InstancedGridTexture cursorGridTexture = new InstancedGridTexture(AssetLoader.getTextureAsset("EditCursor"),2,3);
        instancedGridTexture = new InstancedGridTexture(AssetLoader.getTextureAsset("UVMapCubeTexture"),2,3);
        cursorInstancedMesh = new PartialCubicInstanceMesh(AssetLoader.getMeshAsset("CubicMesh1"),60*60, cursorGridTexture);
        instancedMesh = new PartialCubicInstanceMesh(AssetLoader.loadMesh("/Mesh/PartialCubicBlock/CubicMesh1.obj"),60*60, instancedGridTexture);


        int[] zeroTextureMap = new int[]{0,0,0,0,0,0};
        cursor = new PartialCubicBlock(new Location(0,0,0), cursorGridTexture,"Cursor",defaultOrientation,zeroTextureMap);
        cursor.setScale(1.4f);

        int width = 60;
        int height = 1;
        int length = 60;

       // world = new World("DebugWorld");



        world = new World("DebugWorld");
        ArrayList<Integer> textureCode = new ArrayList<>();
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                for(int z = 0; z < length; z++)
                {
                    Location location = new Location(x,y,z);
                    boolean[] faceStates = new boolean[6];
                    textureCode.clear();
                    faceStates[0] = true;
                    for(int i = 0; i < 6; i++)
                    {
                        textureCode.add(i);
                    }
                    int[] a = new int[textureCode.size()];
                    for(int b = 0; b < a.length; b++)
                    {
                        a[b] = textureCode.get(b);
                    }
                    PartialCubicBlock b = new PartialCubicBlock(location, instancedGridTexture,"UVMapCubeTexture",faceStates,a);
                    world.setBlock(location,b);

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
        hudItems.get(2).setText("Pos:" + formatter.format(camera.getPosition().x) + "," + formatter.format(camera.getPosition().y) + "," + formatter.format(camera.getPosition().z)+ " Rot:" + formatter.format(camera.getRotation().x) + "," + formatter.format(camera.getRotation().y) + "," + formatter.format(camera.getRotation().z));



        if(controller.isCurrentlyActive()) {

            hudItems.get(3).setText("Controller Name " + controller.getName() + " Num_" + controller.getControllerID());
            hudItems.get(4).setText("Direction Pad " + controller.getDirectionPad());
            hudItems.get(5).setText("Directional Buttons " + controller.getNorthButton() + " " + controller.getEastButton() + " " + controller.getSouthButton() + " " + controller.getWestButton());
            hudItems.get(6).setText("Trigger Buttons " + formatter.format(controller.getLeftTrigger()) + " " + formatter.format(controller.getRightTrigger()));
            hudItems.get(7).setText("Bumper Buttons " + controller.getLeftBumperButton() + " " + controller.getRightBumperButton());
            hudItems.get(8).setText("JoyStick Buttons " + controller.getLeftJoyStickButton() + " " + controller.getRightJoyStickButton());
            hudItems.get(9).setText("JoySticks " + formatter.format(controller.getLeftJoyStickX()) + " " + formatter.format(controller.getLeftJoyStickY()) + " " + formatter.format(controller.getRightJoyStickX()) + " " + formatter.format(controller.getRightJoyStickY()));
            hudItems.get(10).setText("Home Buttons " + controller.getLeftHomeButton() + " " + controller.getRightHomeButton());
            hudItems.get(11).setText("Editor Mode: " + currentEditState + " EnableWallMode: " + enableWallMode);
            hudItems.get(12).setText("Cursor Location: X:" + cursorLocation.getX() + " Y:" + cursorLocation.getY() + " Z:" + cursorLocation.getZ());

        }
        else
        {
            for(int i = 3; i <= 10; i++)
            {
                hudItems.get(i).setText("");
            }
        }



        if(controller.isCurrentlyActive()) {
            cameraControlsLogic();
            editControlsLogic();
        }

        //logicCamera();
    }



    /*
    boolean selectionMode = false;
    boolean cursorEnabled = false;
    int selectionX = 0;
    int selectionY = 0;
    int selectionZ = 0;


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

            cameraControlsLogic();

            if(selectionMode)
            {
                cursorMovement();
            }


        }

    }
    */


    /**
     * Movement for Camera
     *
     * Left Joystick
     * - Button + Vertical Direction: Y axis
     * - Horizontal Direction: Relative Left and Right
     * - Vertical Direction: Relative Forward and Backwards
     *
     * Right JoyStick
     * - Button: Switch Camera
     * - Horizontal Direction: Pan Camera Left/Right
     * - Vertical Direction: Pan Camera Up/Down
     */
    private final float CAMERA_MOVEMENT_AMOUNT = 0.3f;
    private final float CAMERA_ROTATION_AMOUNT = 3.0f;
    private final float JOYSTICK_THRESHHOLD = 0.2f;
    public void cameraControlsLogic()
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

        if(controller.getToggleRightJoyStickButton())
        {
            Camera temp = camera;
            camera = camera2;
            camera2 = temp;
        }
    }

    public enum EditState {
        MOVE_CURSOR, // Move Cursor, Delete whole Tiles
        EDIT_MODEL, // Select Models, Bind, Select Orientation
        EDIT_TEXTURE, // Select Texture Sheet, Bind Textures
        ;
        public EditState toggleState()
        {
            switch(this)
            {
                case MOVE_CURSOR:
                    return EDIT_MODEL;
                case EDIT_MODEL:
                    return EDIT_TEXTURE;
                case EDIT_TEXTURE:
                    return MOVE_CURSOR;
            }
            return MOVE_CURSOR;
        }
    }

    private EditState currentEditState = EditState.MOVE_CURSOR;
    public void editControlsLogic()
    {
        if(controller.getToggleRightHomeButton())
        {
            currentEditState = currentEditState.toggleState();


            switch(currentEditState)
            {

                case MOVE_CURSOR:
                    for(Direction direction: Direction.values())
                    {
                        cursor.setDirectionTexture(direction,0);
                    }
                    break;
                case EDIT_MODEL:
                    for(Direction direction: Direction.values())
                    {
                        cursor.setDirectionTexture(direction,2);
                    }
                    break;
                case EDIT_TEXTURE:
                    for(Direction direction: Direction.values())
                    {
                        cursor.setDirectionTexture(direction,4);
                    }
                    break;
            }

        }
        switch(currentEditState)
        {

            case MOVE_CURSOR:
                moveCursorControlsLogic();
                break;
            case EDIT_MODEL:
                editModelControlsLogic();
                break;
            case EDIT_TEXTURE:
                //editTextureControlsLogic();
                break;
        }
    }

    private Location cursorLocation = new Location(0,0,0);
    private boolean enableWallMode = true;
    public void moveCursorControlsLogic() {
        if (controller.getToggleNorthButton()) {
            cursorLocation.addOffset(0,0,-1);
        }
        if (controller.getToggleSouthButton()) {
            cursorLocation.addOffset(0,0,1);
        }
        if (controller.getToggleWestButton()) {
            cursorLocation.addOffset(-1,0,0);
        }
        if (controller.getToggleEastButton()) {
            cursorLocation.addOffset(1,0,0);
        }
        if (controller.getToggleRightBumperButton()) {
            cursorLocation.addOffset(0,1,0);
        }
        if (controller.getToggleRightTrigger()) {
            if(cursorLocation.getY() != 0) {
                cursorLocation.addOffset(0, -1, 0);
            }
        }
        cursor.setLocation(cursorLocation);

        if (controller.getToggleLeftTrigger()) {
            BlockBase blockAtCursorLocation = world.getBlock(cursor.getLocation());
            Location cursorLocationClone = cursorLocation.clone();
            if (blockAtCursorLocation != null) {
                world.setBlock(cursorLocationClone, null);
            } else {
                if(enableWallMode) {
                    PartialCubicBlock partialCubicBlock = new PartialCubicBlock(cursorLocationClone, instancedGridTexture, "DebugTexture");
                    for(Direction direction: Direction.values()) {
                        partialCubicBlock.setDirectionFaceState(direction,true);
                    }
                    world.setBlock(cursorLocationClone, partialCubicBlock);
                    for(Direction direction: Direction.values())
                    {
                        Location offsetLocation = cursorLocationClone.getDirectionLocation(direction);
                        BlockBase wall = world.getBlock(offsetLocation);
                        if(wall != null && wall instanceof PartialCubicBlock)
                        {
                            if(((PartialCubicBlock) wall).getDirectionFaceState(direction.reverse()))
                            {
                                ((PartialCubicBlock) wall).setDirectionFaceState(direction.reverse(),false);
                                ((PartialCubicBlock)world.getBlock(cursorLocation)).setDirectionFaceState(direction,false);

                                if(wall.isUseless())
                                {
                                    world.setBlock(offsetLocation,null);
                                }
                            }
                        }
                    }
                }
                else {
                    world.setBlock(cursor.getLocation().clone(), new PartialCubicBlock(cursor.getLocation().clone(), instancedGridTexture, "DebugTexture"));
                }
            }
        }

        if (controller.getToggleLeftHomeButton())
        {
            enableWallMode = !enableWallMode;
        }

        if(world.getBlock(cursorLocation) != null) {
            for(Direction direction: Direction.values())
            {
                cursor.setDirectionTexture(direction,0);
            }
        }
        else {
            for(Direction direction: Direction.values())
            {
                cursor.setDirectionTexture(direction,1);
            }
        }

        /*
            Use DirectionPad to change models
         */
    }


    public void editModelControlsLogic()
    {
        cursor.setAboveTexture(controller.getRightBumperButton() ? 2 : 1);
        cursor.setBelowTexture(controller.getRightTrigger() >= 0.95 ? 2 : 1);
        cursor.setNorthTexture(controller.getNorthButton() ? 2 : 1);
        cursor.setEastTexture(controller.getEastButton() ? 2 : 1);
        cursor.setSouthTexture(controller.getSouthButton() ? 2 : 1);
        cursor.setWestTexture(controller.getWestButton() ? 2 : 1);


        if(controller.getToggleLeftTrigger()) {

            int index = 0;
            for(int texture: cursor.getAssignedTextures())
            {
                if(texture == 2)
                {
                    BlockBase block = world.getBlock(cursorLocation);
                    if(block != null && block instanceof PartialCubicBlock)
                    {
                        ((PartialCubicBlock) block).getFaceStates()[index] = !((PartialCubicBlock) block).getFaceStates()[index];
                    }
                }
                index++;
            }
        }

    }




    @Override
    public void render() {
        renderer.prepRender(globalGameData.getGameWindow());

        renderBuffer.bindToRender();
        renderer.clear();
        renderer.renderInstancedPartialCubicChunk(globalGameData.getGameWindow(),"Instance3D", camera2, instancedMesh, world.getChunks());
        renderBuffer.unbindToRender(globalGameData.getGameWindow().getWidth(),globalGameData.getGameWindow().getHeight());


        renderer.render3DElements(globalGameData.getGameWindow(),"FlipDefault3D",camera,gameItems);
        renderer.renderInstancedPartialCubicChunk(globalGameData.getGameWindow(),"Instance3D", camera, instancedMesh, world.getChunks());
        renderer.renderHUD(globalGameData.getGameWindow(),"Default2D",hudItems);

        ArrayList<PartialCubicBlock> cc = new ArrayList<>();
        cc.add(cursor);
        renderer.renderInstancedPartialCubic(globalGameData.getGameWindow(),"Instance3D", camera, cursorInstancedMesh, cc);


    }


    @Override
    public void enterState(GameStateType previousGameState) {
        globalGameData.getGameWindow().setIcon(AssetLoader.getInputStream("Icon16x16.png"), AssetLoader.getInputStream("Icon24x24.png"));
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }








}
