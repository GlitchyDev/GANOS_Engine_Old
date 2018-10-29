package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Game.GameStates.Abstract.InputGameStateBase;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.Sounds.SoundManager;
import com.GlitchyDev.Rendering.Assets.Sounds.SoundSource;
import com.GlitchyDev.Utility.GlobalGameData;

public class MapBuilderGameState extends InputGameStateBase {
    private SoundSource debugSource;

    public MapBuilderGameState(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase, GameStateType.MAPBUILDER);
        init();
    }

    @Override
    public void init() {
        debugSource = new SoundSource(true,false);
        debugSource.setBuffer(AssetLoader.getSoundAsset("SoundDebug").getBufferId());
        debugSource.play();
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
        debugSource.stop();
        debugSource.cleanup();
    }

}
