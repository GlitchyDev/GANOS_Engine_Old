package com.GlitchyDev.World.Entities.Living;

import com.GlitchyDev.Game.GameStates.Server.DebugWorldGameStateBase;
import com.GlitchyDev.Rendering.WorldElements.SpriteItem;
import com.GlitchyDev.World.Entities.BasicPhysicalEntity;
import com.GlitchyDev.World.Location;

import java.util.ArrayList;

public class DebugEntity extends BasicPhysicalEntity {
    private SpriteItem spriteItem;

    public DebugEntity(DebugWorldGameStateBase debugWorldGameState, Location location) {
        super(debugWorldGameState, location);
    }

    @Override
    protected void authorityLogic(ArrayList<String> actionList, boolean isNetworked) {

    }

    @Override
    protected void generalLogic() {

    }

    @Override
    public void doEntityRender() {

    }
}
