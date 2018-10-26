package com.GlitchyDev.Game.GameStates;

import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.Utility.GlobalGameData;

public abstract class GameStateBase {
    protected GlobalGameData globalGameData;
    protected final GameStateType gameStateType;
    protected final Renderer renderer;

    public GameStateBase(GlobalGameData globalGameData, GameStateType gameStateType) {
        this.globalGameData = globalGameData;
        this.gameStateType = gameStateType;
        this.renderer = new Renderer();
    }

    public abstract void init() throws Exception;

    public abstract void doRender();

    public abstract void doLogic();

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
