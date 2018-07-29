package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Utility.GlobalGameDataBase;

public abstract class GameStateBase {
    protected GlobalGameDataBase globalGameData;

    public GameStateBase(GlobalGameDataBase globalGameData) {
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
}
