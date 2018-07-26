package com.GlitchyDev.General;

import com.GlitchyDev.Game.ClientGame;
import com.GlitchyDev.IO.AssetLoader;

import java.io.File;
import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) throws Exception {

        ClientGame clientGame = new ClientGame();
        clientGame.run();
    }
}
