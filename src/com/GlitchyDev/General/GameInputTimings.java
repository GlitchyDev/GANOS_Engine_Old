package com.GlitchyDev.General;

import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameInputTimings {
    private final GameInput gameInput;
    private HashMap<Integer,Integer> activeKeyTime = new HashMap<>();
    private int activeMouseButton1Time = 0;
    private int activeMouseButton2Time = 0;
    private int activeMouseButton3Time = 0;

    public GameInputTimings(GameInput gameInput)
    {
        this.gameInput = gameInput;
    }

    public void updateTimings()
    {
        updateActiveKeyTime();
        updateMouseKeyTime();
    }

    public void updateActiveKeyTime()
    {
        ArrayList<Integer> removeList = new ArrayList<>();
        for(int key: activeKeyTime.keySet())
        {
            if(!gameInput.getActiveKeys().contains(key)) {
                removeList.add(key);
            }
            else {
                activeKeyTime.put(key,activeKeyTime.get(key) + 1);
            }
        }
        for(int removeKey: removeList)
        {
            activeKeyTime.remove(removeKey);
        }


        for(int key: gameInput.getActiveKeys())
        {
            if(!activeKeyTime.containsKey(key))
            {
                activeKeyTime.put(key,1);
            }
        }
    }

    public void updateMouseKeyTime()
    {
        if(activeMouseButton1Time == 0)
        {
            if(gameInput.getMouseButton1() == 1)
            {
                activeMouseButton1Time = 1;
            }
        }
        else
        {
            if(gameInput.getMouseButton1() == 0)
            {
                activeMouseButton1Time = 0;
            }
            else
            {
                activeMouseButton1Time++;
            }
        }
        // **
        if(activeMouseButton2Time == 0)
        {
            if(gameInput.getMouseButton2() == 1)
            {
                activeMouseButton2Time = 1;
            }
        }
        else
        {
            if(gameInput.getMouseButton2() == 0)
            {
                activeMouseButton2Time = 0;
            }
            else
            {
                activeMouseButton2Time++;
            }
        }
        // **
        if(activeMouseButton3Time == 0)
        {
            if(gameInput.getMouseButton3() == 1)
            {
                activeMouseButton3Time = 1;
            }
        }
        else
        {
            if(gameInput.getMouseButton3() == 0)
            {
                activeMouseButton3Time = 0;
            }
            else
            {
                activeMouseButton3Time++;
            }
        }
    }

    public GameInput getGameInput() {
        return gameInput;
    }
}
