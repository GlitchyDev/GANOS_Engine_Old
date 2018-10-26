package com.GlitchyDev.Game.GameStates.Server;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.InputGameStateBase;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Networking.GameSocket;
import com.GlitchyDev.Networking.Packets.General.DebugScrollPacket;
import com.GlitchyDev.Networking.Packets.Packet;
import com.GlitchyDev.Networking.Packets.PacketType;
import com.GlitchyDev.Networking.ServerNetworkConnection;
import com.GlitchyDev.Rendering.Assets.FontTexture;
import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.Texture;
import com.GlitchyDev.Rendering.WorldElements.Camera;
import com.GlitchyDev.Rendering.WorldElements.GameItem;
import com.GlitchyDev.Rendering.WorldElements.SpriteItem;
import com.GlitchyDev.Rendering.WorldElements.TextItem;
import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.Utility.GlobalGameData;

import java.awt.*;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class DebugGameState2 extends InputGameStateBase {
    private ServerNetworkConnection serverNetworkConnection;

    private TextItem[] hudItems;
    private SpriteItem[] spriteItems;



    public DebugGameState2(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.DEBUG_2);
        init();

    }

    @Override
    public void init() {
        final Font FONT = new Font("Arial", Font.PLAIN, 20);
        final String CHARSET = "ISO-8859-1";

        FontTexture fontTexture = new FontTexture(FONT,CHARSET);
        TextItem item = new TextItem("0",fontTexture);
        TextItem item2 = new TextItem("0",fontTexture);
        item2.setPosition(0,100,0);
        hudItems = new TextItem[]{item,item2};

        spriteItems = new SpriteItem[1];
        for(int i = 0; i < spriteItems.length; i++) {
            spriteItems[i] = new SpriteItem(AssetLoader.getTextureAsset("Tomo"),true);
            spriteItems[i].setPosition(0, 0, (float) (0.000001 * i));
            spriteItems[i].setScale(1.0f);
        }

        serverNetworkConnection = new ServerNetworkConnection();
        serverNetworkConnection.enableAcceptingClients();
    }

    @Override
    public void render() {
        renderer.prepRender(globalGameData.getGameWindow());
        renderer.renderHUD(globalGameData.getGameWindow(),"Default2D",hudItems);
    }

    @Override
    public void logic() {
        if (gameInput.getKeyValue(GLFW_KEY_ESCAPE) == 1) {
            globalGameData.getGameWindow().makeWindowClose();
            System.out.println("DebugGameState: CLOSING WINDOW");
        }


        String total = "ConnectedUsers: ";
        for (Object o : serverNetworkConnection.getConnectedUsers()) {
            total += o;
        }
        hudItems[0].setText(total);

        String total2 = "Approved Users: ";
        for (Object o : serverNetworkConnection.getApprovedUsers()) {
            total2 += o + " ";
        }
        hudItems[1].setText(total2);


        if(serverNetworkConnection.getNumberOfConnectedUsers() == 1)
        {
            GameSocket gameSocket = serverNetworkConnection.getUsersGameSocket("James");
            if(gameSocket.hasUnprocessedPackets())
            {
                for(Packet packet: gameSocket.getUnprocessedPackets()) {
                    if(packet.getPacketType() == PacketType.A_DEBUGSCROLL) {
                        DebugScrollPacket debugScrollPacket = new DebugScrollPacket(packet);
                        spriteItems[0].getPosition().add(0, (float) (debugScrollPacket.getUnit()), 0);
                    }
                }
            }
        }




    }

    @Override
    public void enterState(GameStateType previousGameState) {
        super.enterState(previousGameState);
        globalGameData.getGameWindow().setCursor(AssetLoader.getInputStream("Icon16x16.png"),0,0);
        globalGameData.getGameWindow().setIcon(getWindowHandle(),AssetLoader.getInputStream("Icon24x24.png"),AssetLoader.getInputStream("Icon32x32.png"));
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

        serverNetworkConnection.disableAcceptingClients();

    }
}
