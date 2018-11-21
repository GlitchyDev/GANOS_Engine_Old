package com.GlitchyDev.World;

import com.GlitchyDev.Rendering.Assets.WorldElements.GameItem;

import java.io.Serializable;

public abstract class BlockBase extends GameItem implements Serializable {
    protected final BlockType blockType;
    // DO NOT serialize, as it is relative to the position of its accompanying region
    protected Location location;

    public BlockBase(BlockType blockType, Location location) {
        this.blockType = blockType;
        this.location = location;
        setPosition(location.getX() * 2, location.getY() * 2, location.getZ() * 2);
    }

    public abstract void buildMeshes();

    public Location getLocation() {
        return location;
    }

}
