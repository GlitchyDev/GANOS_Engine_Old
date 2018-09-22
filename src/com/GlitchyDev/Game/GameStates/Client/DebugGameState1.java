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
import com.GlitchyDev.Rendering.Assets.FontTexture;
import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.RenderBuffer;
import com.GlitchyDev.Rendering.Assets.Texture;
import com.GlitchyDev.Rendering.WorldElements.Camera;
import com.GlitchyDev.Rendering.WorldElements.GameItem;
import com.GlitchyDev.Rendering.WorldElements.SpriteItem;
import com.GlitchyDev.Rendering.WorldElements.TextItem;
import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.Utility.GlobalGameData;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;


public class DebugGameState1 extends InputGameStateBase {
    private Renderer renderer;
    private Camera camera;
    private GameItem[] gameItems;
    private TextItem[] hudItems;
    private SpriteItem[] spriteItems;
    private GameStateType gameStateType = GameStateType.DEBUG_1;
    private GameSocket gameSocket;

    private RenderBuffer debugBuffer;
    private SpriteItem debugItem;

    public DebugGameState1(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase);
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


        int length = 1000;
        gameItems = new GameItem[length];
        for(int i = 0; i < length; i++)
        {
            GameItem gameItem = new GameItem(mesh);
            gameItem.setScale(0.5f);
            gameItem.setPosition(i%9-4, (float) (Math.random() * 10)-5, -2 - i/9);
            gameItems[i] = gameItem;


        }





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



        debugBuffer = new RenderBuffer(500,500);
        debugItem = new SpriteItem(debugBuffer);



        debugItem.setPosition(0,10,0);
    }

    @Override
    public void render() {


        if(Math.random() < 0.3) {
            debugBuffer.bindToRender();
            renderer.render(globalGameData.getGameWindow(), camera, gameItems, hudItems, spriteItems);
            debugBuffer.unbindToRender(globalGameData.getGameWindow().getWidth(),globalGameData.getGameWindow().getHeight());
        }
        renderer.render(globalGameData.getGameWindow(),camera,gameItems,hudItems,new SpriteItem[]{debugItem});
    }

    @Override
    public void logic() {

        double length = 5000;
        camera.setRotation(0, (float) (50*Math.sin((Math.PI/(length/2)) * (System.currentTimeMillis()%length))),0);

        gameItems[0].setPosition((float) (4*Math.sin((Math.PI/(length/2)) * (System.currentTimeMillis()%length))), 0,-5);


        hudItems[0].setText("FPS: " + getCurrentFPS() + " Count " + getFPSCount() + " A " + (getRenderUtilization() + getLogicUtilization()));

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



    }

    @Override
    public void enterState(GameStateType previousGameState) {
        super.enterState(previousGameState);
        globalGameData.getGameWindow().setCursor(AssetLoader.getGeneralAsset("Icon24x24.png"),0,0);
        globalGameData.getGameWindow().setIcon(getWindowHandle(),AssetLoader.getGeneralAsset("Icon16x16.png"),AssetLoader.getGeneralAsset("Icon32x32.png"));
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

    }
}
