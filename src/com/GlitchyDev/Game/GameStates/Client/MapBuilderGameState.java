package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.Abstract.EnvironmentGameState;
import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.GameInput.ControllerDirectionPad;
import com.GlitchyDev.GameInput.GameController;
import com.GlitchyDev.GameInput.PS4Controller;
import com.GlitchyDev.GameInput.XBox360Controller;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.*;
import com.GlitchyDev.Rendering.Assets.Fonts.CustomFontTexture;
import com.GlitchyDev.Rendering.Assets.Sounds.SoundSource;
import com.GlitchyDev.Rendering.Assets.WorldElements.Camera;
import com.GlitchyDev.Rendering.Assets.WorldElements.GameItem;
import com.GlitchyDev.Rendering.Assets.WorldElements.SpriteItem;
import com.GlitchyDev.Rendering.Assets.WorldElements.TextItem;
import com.GlitchyDev.Utility.GlobalGameData;
import com.GlitchyDev.World.*;
import com.GlitchyDev.World.Blocks.Abstract.BlockBase;
import com.GlitchyDev.World.Blocks.Abstract.PartialCubicBlock;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

/**
 * A gamestate designed to be the main form of building Map files in the game, as well as debugging functions
 *
 * Potentially, one should be able to use this gamestate to edit a live game owo
 *
 * Features should include
 * - Creating Map Files
 * - Loading Map Files
 * - Editing Map Files
 * - Saving Map Files
 *
 * Wanted Features:
 * - Make Selection of block faces based on rotation, no more guessing owo
 *
 */
public class MapBuilderGameState extends EnvironmentGameState {
    // Show off the camera switching feature
    private Camera camera;
    private Camera camera2;
    // Used to render Camera 2 to texture for viewing ( Camera screens, mirrors, ect )
    private Texture renderTexture;
    private RenderBuffer renderBuffer;
    // Game Items held
    private ArrayList<GameItem> gameItems = new ArrayList<>();
    private ArrayList<TextItem> hudItems = new ArrayList<>();
    private ArrayList<SpriteItem> spriteItems = new ArrayList<>();
    private ArrayList<GameItem> debugItems = new ArrayList<>();
    private PartialCubicBlock cursor;

    // Init'd controller 1
    private GameController controller;
    // Mesh for PartialCubicBlocks
    private PartialCubicInstanceMesh instancedMesh;
    private InstancedGridTexture instancedGridTexture;
    private PartialCubicInstanceMesh cursorInstancedMesh;

    // Loaded World
    private World world;

    // Configs of textures for texture selection menu
    private final int TEXTURE_SCALING = 1;
    private final int XOFFSET = 0;
    private final int YOFFSET = 130;

    // Loaded Texture Selections
    private ArrayList<InstancedGridTexture> availableInstanceTextures = new ArrayList<>();
    private int selectedTexturePackId = 0;
    private int selectedTextureId = 0;
    private int textureCursorX = 0;
    private int textureCursorY = 0;

    private SoundSource backgroundMusic;

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
        gameItems.get(0).setPosition(0,0,0);
        gameItems.get(0).setRotation(0,0,0);
        gameItems.get(0).setScale(1);

        gameItems.add(new GameItem(instanceMesh));
        gameItems.get(1).setPosition(0,0,0);
        gameItems.get(1).setRotation(0,0,0);
        gameItems.get(1).setScale(1.0f);

        gameItems.add(new GameItem(instanceMesh));
        gameItems.get(2).setPosition(0,0,0);
        gameItems.get(2).setRotation(0,0,90);
        gameItems.get(2).setScale(1.0f);

        gameItems.add(new GameItem(instanceMesh));
        gameItems.get(3).setPosition(0,0,0);
        gameItems.get(3).setRotation(0,0,180);
        gameItems.get(3).setScale(1.0f);



        CustomFontTexture customTexture = new CustomFontTexture("DebugFont");

        final int NUM_TEX_ITEMS = 16;
        for(int i = 0; i < NUM_TEX_ITEMS; i++) {
            TextItem item = new TextItem("",customTexture);
            item.setPosition(55*3,i*12,0);
            hudItems.add(item);
        }


        SpriteItem item = new SpriteItem(AssetLoader.getTextureAsset("Standing_Mirror"),true);
        item.setPosition(0,2,0);

        SpriteItem item2 = new SpriteItem(AssetLoader.getTextureAsset("Standing_Mirror"),true);
        item2.setPosition(0,2,0);
        item2.setRotation(0, 90,0);

        SpriteItem item3 = new SpriteItem(AssetLoader.getTextureAsset("Standing_Mirror"),true);
        item3.setPosition(0,2,0);
        item3.setRotation(0, 180,0);

        SpriteItem item4 = new SpriteItem(AssetLoader.getTextureAsset("Standing_Mirror"),true);
        item4.setPosition(0,2,0);
        item4.setRotation(0,270,0);


        debugItems.add(item);
        debugItems.add(item2);
        debugItems.add(item3);
        debugItems.add(item4);






        boolean[] defaultOrientation = new boolean[]{true,true,true,true,true,true};
        //int[] defaultTextureMapping = new int[]{0,1,2,3,4,5};


        // REGISTER THESE GOD DAMN GRID TEXTURES AS ASSETS
        AssetLoader.registerInstanceGridTexture(AssetLoader.getTextureAsset("EditCursor"),"EditCursor",2,3);
        InstancedGridTexture cursorGridTexture = AssetLoader.getInstanceGridTexture("EditCursor");

        AssetLoader.registerInstanceGridTexture(AssetLoader.getTextureAsset("UVMapCubeTexture"),"UVMapCubeTexture",2,3);
        instancedGridTexture = AssetLoader.getInstanceGridTexture("UVMapCubeTexture");

        cursorInstancedMesh = new PartialCubicInstanceMesh(AssetLoader.getMeshAsset("CubicMesh1"),3600, cursorGridTexture);
        instancedMesh = new PartialCubicInstanceMesh(AssetLoader.loadMesh("/Mesh/PartialCubicBlock/CubicMesh1.obj"),60*60, instancedGridTexture);



        availableInstanceTextures.add(instancedGridTexture);

        AssetLoader.registerInstanceGridTexture(AssetLoader.getTextureAsset("TestDice"),"TestDice",6,7);
        availableInstanceTextures.add(AssetLoader.getInstanceGridTexture("TestDice"));

        AssetLoader.registerInstanceGridTexture(AssetLoader.getTextureAsset("SchoolTiles"),"SchoolTiles",2,4);
        availableInstanceTextures.add(AssetLoader.getInstanceGridTexture("SchoolTiles"));

        AssetLoader.registerInstanceGridTexture(AssetLoader.getTextureAsset("School_Tiles"),"School_Tiles",7,6);
        availableInstanceTextures.add(AssetLoader.getInstanceGridTexture("School_Tiles"));



        SpriteItem spriteItem1 = new SpriteItem(availableInstanceTextures.get(0),true);
        spriteItem1.setScale(TEXTURE_SCALING);
        spriteItem1.setPosition(XOFFSET,YOFFSET,0);
        SpriteItem spriteItem2 = new SpriteItem(AssetLoader.getTextureAsset("Cursor"), availableInstanceTextures.get(0).getCubeSideLength() ,availableInstanceTextures.get(0).getCubeSideLength(), true);
        spriteItem2.setScale(TEXTURE_SCALING);
        spriteItem2.setPosition(XOFFSET,YOFFSET,1);

        SpriteItem spriteItem3 = new SpriteItem(AssetLoader.getTextureAsset("Selector"),true);
        spriteItem3.setScale(1);
        spriteItem3.setPosition(globalGameData.getGameWindow().getWidth()/2 - 25, globalGameData.getGameWindow().getHeight()/2 - 25,1);

        spriteItems.add(spriteItem1);
        spriteItems.add(spriteItem2);
        spriteItems.add(spriteItem3);

        int[] zeroTextureMap = new int[]{0,0,0,0,0,0};
        cursor = new PartialCubicBlock(new Location(0,0,0,world), cursorGridTexture,defaultOrientation,zeroTextureMap);
        cursor.setScale(1.4f);

        int width = 60;
        int height = 1;
        int length = 60;

       // world = new World("DebugWorld");



        world = new World("DebugWorld");
        ArrayList<Integer> textureCode = new ArrayList<>();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                for(int z = 0; z < length; z++) {
                    Location location = new Location(x,y,z,world);
                    boolean[] faceStates = new boolean[6];
                    textureCode.clear();
                    faceStates[0] = true;
                    for(int i = 0; i < 6; i++) {
                        textureCode.add(i);
                    }
                    int[] a = new int[textureCode.size()];
                    for(int b = 0; b < a.length; b++) {
                        a[b] = textureCode.get(b);
                    }
                    PartialCubicBlock b = new PartialCubicBlock(location, instancedGridTexture,faceStates,a);
                    world.setBlock(location,b);

                }
            }
        }

        controller = new PS4Controller(0);


        backgroundMusic = new SoundSource(true,true);





    }






    private final NumberFormat formatter = new DecimalFormat("#0.00");
    @Override
    public void logic() {
        controller.tick();
        camera.updateViewMatrix();
        camera2.updateViewMatrix();

        hudItems.get(1).setText("Render: " + formatter.format(getRenderUtilization()) + " Logic: " + formatter.format(getLogicUtilization()));
        hudItems.get(2).setText("FPS:" + getCurrentFPS());
        hudItems.get(4).setText("Pos:" + formatter.format(camera.getPosition().x) + "," + formatter.format(camera.getPosition().y) + "," + formatter.format(camera.getPosition().z)+ " Rot:" + formatter.format(camera.getRotation().x) + "," + formatter.format(camera.getRotation().y) + "," + formatter.format(camera.getRotation().z));
        hudItems.get(5).setText("Editor Mode: " + currentEditState + " EnableWallMode: " + enableWallMode);
        hudItems.get(6).setText("Cursor Location: " + cursorLocation.getX() + " Y:" + cursorLocation.getY() + " Z:" + cursorLocation.getZ());
        hudItems.get(6).setText("Blocks Rendered: " + instancedMesh.getBlocksRendered() + " Blocks Ignored: " + instancedMesh.getBlocksIgnored());
        if(currentEditState == EditState.EDIT_TEXTURE) {
            hudItems.get(7).setText("TexturePackID: " + selectedTexturePackId + " TextureID: " + selectedTextureId + " Cursor " + textureCursorX + "," + textureCursorY);
        }
        else {
            hudItems.get(7).setText("");
        }


        cameraControlsLogic();
        if(controller.isCurrentlyActive()) {
            editControlsLogic();
        }


        if(controller.getLeftHomeButton()) {
            if(controller.getToggleLeftBumperButton()) {
                System.out.println("WRITING TO FILE");
                File file = new File("C:/Users/Robert/Desktop/TestWorld_0_0.wcnk");
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                    Chunk chunk = world.getChunk(new ChunkCord(0, 0));
                    chunk.writeObject(oos);
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(controller.getToggleRightBumperButton()) {
                System.out.println("READING FROM FILE");
                File file = new File("C:/Users/Robert/Desktop/TestWorld_0_0.wcnk");
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    Chunk chunk = world.getChunk(new ChunkCord(0, 0));
                    chunk.readObject(in, new Location(world));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        ArrayList<BlockBase> aaa = new ArrayList<>();
        BlockBase blocks[][][] = world.getChunk(new ChunkCord(0,0)).getBlocks();

        for(BlockBase[][] b : blocks) {
            for(BlockBase[] bb : b) {
                for(BlockBase bbb : bb) {
                    if(bbb != null) {
                        aaa.add(bbb);
                    }
                }
            }
        }


        BlockBase b = selectBlock3D(aaa,camera);
        if(b != null) {
            if(b instanceof PartialCubicBlock) {
                ((PartialCubicBlock) b).setInstancedGridTexture(availableInstanceTextures.get(2));
            }
        }
        //logicCamera();


        
    }





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

    private void cameraControlsLogic() {
        final float CAMERA_MOVEMENT_AMOUNT = 0.3f;
        final float CAMERA_ROTATION_AMOUNT = 3.0f;
        final float JOYSTICK_THRESHOLD = 0.2f;

        if(controller.isCurrentlyActive()) {
            if (!controller.getLeftJoyStickButton()) {
                if (controller.getLeftJoyStickY() < -JOYSTICK_THRESHOLD) {
                    camera.moveForward(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (controller.getLeftJoyStickY() > JOYSTICK_THRESHOLD) {
                    camera.moveBackwards(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (controller.getLeftJoyStickX() > JOYSTICK_THRESHOLD) {
                    camera.moveRight(controller.getLeftJoyStickX() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (controller.getLeftJoyStickX() < -JOYSTICK_THRESHOLD) {
                    camera.moveLeft(controller.getLeftJoyStickX() * CAMERA_MOVEMENT_AMOUNT);
                }
            } else {
                if (controller.getLeftJoyStickY() > JOYSTICK_THRESHOLD) {
                    camera.moveDown(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (controller.getLeftJoyStickY() < -JOYSTICK_THRESHOLD) {
                    camera.moveUp(controller.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }

            }

            if (controller.getRightJoyStickX() > JOYSTICK_THRESHOLD || controller.getRightJoyStickX() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(0, controller.getRightJoyStickX() * CAMERA_ROTATION_AMOUNT, 0);
            }
            if (controller.getRightJoyStickY() > JOYSTICK_THRESHOLD || controller.getRightJoyStickY() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(controller.getRightJoyStickY() * CAMERA_ROTATION_AMOUNT, 0, 0);

            }

            if (controller.getToggleRightJoyStickButton()) {
                Camera temp = camera;
                camera = camera2;
                camera2 = temp;
            }
        } else {
            if (gameInput.getKeyValue(GLFW_KEY_SPACE) >= 1) {
                camera.moveUp(1);
            }
            if (gameInput.getKeyValue(GLFW_KEY_LEFT_SHIFT) >= 1) {
                camera.moveDown(1);
            }

            if (gameInput.getKeyValue(GLFW_KEY_UP) >= 1) {
                camera.moveForward(1);
            }
            if (gameInput.getKeyValue(GLFW_KEY_DOWN) >= 1) {
                camera.moveBackwards(1);
            }
            if (gameInput.getKeyValue(GLFW_KEY_LEFT) >= 1) {
                camera.moveLeft(1);
            }
            if (gameInput.getKeyValue(GLFW_KEY_RIGHT) >= 1) {
                camera.moveRight(1);
            }


            if (gameInput.getKeyValue(GLFW_KEY_W) >= 1) {
                camera.moveRotation(-1,0,0);
            }
            if (gameInput.getKeyValue(GLFW_KEY_S) >= 1) {
                camera.moveRotation(1,0,0);
            }
            if (gameInput.getKeyValue(GLFW_KEY_D) >= 1) {
                camera.moveRotation(0,1,0);
            }
            if (gameInput.getKeyValue(GLFW_KEY_A) >= 1) {
                camera.moveRotation(0,-1,0);
            }





            if (controller.getRightJoyStickX() > JOYSTICK_THRESHOLD || controller.getRightJoyStickX() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(0, controller.getRightJoyStickX() * CAMERA_ROTATION_AMOUNT, 0);
            }
            if (controller.getRightJoyStickY() > JOYSTICK_THRESHOLD || controller.getRightJoyStickY() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(controller.getRightJoyStickY() * CAMERA_ROTATION_AMOUNT, 0, 0);

            }

            if (gameInput.getKeyValue(GLFW_KEY_TAB) >= 1) {
                Camera temp = camera;
                camera = camera2;
                camera2 = temp;
            }
        }
    }

    public enum EditState {
        MOVE_CURSOR, // Move Cursor, Delete whole Tiles, Change model
        EDIT_TEXTURE, // Select Texture Sheet, Bind Textures, edit Mesh
        ;
        public EditState toggleState() {
            switch(this) {
                case MOVE_CURSOR:
                    return EDIT_TEXTURE;
                case EDIT_TEXTURE:
                    return MOVE_CURSOR;
            }
            return MOVE_CURSOR;
        }
    }

    private EditState currentEditState = EditState.MOVE_CURSOR;
    private void editControlsLogic()
    {
        if(controller.getToggleRightHomeButton()) {
            currentEditState = currentEditState.toggleState();
            switch(currentEditState) {

                case MOVE_CURSOR:
                    for(Direction direction: Direction.values()) {
                        cursor.setDirectionTexture(direction,0);
                    }
                    break;
                case EDIT_TEXTURE:
                    for(Direction direction: Direction.values()) {
                        cursor.setDirectionTexture(direction,2);
                    }
                    break;
            }

        }
        switch(currentEditState)
        {

            case MOVE_CURSOR:
                moveCursorControlsLogic();
                break;
            case EDIT_TEXTURE:
                editTextureControlsLogic();
                break;
        }
    }



    // BLECK!

    private Location cursorLocation = new Location(0,0,0,null);
    private boolean enableWallMode = true;
    private void moveCursorControlsLogic() {
        if (controller.getToggleNorthButton()) {
            cursorLocation = cursorLocation.getOffsetLocation(0,0,-1);
        }
        if (controller.getToggleSouthButton()) {
            cursorLocation = cursorLocation.getOffsetLocation(0,0,1);
        }
        if (controller.getToggleWestButton()) {
            cursorLocation = cursorLocation.getOffsetLocation(-1,0,0);
        }
        if (controller.getToggleEastButton()) {
            cursorLocation = cursorLocation.getOffsetLocation(1,0,0);
        }
        if (controller.getToggleRightBumperButton()) {
            cursorLocation = cursorLocation.getOffsetLocation(0,1,0);
        }
        if (controller.getToggleRightTrigger()) {
            if(cursorLocation.getY() != 0) {
                cursorLocation = cursorLocation.getOffsetLocation(0, -1, 0);
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
                    PartialCubicBlock partialCubicBlock = new PartialCubicBlock(cursorLocationClone, instancedGridTexture);
                    for(Direction direction: Direction.values()) {
                        partialCubicBlock.setDirectionFaceState(direction,true);
                    }
                    // Adds block to world
                    world.setBlock(cursorLocationClone, partialCubicBlock);
                    for(Direction direction: Direction.values()) {
                        Location offsetLocation = cursorLocationClone.getDirectionLocation(direction);
                        BlockBase wall = world.getBlock(offsetLocation);
                        if(wall instanceof PartialCubicBlock) {
                            if((partialCubicBlock).getDirectionFaceState(direction)) {

                                ((PartialCubicBlock) wall).setDirectionFaceState(direction.reverse(),false);
                                ((PartialCubicBlock)world.getBlock(cursorLocation)).setDirectionFaceState(direction,false);

                                if(wall.isUseless()) {
                                    world.setBlock(offsetLocation,null);
                                }
                            }
                        }
                    }
                }
                else {
                    world.setBlock(cursorLocationClone, new PartialCubicBlock(cursorLocationClone, instancedGridTexture));
                }
            }
        }
        if (controller.getToggleLeftBumperButton()) {
            enableWallMode = !enableWallMode;
        }
        if(world.getBlock(cursorLocation) instanceof PartialCubicBlock) {
            for(Direction direction: Direction.values()) {
                cursor.setDirectionTexture(direction,0);
            }
        }
        else {
            for(Direction direction: Direction.values()) {
                cursor.setDirectionTexture(direction,1);
            }
        }

        /*
            Use DirectionPad to change models
         */
    }





    private void editTextureControlsLogic() {
        cursor.setAboveTexture(controller.getRightBumperButton() ? 3 : 2);
        cursor.setBelowTexture(controller.getRightTrigger() >= 0.95 ? 3 : 2);
        cursor.setNorthTexture(controller.getNorthButton() ? 3 : 2);
        cursor.setEastTexture(controller.getEastButton() ? 3 : 2);
        cursor.setSouthTexture(controller.getSouthButton() ? 3 : 2);
        cursor.setWestTexture(controller.getWestButton() ? 3 : 2);

        if(controller.getToggleLeftTrigger()) {
            BlockBase block = world.getBlock(cursorLocation);
            int index = 0;
            for(int texture: cursor.getAssignedTextures()) {
                if(texture == 3) {
                    if(block instanceof PartialCubicBlock) {
                        ((PartialCubicBlock) block).getFaceStates()[index] = !((PartialCubicBlock) block).getFaceStates()[index];
                    }
                }
                index++;
            }
            if(block != null && block.isUseless()) {
                world.setBlock(cursorLocation,null);
            }
        }

        if(controller.getToggleDirectionPad() != ControllerDirectionPad.NONE) {
            switch(controller.getDirectionPad()) {
                case NORTH:
                    selectedTextureId = (selectedTextureId == 0) ? (availableInstanceTextures.get(selectedTexturePackId).getTextureGridWidth() * availableInstanceTextures.get(selectedTexturePackId).getTextureGridHeight())-1 : selectedTextureId-1;
                    textureCursorX = selectedTextureId % availableInstanceTextures.get(selectedTexturePackId).getTextureGridWidth();
                    textureCursorY = selectedTextureId / availableInstanceTextures.get(selectedTexturePackId).getTextureGridWidth();
                    setCursorPosition(textureCursorX,textureCursorY);
                    break;
                case EAST:
                    selectedTexturePackId= (selectedTexturePackId == availableInstanceTextures.size()-1) ? 0 : selectedTexturePackId+1;
                    loadTexturePack();
                    selectedTextureId = 0;
                    break;
                case SOUTH:
                    selectedTextureId = (selectedTextureId >= (availableInstanceTextures.get(selectedTexturePackId).getTextureGridWidth() * availableInstanceTextures.get(selectedTexturePackId).getTextureGridHeight())-1) ? 0 : selectedTextureId+1;
                    textureCursorX = selectedTextureId % availableInstanceTextures.get(selectedTexturePackId).getTextureGridWidth();
                    textureCursorY = selectedTextureId / availableInstanceTextures.get(selectedTexturePackId).getTextureGridWidth();
                    setCursorPosition(textureCursorX,textureCursorY);
                    break;
                case WEST:
                    selectedTexturePackId = (selectedTexturePackId == 0) ? availableInstanceTextures.size()-1 : selectedTexturePackId-1;
                    loadTexturePack();
                    selectedTextureId = 0;
                    break;
            }
        }

        if(controller.getToggleLeftBumperButton()) {
            if (world.getBlock(cursorLocation) instanceof PartialCubicBlock) {
                PartialCubicBlock block = (PartialCubicBlock) world.getBlock(cursorLocation);
                InstancedGridTexture blockTexture = block.getInstancedGridTexture();

                if (controller.getNorthButton()) {
                    if(availableInstanceTextures.get(selectedTexturePackId) != blockTexture) {
                        block.setInstancedGridTexture(availableInstanceTextures.get(selectedTexturePackId));
                        for(Direction direction: Direction.values()) {
                            block.setDirectionTexture(direction,0);
                        }
                    }
                    block.setDirectionTexture(Direction.NORTH,selectedTextureId);
                }
                if (controller.getSouthButton()) {
                    if(availableInstanceTextures.get(selectedTexturePackId) != blockTexture) {
                        block.setInstancedGridTexture(availableInstanceTextures.get(selectedTexturePackId));
                        for(Direction direction: Direction.values()) {
                            block.setDirectionTexture(direction,0);
                        }
                    }
                    block.setDirectionTexture(Direction.SOUTH,selectedTextureId);
                }
                if (controller.getEastButton()) {
                    if(availableInstanceTextures.get(selectedTexturePackId) != blockTexture) {
                        block.setInstancedGridTexture(availableInstanceTextures.get(selectedTexturePackId));
                        for(Direction direction: Direction.values()) {
                            block.setDirectionTexture(direction,0);
                        }
                    }
                    block.setDirectionTexture(Direction.EAST,selectedTextureId);
                }
                if (controller.getWestButton()) {
                    if(availableInstanceTextures.get(selectedTexturePackId) != blockTexture) {
                        block.setInstancedGridTexture(availableInstanceTextures.get(selectedTexturePackId));
                        for(Direction direction: Direction.values()) {
                            block.setDirectionTexture(direction,0);
                        }
                    }
                    block.setDirectionTexture(Direction.WEST,selectedTextureId);
                }
                if (controller.getRightBumperButton()) {
                    if(availableInstanceTextures.get(selectedTexturePackId) != blockTexture) {
                        block.setInstancedGridTexture(availableInstanceTextures.get(selectedTexturePackId));
                        for(Direction direction: Direction.values()) {
                            block.setDirectionTexture(direction,0);
                        }
                    }
                    block.setDirectionTexture(Direction.ABOVE,selectedTextureId);
                }
                if (controller.getRightTrigger() >= 0.95) {
                    if(cursorLocation.getY() != 0) {
                        if(availableInstanceTextures.get(selectedTexturePackId) != blockTexture) {
                            block.setInstancedGridTexture(availableInstanceTextures.get(selectedTexturePackId));
                            for(Direction direction: Direction.values()) {
                                block.setDirectionTexture(direction,0);
                            }
                        }
                        block.setDirectionTexture(Direction.BELOW,selectedTextureId);
                    }
                }
            }
        }
    }



    private void loadTexturePack() {
        spriteItems.get(0).setSprite(availableInstanceTextures.get(selectedTexturePackId),true);
        spriteItems.get(1).setSprite(AssetLoader.getTextureAsset("Cursor"), availableInstanceTextures.get(selectedTexturePackId).getCubeSideLength() ,availableInstanceTextures.get(selectedTexturePackId).getCubeSideLength(), true);
        textureCursorX = 0;
        textureCursorY = 0;
        setCursorPosition(0,0);
    }

    private void setCursorPosition(int x, int y) {
        spriteItems.get(0).setPosition(XOFFSET,YOFFSET,0);
        spriteItems.get(1).setPosition(XOFFSET + availableInstanceTextures.get(selectedTexturePackId).getCubeSideLength() * x * TEXTURE_SCALING,YOFFSET + availableInstanceTextures.get(selectedTexturePackId).getCubeSideLength() * y * TEXTURE_SCALING,1);
    }
      /*
       int num = ((PartialCubicBlock) block).getAssignedTextures()[i];
       int x = num % instancedGridTexture.getTextureGridWidth();
       int y = num / instancedGridTexture.getTextureGridWidth();
     */



    @Override
    public void render() {
        renderer.prepRender(globalGameData.getGameWindow());

        renderBuffer.bindToRender();
        renderer.clear();
        renderer.updateFrustumCullingFilter(globalGameData.getGameWindow(),camera2,world.getChunks());
        renderer.renderInstancedPartialCubicChunk(globalGameData.getGameWindow(),"Instance3D", camera2, instancedMesh, world.getChunks(), true);
        renderBuffer.unbindToRender(globalGameData.getGameWindow().getWidth(),globalGameData.getGameWindow().getHeight());


        renderer.updateFrustumCullingFilter(globalGameData.getGameWindow(),camera,world.getChunks());
        renderer.render3DElements(globalGameData.getGameWindow(),"FlipDefault3D",camera,gameItems);
        renderer.renderInstancedPartialCubicChunk(globalGameData.getGameWindow(),"Instance3D", camera, instancedMesh, world.getChunks(), true);
        renderer.render3DElements(globalGameData.getGameWindow(),"Glitchy3D",camera, debugItems);
        if(currentEditState == EditState.EDIT_TEXTURE) {
            renderer.renderSprites(globalGameData.getGameWindow(), "Default2D", spriteItems);
        }
        renderer.renderHUD(globalGameData.getGameWindow(),"Default2D",hudItems);

        controller.render(renderer,globalGameData.getGameWindow(),"Default2D");

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
