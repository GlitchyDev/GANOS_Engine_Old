package com.GlitchyDev.World;

public class Location {
    private int x;
    private int y;
    private int z;
    private String world;


    public Location(int x, int y, int z, String world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Location getOffsetLocation(int x, int y, int z)
    {
        return new Location(this.x + x, this.y + y, this.z + z, world);
    }

    public void addOffset(int x, int y, int z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public Location clone()
    {
        return new Location(x,y,z,world);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getWorld() {
        return world;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}
