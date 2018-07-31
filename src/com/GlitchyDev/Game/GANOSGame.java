package com.GlitchyDev.Game;

import com.GlitchyDev.Game.GameStates.Client.DebugGameState1;
import com.GlitchyDev.Game.GameStates.Client.DebugGameState2;
import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.General.GameStateBase;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalGameData;

public class GANOSGame {

    protected GameWindow gameWindow;
    protected GlobalGameData globalGameData;

    public GANOSGame(String[] args) throws Exception {
        gameWindow = new GameWindow(args[0] + "_GANOS",1000,1000,true);
        globalGameData = new GlobalGameData(gameWindow);
        gameWindow.init();

        AssetLoader.loadAssets();
        initGameStates(args);
        gameWindow.showWindow();

        run();
    }

    public void initGameStates(String[] args)
    {
        if(args.length != 0) {
            switch (args[0]) {
                case "GENERAL":
                case "CLIENT":
                    globalGameData.registerGameState(new DebugGameState1(globalGameData));
                    break;
                case "SERVER":
                    globalGameData.registerGameState(new DebugGameState2(globalGameData));
                    break;


            }
        }
        else
        {
           // Debug GameState
            globalGameData.registerGameState(new DebugGameState1(globalGameData));
        }

    }


    public void run()
    {
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
