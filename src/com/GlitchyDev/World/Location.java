package com.GlitchyDev.World;

import java.io.Serializable;

public class Location implements Serializable {
    private int x;
    private int y;
    private int z;
    private World world;

    public Location(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Location()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Location getOffsetLocation(int x, int y, int z)
    {
        return new Location(this.x + x, this.y + y, this.z + z);
    }

    public void addOffset(int x, int y, int z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setWorld(World world) {
        this.world = world;
    }


    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public World getWorld() {
        return world;
    }
}
