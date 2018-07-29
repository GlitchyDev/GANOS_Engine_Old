package com.GlitchyDev.Utility;

import com.GlitchyDev.Game.GameStates.Client.DebugGameState1;
import com.GlitchyDev.Game.GameStates.GameStateType;

public class GlobalClientGameData extends GlobalGameDataBase {


    public GlobalClientGameData(GameWindow gameWindow) {
        super(gameWindow);
    }

    @Override
    public void initGameStates() {
        registerGameState(GameStateType.DEBUG_1,new DebugGameState1(this));

        setCurrentGameState(GameStateType.DEBUG_1);
    }
}
