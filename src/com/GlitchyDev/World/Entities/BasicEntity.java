package com.GlitchyDev.World.Entities;

import com.GlitchyDev.Game.GameStates.Server.DebugWorldGameStateBase;

import java.util.ArrayList;

public abstract class BasicEntity {
    protected DebugWorldGameStateBase debugWorldGameState;

    public BasicEntity(DebugWorldGameStateBase debugWorldGameState) {
        this.debugWorldGameState = debugWorldGameState;
    }


    public void doEntityLogic(ArrayList<String> actionList, boolean isAuthority, boolean isNetworked)
    {
        if(isAuthority)
        {
            authorityLogic(actionList,isNetworked);
        }
        generalLogic();
    }

    /*
    Logic that is AI or affected by AI
     */
    protected abstract void authorityLogic(ArrayList<String> actionList, boolean isNetworked);

    /*
    Logic that only affects Rendering, animation, ect
     */
    protected abstract void generalLogic();

    public abstract void doEntityRender();

}
