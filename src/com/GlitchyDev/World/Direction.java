package com.GlitchyDev.World;

public enum Direction {
    ABOVE,
    BELOW,
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction reverse()
    {
        switch(this)
        {
            case ABOVE:
                return BELOW;
            case BELOW:
                return ABOVE;
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
        }
        return ABOVE;
    }
}
