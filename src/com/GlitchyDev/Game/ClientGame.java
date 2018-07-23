package com.GlitchyDev.Game;

import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalClientData;

public class ClientGame extends BasicGame {

    public ClientGame()
    {
        gameWindow = new GameWindow("Client GANOS",500,500,true);
        globalDataBase = new GlobalClientData(gameWindow);
        gameWindow.init();
        globalDataBase.initGameStates();
        // Load Assets
        gameWindow.showWindow();

    }


}
