package com.GlitchyDev.World.Blocks.Abstract;

import com.GlitchyDev.World.Blocks.BlockType;
import com.GlitchyDev.World.Direction;
import com.GlitchyDev.World.Location;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * A Block, the basic building units of the world map. Blocks should contain and run its own logic for
 * - Rendering
 * - Ticking
 * - Pathfinding ( Like a portal block should provide pathfinding AI the connection detail to its other side
 */
public abstract class BlockBase {
    protected final BlockType blockType;
    // Serialize only as
    protected Vector3f rotation;
    protected float scale;
    // DO NOT serialize, as it is relative to the position of its accompanying region
    protected Location location;
    private boolean isDisableFrustumCulling = false;
    private boolean insideFrustum = false;

    public BlockBase(BlockType blockType, Location location, Vector3f rotation) {
        this.blockType = blockType;
        this.location = location;
        this.rotation = rotation;
        this.scale = 1.0f;
    }
    public BlockBase(BlockType blockType, Location location) {
        this.blockType = blockType;
        this.location = location;
        this.rotation = new Vector3f();
        this.scale = 1.0f;
    }

    public abstract boolean isUseless();

    public abstract void rotate(Direction direction);


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

    public boolean isDisableFrustumCulling() {
        return isDisableFrustumCulling;
    }

    public void setInsideFrustum(boolean insideFrustum) {
        this.insideFrustum = insideFrustum;
    }

    public boolean isInsideFrustum() {
        return insideFrustum;
    }




    // Add Serialization
}
