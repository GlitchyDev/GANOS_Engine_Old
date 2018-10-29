package com.GlitchyDev.Game.GameStates.Server;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.Abstract.InputGameStateBase;
import com.GlitchyDev.Networking.ServerNetworkConnection;
import com.GlitchyDev.Rendering.WorldElements.SpriteItem;
import com.GlitchyDev.Rendering.WorldElements.TextItem;
import com.GlitchyDev.Utility.GlobalGameData;

import java.util.ArrayList;

public class DebugGameState2 extends InputGameStateBase {
    private ServerNetworkConnection serverNetworkConnection;

    private ArrayList<TextItem> hudItems;
    private ArrayList<SpriteItem> spriteItems;



    public DebugGameState2(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.DEBUG_2);
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

        serverNetworkConnection.disableAcceptingClients();

    }
}
