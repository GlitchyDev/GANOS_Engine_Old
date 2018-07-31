package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Utility.GlobalGameData;

public abstract class GameStateBase {
    protected GlobalGameData globalGameData;
    protected GameStateType gameStateType;

    public GameStateBase(GlobalGameData globalGameData) {
        this.globalGameData = globalGameData;
    }

    public abstract void doRender();

    public abstract void doLogic();

    /**
     * Initialize the GameState's NonInherited variables to default
     */
    public abstract void init();

    public abstract void enterState(GameStateType previousGameState);

    public abstract void exitState(GameStateType nextGameState);

    public abstract void windowClose();

    // Functions

    protected long getWindowHandle() {
        return globalGameData.getGameWindow().getWindowHandle();
    }

    public GameStateType getGameStateType() {
        return gameStateType;
    }
}
