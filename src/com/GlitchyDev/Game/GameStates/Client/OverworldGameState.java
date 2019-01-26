package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.Abstract.EnvironmentGameState;
import com.GlitchyDev.Game.GameStates.Abstract.GameStateBase;
import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.GameInput.GameController;
import com.GlitchyDev.GameInput.GameControllerManager;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.InstancedGridTexture;
import com.GlitchyDev.Rendering.Assets.PartialCubicInstanceMesh;
import com.GlitchyDev.Rendering.Assets.WorldElements.Camera;
import com.GlitchyDev.Utility.GlobalGameData;
import com.GlitchyDev.World.Entities.Player_LN;
import com.GlitchyDev.World.World;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class OverworldGameState extends EnvironmentGameState {
    private World world;
    private Player_LN player;
    private MapBuilderGameState.DebugCommandRunnable debugCommandRunnable;

    private Camera camera;
    private GameController gameController;
    private PartialCubicInstanceMesh instancedMesh;
    private InstancedGridTexture instancedGridTexture;

    public OverworldGameState(GlobalGameData globalGameDataBase, World world, Player_LN player_ln, MapBuilderGameState.DebugCommandRunnable debugCommandRunnable, GameController gameController) {
        super(globalGameDataBase, GameStateType.OVERWORLD);

        camera = new Camera();
        camera.setPosition(0,10,10);
        camera.setRotation(45,0, 0);



        instancedGridTexture = AssetLoader.getInstanceGridTexture("UVMapCubeTexture");
        instancedMesh = new PartialCubicInstanceMesh(AssetLoader.loadMesh("/Mesh/PartialCubicBlock/CubicMesh1.obj"),60*60, instancedGridTexture);

        this.world = world;
        this.player = player_ln;
        this.debugCommandRunnable = debugCommandRunnable;
        this.gameController = gameController;


    }


    @Override
    public void init() {

    }

    @Override
    public void logic() {
        camera.updateViewMatrix();
        cameraControlsLogic();




        ArrayList<String> commands = debugCommandRunnable.getCommands();
        if(commands.size() != 0) {
            for(String command: commands) {
                String[] parsedCommand = command.split(" ");
                String[] args = new String[parsedCommand.length-1];
                int index = 0;
                for(String arg: parsedCommand) {
                    if(index != 0) {
                        args[index-1] = arg;
                    }
                    index++;
                }
                debugCommands(parsedCommand[0],args);
            }
        }
    }

    private void cameraControlsLogic() {
        final float CAMERA_MOVEMENT_AMOUNT = 0.3f;
        final float CAMERA_ROTATION_AMOUNT = 3.0f;
        final float JOYSTICK_THRESHOLD = 0.2f;

        if(gameController != null && gameController.isCurrentlyActive()) {
            if (!gameController.getLeftJoyStickButton()) {
                if (gameController.getLeftJoyStickY() < -JOYSTICK_THRESHOLD) {
                    camera.moveForward(gameController.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (gameController.getLeftJoyStickY() > JOYSTICK_THRESHOLD) {
                    camera.moveBackwards(gameController.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (gameController.getLeftJoyStickX() > JOYSTICK_THRESHOLD) {
                    camera.moveRight(gameController.getLeftJoyStickX() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (gameController.getLeftJoyStickX() < -JOYSTICK_THRESHOLD) {
                    camera.moveLeft(gameController.getLeftJoyStickX() * CAMERA_MOVEMENT_AMOUNT);
                }
            } else {
                if (gameController.getLeftJoyStickY() > JOYSTICK_THRESHOLD) {
                    camera.moveDown(gameController.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }
                if (gameController.getLeftJoyStickY() < -JOYSTICK_THRESHOLD) {
                    camera.moveUp(gameController.getLeftJoyStickY() * CAMERA_MOVEMENT_AMOUNT);
                }

            }

            if (gameController.getRightJoyStickX() > JOYSTICK_THRESHOLD || gameController.getRightJoyStickX() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(0, gameController.getRightJoyStickX() * CAMERA_ROTATION_AMOUNT, 0);
            }
            if (gameController.getRightJoyStickY() > JOYSTICK_THRESHOLD || gameController.getRightJoyStickY() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(gameController.getRightJoyStickY() * CAMERA_ROTATION_AMOUNT, 0, 0);

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





            if (gameController.getRightJoyStickX() > JOYSTICK_THRESHOLD || gameController.getRightJoyStickX() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(0, gameController.getRightJoyStickX() * CAMERA_ROTATION_AMOUNT, 0);
            }
            if (gameController.getRightJoyStickY() > JOYSTICK_THRESHOLD || gameController.getRightJoyStickY() < -JOYSTICK_THRESHOLD) {
                camera.moveRotation(gameController.getRightJoyStickY() * CAMERA_ROTATION_AMOUNT, 0, 0);

            }


        }
    }


    public void debugCommands(String command, String[] args) {
        System.out.println("Recieved Command " + command);
        switch(command.toUpperCase()) {
            case "OVERWORLD":
                globalGameData.switchGameState(GameStateType.OVERWORLD);
                break;
            case "MAPBUILDER":
                globalGameData.switchGameState(GameStateType.MAPBUILDER);
                break;


        }
    }

    @Override
    public void render() {
        renderer.prepRender(globalGameData.getGameWindow());
        renderer.updateFrustumCullingFilter(globalGameData.getGameWindow(),camera,world.getChunks());
        renderer.renderInstancedPartialCubicChunk(globalGameData.getGameWindow(),"Instance3D", camera, instancedMesh, world.getChunks(), true);
    }


    @Override
    public void enterState(GameStateType previousGameState) {

    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {



        System.exit(0);
    }
}
