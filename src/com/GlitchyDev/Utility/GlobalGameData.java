package com.GlitchyDev.Utility;

import com.GlitchyDev.Game.GameStates.Abstract.GameStateBase;
import com.GlitchyDev.Game.GameStates.GameStateType;

import java.util.HashMap;

/**
 * A collection and holder of all pertinent game information accessible by any internal components
 */
public class GlobalGameData {
    private GameWindow gameWindow;
    private HashMap<GameStateType,GameStateBase> loadedGameStates;
    private GameStateType currentGameState;

    public GlobalGameData(GameWindow gameWindow)
    {
        this.gameWindow = gameWindow;
        loadedGameStates = new HashMap<>();
        currentGameState = currentGameState;
    }


    public void switchGameState(GameStateType gameStateType)
    {
        GameStateType previousState = currentGameState;
        loadedGameStates.get(previousState).exitState(gameStateType);
        currentGameState = gameStateType;

        if(loadedGameStates.get(currentGameState) == null) {
            System.out.println("DIE POTATOE");
        }
        loadedGameStates.get(currentGameState).enterState(previousState);
    }

    public GameStateBase getCurrentGameState()
    {
        return loadedGameStates.get(currentGameState);
    }

    public void registerGameState(GameStateBase gameState)
    {
        System.out.println("Registering gameState " + gameState);
        loadedGameStates.put(gameState.getGameStateType(),gameState);
        System.out.println(currentGameState);
        if(currentGameState == GameStateType.NONE)
        {
            System.out.println("Initial GameState is " + gameState);
            currentGameState = gameState.getGameStateType();
            gameState.enterState(gameState.getGameStateType());
        }
    }

    // Getters

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public GameStateBase getGameState(String gameState) {
        return loadedGameStates.get(gameState);
    }

}
