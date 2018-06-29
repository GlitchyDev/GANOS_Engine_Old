package com.GlitchyDev.Utility;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import java.util.HashMap;

public class GameController {

    private static GameContainer gameContainer;
    private static HashMap<GButtons,Integer> controllerMapping;

    public static void linkControls(GameContainer gameContainer)
    {
        System.out.println("GameController: Linking Controls");
        GameController.gameContainer = gameContainer;
        GameController.controllerMapping = new HashMap<>();
        createMapping();
    }

    private static void createMapping()
    {
        controllerMapping.put(GButtons.UP,Input.KEY_UP);
        controllerMapping.put(GButtons.LEFT,Input.KEY_LEFT);
        controllerMapping.put(GButtons.DOWN,Input.KEY_DOWN);
        controllerMapping.put(GButtons.RIGHT,Input.KEY_RIGHT);
        controllerMapping.put(GButtons.A, Input.KEY_Z);
        controllerMapping.put(GButtons.B,Input.KEY_X);
        controllerMapping.put(GButtons.START,Input.KEY_ENTER);
        controllerMapping.put(GButtons.SELECT,Input.KEY_BACK);
        controllerMapping.put(GButtons.L,Input.KEY_A);
        controllerMapping.put(GButtons.R,Input.KEY_S);
        controllerMapping.put(GButtons.DEBUG,Input.KEY_BACKSLASH);
    }

    public static boolean isButtonPressed(GButtons button)
    {
        return gameContainer.getInput().isKeyPressed(controllerMapping.get(button));
    }

    public static boolean isButtonDown(GButtons button)
    {
        return gameContainer.getInput().isKeyDown(controllerMapping.get(button));
    }
}
