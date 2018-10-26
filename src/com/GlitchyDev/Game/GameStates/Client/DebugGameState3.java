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


public class DebugGameState3 extends InputGameStateBase {
    // required for each gamestate

    public DebugGameState3(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.DEBUG_3);
        init();

    }

    @Override
    public void init() {




    }

    @Override
    public void render() {


    }

    @Override
    public void logic() {



    }

    @Override
    public void enterState(GameStateType previousGameState) {
        super.enterState(previousGameState);
    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {
        renderer.cleanup();

    }
}
