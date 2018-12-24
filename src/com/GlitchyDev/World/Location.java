package com.GlitchyDev.World;

import org.joml.Vector3i;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Location implements Serializable {
    private Vector3i position;
    //private World world;


    public Location() {
        position = new Vector3i();
    }

    public Location(int x, int y, int z) {
        position = new Vector3i(x, y, z);
    }

    public Location(Vector3i position) {
        this.position = position;
    }

    public Location(Location location) {
        position = new Vector3i(location.getPosition());
    }



    public Location getOffsetLocation(int x, int y, int z) {

        Location offsetCopy = this.clone();
        offsetCopy.addOffset(x, y, z);
        return offsetCopy;
    }

    public void addOffset(int x, int y, int z) {
        position.add(x, y, z);
    }


    public void setX(int x) {
        this.position.x = x;
    }

    public void setY(int y) {
        this.position.y = y;
    }

    public void setZ(int z) {
        this.position.z = z;
    }

    public void setPosition(int x, int y, int z) {
        position.set(x, y, z);
    }

    //public void setWorld(World world) { this.world = world; }


    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public int getZ() {
        return position.z;
    }


    public Vector3i getPosition() {
        return position;
    }

    public Location getAbove()
    {
        return getOffsetLocation(0,1,0);
    }
    public Location getBelow()
    {
        return getOffsetLocation(0,-1,0);
    }
    public Location getNorth()
    {
        return getOffsetLocation(0,0,-1);
    }
    public Location getEast()
    {
        return getOffsetLocation(1,0,0);
    }
    public Location getSouth()
    {
        return getOffsetLocation(0,0,1);
    }
    public Location getWest()
    {
        return getOffsetLocation(-1,0,0);
    }

    public Location clone()
    {
        return new Location(this);
    }
    //public World getWorld() {return world;}

    public Location getDirectionLocation(Direction direction)
    {
        switch(direction)
        {
            case ABOVE:
                return getAbove();
            case BELOW:
                return getBelow();
            case NORTH:
                return getNorth();
            case EAST:
                return getEast();
            case SOUTH:
                return getSouth();
            case WEST:
                return getWest();
        }
        return this;
    }

    @Override
    public String toString() {
        return "l@" + getX() + "," + getY() + "," + getZ();
    }
}
