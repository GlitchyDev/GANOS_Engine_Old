package com.GlitchyDev.World;

public enum Direction {
    ABOVE,
    NORTH,
    EAST,
    SOUTH,
    WEST,
    BELOW;

    public Direction reverse() {
        switch(this) {
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

    public int getRotation() {
        switch(this) {
            case NORTH:
                return 0;
            case EAST:
                return 90;
            case SOUTH:
                return 180;
            case WEST:
                return 270;
        }
        return 0;
    }
}
