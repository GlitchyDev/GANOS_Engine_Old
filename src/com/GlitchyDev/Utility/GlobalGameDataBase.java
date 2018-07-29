package com.GlitchyDev.Utility;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.General.GameStateBase;

import java.util.HashMap;

public abstract class GlobalGameDataBase {
    private GameWindow gameWindow;
    private HashMap<GameStateType,GameStateBase> loadedGameStates;
    private GameStateType currentGameState;

    public GlobalGameDataBase(GameWindow gameWindow)
    {
        this.gameWindow = gameWindow;
        loadedGameStates = new HashMap<>();
    }

    public abstract void initGameStates();


    public void switchGameState(GameStateType gameStateType)
    {
        GameStateType previousState = currentGameState;
        loadedGameStates.get(previousState).exitState(gameStateType);
        currentGameState = gameStateType;
        loadedGameStates.get(currentGameState).enterState(previousState);
    }

    public GameStateBase getCurrentGameState()
    {
        return loadedGameStates.get(currentGameState);
    }

    public void registerGameState(GameStateType gameStateType, GameStateBase gameState)
    {
        loadedGameStates.put(gameStateType,gameState);
    }

    // Getters

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setCurrentGameState(GameStateType currentGameState) {
        this.currentGameState = currentGameState;
        getCurrentGameState().enterState(GameStateType.NONE);
    }
}
