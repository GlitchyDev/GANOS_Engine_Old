package com.GlitchyDev.World.Entities;

import com.GlitchyDev.Game.GameStates.Server.DebugWorldGameStateBase;
import com.GlitchyDev.World.Location;

public abstract class BasicPhysicalEntity extends BasicEntity {
    protected Location location;

    public BasicPhysicalEntity(DebugWorldGameStateBase debugWorldGameState, Location location)
    {
        super(debugWorldGameState);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
