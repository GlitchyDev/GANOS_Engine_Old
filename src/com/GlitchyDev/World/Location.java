package com.GlitchyDev.World;

import org.joml.Vector3i;

import java.io.Serializable;

public class Location implements Serializable {
    private Vector3i position;
    //private World world;


    public Location()
    {
        position = new Vector3i();
    }
    public Location(int x, int y, int z)
    {
        position = new Vector3i(x,y,z);
    }
    public Location(Vector3i position)
    {
        this.position = position;
    }
    public Location(Location location)
    {
        position = new Vector3i(location.getPosition());
    }


    public Location getOffsetLocation(int x, int y, int z)
    {

        return new Location(new Vector3i(position).add(x,y,z));
    }

    public void addOffset(int x, int y, int z)
    {
        position.add(x,y,z);
    }



    public void setX(int x) {
        this.position.x = x;
    }

    public void setY(int y) { this.position.y = y; }

    public void setZ(int z) { this.position.z = z; }

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


    public Vector3i getPosition(){
        return position;
    }



    //public World getWorld() {return world;}
}
