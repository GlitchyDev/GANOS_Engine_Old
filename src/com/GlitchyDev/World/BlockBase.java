package com.GlitchyDev.World;

import com.GlitchyDev.Rendering.Assets.WorldElements.GameItem;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.Serializable;

public abstract class BlockBase {
    protected final BlockType blockType;
    // Serialize only as
    protected Vector3f rotation;
    protected float scale;
    // DO NOT serialize, as it is relative to the position of its accompanying region
    protected Location location;

    public BlockBase(BlockType blockType, Location location, Vector3f rotation) {
        this.blockType = blockType;
        this.location = location;
        this.rotation = rotation;
        this.scale = 1.0f;
    }


    public BlockType getBlockType() {
        return blockType;
    }

    public Vector3f getRotation()
    {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Location getLocation()
    {
        return location;
    }

    public Vector3i getPosition(){
        return location.getPosition();
    }

    public Vector3i getNormalizedPosition(){
        return new Vector3i(location.getPosition()).mul(2);
    }


    // *****

    public void setRotation(Vector3f rotation)
    {
        this.rotation = rotation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



    // Add Serialization
}
