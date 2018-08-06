package com.GlitchyDev.Game.GameStates.Server;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.InputGameStateBase;
import com.GlitchyDev.Utility.GlobalGameData;
import com.GlitchyDev.World.Entities.BasicEntity;

import java.util.ArrayList;

public class DebugWorldGameStateBase extends InputGameStateBase {
    private final GameStateType gameStateType = GameStateType.DEBUG_WORLD;
    private ArrayList<BasicEntity> entities;

    public DebugWorldGameStateBase(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase);
        init();
    }

    @Override
    public void init() {
        entities = new ArrayList<>();


    }

    @Override
    public void logic() {


    }

    @Override
    public void render() {

    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }
}
