package com.GlitchyDev.Game;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalClientData;

public class ClientGame extends BasicGame {

    public ClientGame() throws Exception {
        gameWindow = new GameWindow("Client GANOS",500,500,true);
        globalDataBase = new GlobalClientData(gameWindow);
        gameWindow.init();
        AssetLoader.loadAssets();
        globalDataBase.initGameStates();
        gameWindow.showWindow();

    }


}
