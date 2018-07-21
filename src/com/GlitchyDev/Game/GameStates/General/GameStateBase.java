package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Utility.GlobalDataBase;

public abstract class GameStateBase {
    protected GlobalDataBase globalDataBase;

    public GameStateBase(GlobalDataBase globalDataBase) {
        this.globalDataBase = globalDataBase;
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
        return globalDataBase.getGameWindow().getWindowHandle();
    }
}
