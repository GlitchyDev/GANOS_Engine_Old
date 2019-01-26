package com.GlitchyDev.Game.GameStates.Client;

import com.GlitchyDev.Game.GameStates.Abstract.EnvironmentGameState;
import com.GlitchyDev.Game.GameStates.Abstract.GameStateBase;
import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.GameInput.GameController;
import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.InstancedGridTexture;
import com.GlitchyDev.Rendering.Assets.PartialCubicInstanceMesh;
import com.GlitchyDev.Rendering.Assets.WorldElements.Camera;
import com.GlitchyDev.Utility.GlobalGameData;
import com.GlitchyDev.World.Entities.Player_LN;
import com.GlitchyDev.World.World;

public class OverworldGameState extends GameStateBase {
    private World world;
    private Player_LN player;
    private MapBuilderGameState.DebugCommandRunnable debugCommandRunnable;

    private Camera camera;
    private GameController gameController;
    private PartialCubicInstanceMesh instancedMesh;
    private InstancedGridTexture instancedGridTexture;

    public OverworldGameState(GlobalGameData globalGameDataBase, World world, Player_LN player_ln) {
        super(globalGameDataBase, GameStateType.OVERWORLD);
        camera = new Camera();
        instancedGridTexture = AssetLoader.getInstanceGridTexture("UVMapCubeTexture");
        instancedMesh = new PartialCubicInstanceMesh(AssetLoader.loadMesh("/Mesh/PartialCubicBlock/CubicMesh1.obj"),60*60, instancedGridTexture);

        this.world = world;
        this.player = player_ln;
    }


    @Override
    public void init() {

    }

    @Override
    public void doRender() {
        renderer.prepRender(globalGameData.getGameWindow());
        renderer.updateFrustumCullingFilter(globalGameData.getGameWindow(),camera,world.getChunks());
        renderer.renderInstancedPartialCubicChunk(globalGameData.getGameWindow(),"Instance3D", camera, instancedMesh, world.getChunks(), true);
    }

    @Override
    public void doLogic() {

    }

    @Override
    public void enterState(GameStateType previousGameState) {

    }

    @Override
    public void exitState(GameStateType nextGameState) {

    }

    @Override
    public void windowClose() {

    }
}
