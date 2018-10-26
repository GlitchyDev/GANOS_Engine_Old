package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.InputGameStateBase;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Networking.GameSocket;
import com.GlitchyDev.Networking.Packets.ClientPackets.ClientIntroductionPacket;
import com.GlitchyDev.Networking.Packets.General.DebugScrollPacket;
import com.GlitchyDev.Networking.Packets.NetworkDisconnectType;
import com.GlitchyDev.Networking.Packets.Packet;
import com.GlitchyDev.Networking.Packets.PacketType;
import com.GlitchyDev.Rendering.Assets.*;
import com.GlitchyDev.Rendering.WorldElements.Camera;
import com.GlitchyDev.Rendering.WorldElements.GameItem;
import com.GlitchyDev.Rendering.WorldElements.SpriteItem;
import com.GlitchyDev.Rendering.WorldElements.TextItem;
import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalGameData;
import org.joml.*;

import java.awt.*;
import java.io.IOException;
import java.lang.Math;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;


public class DebugGameState1 extends InputGameStateBase {
    // required for each gamestate
    private Camera camera;
    private GameItem[] gameItems;
    private TextItem[] hudItems;
    private SpriteItem[] spriteItems;
    private GameSocket gameSocket;

    private RenderBuffer debugBuffer;
    private SpriteItem debugItem;

    private Mesh mesh1;
    private Mesh mesh2;

    public DebugGameState1(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.DEBUG_1);
        init();

    }


    @Override
    public void init() {
        camera = new Camera();

        mesh1 = AssetLoader.getMeshAsset("untitled");
        mesh2 = AssetLoader.loadCacheMesh("untitled.obj");

        mesh1.setTexture(AssetLoader.getTextureAsset("grassblock"));
        mesh2.setTexture(AssetLoader.getTextureAsset("testTexture"));



        int width = 2;
        int height = 2;
        int length = 1;
        gameItems = new GameItem[width * height * length];


        int ii = 0;
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                for(int z = 0; z < length; z++)
                {
                    GameItem gameItem;
                    if(Math.random() > 0.5) {
                        gameItem = new GameItem(mesh1);
                    }
                    else
                    {
                        gameItem = new GameItem(mesh2);
                    }
                    gameItem.setScale(0.5f);
                    gameItem.setPosition(x,y,z);
                    gameItems[ii] = gameItem;
                    ii++;
                }
            }
        }






        final Font FONT = new Font("Greek", Font.PLAIN, 20);
        String CHARSET = "ISO-8859-1";

        FontTexture fontTexture = new FontTexture(FONT,CHARSET);
        CustomFontTexture customTexture = new CustomFontTexture("DebugFont");
        TextItem item = new TextItem("",fontTexture);
        TextItem item2 = new TextItem("Robert_Louis_Hannah",customTexture);
        item2.setPosition(0,100,0);
        hudItems = new TextItem[]{item,item2};

        spriteItems = new SpriteItem[1];
        for(int i = 0; i < spriteItems.length; i++) {
            spriteItems[i] = new SpriteItem(AssetLoader.getTextureAsset("Tomo"),true);
            spriteItems[i].setPosition(0, 0, (float) (0.000001 * i));
            spriteItems[i].setScale(1.0f);
        }



        debugBuffer = new RenderBuffer(500,500);
        debugItem = new SpriteItem(debugBuffer);



        debugItem.setPosition(0,0,0);

    }

    /*
            debugBuffer.bindToRender();
        renderer.prepRender(globalGameData.getGameWindow());
        renderer.render3DElements(globalGameData.getGameWindow(), "Default3D", camera, gameItems);
        renderer.renderSprites(globalGameData.getGameWindow(), "Default2D", spriteItems);
        debugBuffer.unbindToRender(globalGameData.getGameWindow().getWidth(),globalGameData.getGameWindow().getHeight());

        renderer.prepRender(globalGameData.getGameWindow());
        renderer.renderHUD(globalGameData.getGameWindow(), "Default2D", hudItems);
        renderer.renderSprites(globalGameData.getGameWindow(),"Default2D",new SpriteItem[]{debugItem});
     */
    @Override
    public void render() {


        renderer.prepRender(globalGameData.getGameWindow());
        renderer.render3DElements(globalGameData.getGameWindow(), "Default3D", camera, gameItems);
        //renderer.renderSprites(globalGameData.getGameWindow(), "Default2D", spriteItems);
        renderer.renderHUD(globalGameData.getGameWindow(), "Default2D", hudItems);


    }

    @Override
    public void logic() {


        hudItems[1].setText("FPS:_" + getCurrentFPS() + "_COUNT_" + getFPSCount() + "_A_" + (getRenderUtilization() + getLogicUtilization()));
        hudItems[0].setText("" + camera.getRotation().y + " " + Math.sin(camera.getRotation().y/180*Math.PI));


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

        /*
        GameItem gameItem1 = selectGameItem(gameItems,camera);
        GameItem gameItem2 = selectGameItem2D(gameItems,globalGameData.getGameWindow(),new Vector2d(gameInput.getMouseX(),gameInput.getMouseY()),camera);

        for(GameItem gameItem: gameItems)
        {
            gameItem.setMesh(mesh1);
        }
        if(gameItem1 != null)
        {
            gameItem1.setMesh(mesh2);
        }
        if(gameItem2 != null)
        {
            gameItem2.setMesh(mesh2);
        }
        */


    }



    Vector3f dir = new Vector3f();
    Vector3f max = new Vector3f();
    Vector3f min = new Vector3f();
    Vector2f nearFar = new Vector2f();
    public GameItem selectGameItem(GameItem[] gameItems, Camera camera) {
        dir = camera.getViewMatrix().positiveZ(dir).negate();
        return selectGameItem(gameItems, camera.getPosition(), dir);
    }

    protected GameItem selectGameItem(GameItem[] gameItems, Vector3f center, Vector3f dir) {
        GameItem selectedGameItem = null;
        float closestDistance = Float.POSITIVE_INFINITY;

        for (GameItem gameItem : gameItems) {
            min.set(gameItem.getPosition());
            max.set(gameItem.getPosition());
            min.add(-gameItem.getScale(), -gameItem.getScale(), -gameItem.getScale());
            max.add(gameItem.getScale(), gameItem.getScale(), gameItem.getScale());
            if (Intersectionf.intersectRayAab(center, dir, min, max, nearFar) && nearFar.x < closestDistance) {
                closestDistance = nearFar.x;
                selectedGameItem = gameItem;
            }
        }

        return selectedGameItem;
    }


    Matrix4f invProjectionMatrix = new Matrix4f();
    Matrix4f invViewMatrix = new Matrix4f();
    Vector3f mouseDir = new Vector3f();
    Vector4f tmpVec = new Vector4f();
    public GameItem selectGameItem2D(GameItem[] gameItems, GameWindow window, Vector2d mousePos, Camera camera) {
        // Transform mouse coordinates into normalized spaze [-1, 1]
        int wdwWitdh = window.getWidth();
        int wdwHeight = window.getHeight();

        float x = (float)(2 * mousePos.x) / (float)wdwWitdh - 1.0f;
        float y = 1.0f - (float)(2 * mousePos.y) / (float)wdwHeight;
        float z = -1.0f;

        invProjectionMatrix.set(window.getProjectionMatrix());
        invProjectionMatrix.invert();

        tmpVec.set(x, y, z, 1.0f);
        tmpVec.mul(invProjectionMatrix);
        tmpVec.z = -1.0f;
        tmpVec.w = 0.0f;

        Matrix4f viewMatrix = camera.getViewMatrix();
        invViewMatrix.set(viewMatrix);
        invViewMatrix.invert();
        tmpVec.mul(invViewMatrix);

        mouseDir.set(tmpVec.x, tmpVec.y, tmpVec.z);

        return selectGameItem(gameItems, camera.getPosition(), mouseDir);
    }
    /*
    if (gameInput.getKeyValue(GLFW_KEY_ESCAPE) == 1) {
            globalGameData.getGameWindow().makeWindowClose();
            System.out.println("DebugGameState: CLOSING WINDOW");
        }

        if (gameInput.getKeyValue(GLFW_KEY_0) == 1) {

            if(gameSocket == null)
            {
                try {
                    Socket socket = new Socket("192.168.1.3",8001);
                    gameSocket = new GameSocket(socket);

                    gameSocket.sendPacket(new ClientIntroductionPacket("James"));
                    while(!gameSocket.hasUnprocessedPackets())
                    {
                        Thread.yield();
                    }
                    Packet packet = gameSocket.getUnprocessedPackets().get(0);
                    if(packet.getPacketType() == PacketType.A_GOODBYE)
                    {
                        gameSocket = null;
                    }
                    else
                    {
                        globalGameData.getGameWindow().setClearColor(1.0f,0.0f,0.0f,1.0f);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }






        if(gameInputTimings.getActiveMouseButton1Time() != 0) {
            hudItems[0].setText("" + gameInputTimings.getActiveMouseButton1Time());

            if(gameInputTimings.getActiveMouseButton1Time() > 60)
            {
                globalGameData.getGameWindow().makeWindowClose();
            }
        }


        spriteItems[0].getPosition().y += gameInputTimings.getActiveScroll() * 10;
        if(gameSocket != null) {
            gameSocket.sendPacket(new DebugScrollPacket(gameInputTimings.getActiveScroll() * 10));
        }
     */

    @Override
    public void enterState(GameStateType previousGameState) {
        super.enterState(previousGameState);
        globalGameData.getGameWindow().setCursor(AssetLoader.getInputStream("Icon16x16.png"),0,0);
        globalGameData.getGameWindow().setIcon(getWindowHandle(),AssetLoader.getInputStream("Icon16x16.png"),AssetLoader.getInputStream("Icon32x32.png"));
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {
        if(gameSocket != null)
        {
            gameSocket.disconnect(NetworkDisconnectType.WINDOW_CLOSED);
        }
        renderer.cleanup();

    }
}
