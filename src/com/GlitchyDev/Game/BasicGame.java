package com.GlitchyDev.Game;

import com.GlitchyDev.Game.GameStates.General.GameStateBase;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalDataBase;

public abstract class BasicGame {
    protected GameWindow gameWindow;
    protected GlobalDataBase globalDataBase;

    public void run()
    {
        final int FPS_Target = globalDataBase.getGameWindow().getTargetFPS();

        GameStateBase currentGameState;
        while(!globalDataBase.getGameWindow().getWindowShouldClose()) {

            gameWindow.update();

            currentGameState = globalDataBase.getCurrentGameState();

            long frameStart = System.currentTimeMillis();
            currentGameState.doLogic();
            currentGameState.doRender();
            long frameEnd = System.currentTimeMillis();

            long leftover = (long) ((1000.0/FPS_Target) - (frameEnd-frameStart));
            if(leftover > 0)
            {
                try {
                    Thread.sleep(leftover);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(gameWindow.getWindowShouldClose())
            {
                currentGameState.windowClose();
            }
        }
    }


    // Getters

    public long getWindowHandle()
    {
        return globalDataBase.getGameWindow().getWindowHandle();
    }

}
