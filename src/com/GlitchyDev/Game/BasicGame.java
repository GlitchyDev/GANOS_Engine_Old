package com.GlitchyDev.Game;

import com.GlitchyDev.Game.GameStates.General.GameStateBase;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalGameDataBase;

public abstract class BasicGame {
    protected GameWindow gameWindow;
    protected GlobalGameDataBase globalGameData;

    public void run()
    {
        final int FPS_Target = globalGameData.getGameWindow().getTargetFPS();

        GameStateBase currentGameState;
        while(!globalGameData.getGameWindow().getWindowShouldClose()) {

            gameWindow.update();

            currentGameState = globalGameData.getCurrentGameState();

            currentGameState.doLogic();
            currentGameState.doRender();


            if(gameWindow.getWindowShouldClose())
            {
                currentGameState.windowClose();
            }
        }
    }


}
