package com.GlitchyDev.Utility;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NONE,
    NORTHEAST,
    NORTHWEST,
    SOUTHEAST,
    SOUTHWEST;

    public Direction reverse()
    {
        switch(this)
        {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case NORTHEAST:
                return SOUTHWEST;
            case NORTHWEST:
                return SOUTHEAST;
            case SOUTHEAST:
                return NORTHWEST;
            case SOUTHWEST:
                return NORTHEAST;
        }
        return NONE;
    }

    public Direction mirror()
    {
        switch(this)
        {
            case NORTH:
                return NORTH;
            case EAST:
                return WEST;
            case SOUTH:
                return SOUTH;
            case WEST:
                return EAST;
            case NORTHEAST:
                return NORTHWEST;
            case NORTHWEST:
                return NORTHEAST;
            case SOUTHEAST:
                return SOUTHWEST;
            case SOUTHWEST:
                return SOUTHEAST;
        }
        return NONE;
    }

    @Override
    public String toString() {
        switch(this)
        {
            case NORTH:
                return "North";
            case EAST:
                return "East";
            case SOUTH:
                return "South";
            case WEST:
                return "West";
            case NORTHEAST:
                return "NorthEast";
            case NORTHWEST:
                return "NorthWest";
            case SOUTHEAST:
                return "SouthEast";
            case SOUTHWEST:
                return "SouthWest";
        }
        return "None";
    }

    public boolean requiresFlip()
    {
        switch(this)
        {
            case NORTH:
            case SOUTH:
            case EAST:
            case NORTHEAST:
            case SOUTHEAST:
                return false;
            case WEST:
            case NORTHWEST:
            case SOUTHWEST:
                return true;
        }
        return false;
    }

    public static Direction getDirection(long[] controlMapping)
    {
        if(controlMapping[0] >= 1) {
            if(controlMapping[1] >= 1) {
                return NORTHEAST;
            }
            else {
                if(controlMapping[3] >= 1) {
                    return NORTHWEST;
                }
                else {
                    return NORTH;
                }
            }
        }
        else {
            if(controlMapping[2] >= 1) {
                if(controlMapping[1] >= 1) {
                    return SOUTHEAST;
                }
                else {
                    if(controlMapping[3] >= 1) {
                        return SOUTHWEST;
                    }
                    else {
                        return SOUTH;
                    }
                }
            }
            else {
                if(controlMapping[1] >= 1) {
                    return EAST;
                }
                else {
                    if(controlMapping[3] >= 1) {
                        return WEST;
                    }
                    else {
                        return NONE;
                    }
                }
            }
        }

    }

    public long getDirectionValue(long[] inputMapping)
    {
        switch(this)
        {
            case NORTH:
                return inputMapping[0];
            case EAST:
                return inputMapping[1];
            case SOUTH:
                return inputMapping[2];
            case WEST:
                return inputMapping[3];
            case NORTHEAST:
                return inputMapping[0] > inputMapping[1] ? inputMapping[0] : inputMapping[1];
            case NORTHWEST:
                return inputMapping[0] > inputMapping[3] ? inputMapping[0] : inputMapping[3];
            case SOUTHEAST:
                return inputMapping[2] > inputMapping[1] ? inputMapping[2] : inputMapping[1];
            case SOUTHWEST:
                return inputMapping[2] > inputMapping[3] ? inputMapping[2] : inputMapping[3];
            case NONE:
            default:
                return 0;
        }
    }

    public int getX(){
        switch(this)
        {
            case NORTH:
                return 0;
            case EAST:
                return 1;
            case SOUTH:
                return 0;
            case WEST:
                return -1;
            case NORTHEAST:
                return 2;
            case NORTHWEST:
                return -2;
            case SOUTHEAST:
                return 2;
            case SOUTHWEST:
                return -2;
            default:
                return 0;
        }
    }

    public int getY() {
        switch(this)
        {
            case NORTH:
                return -1;
            case EAST:
                return 0;
            case SOUTH:
                return 1;
            case WEST:
                return 0;
            case NORTHEAST:
                return -1;
            case NORTHWEST:
                return -1;
            case SOUTHEAST:
                return 1;
            case SOUTHWEST:
                return 1;
            default:
                return 0;
        }
    }

    final public boolean isDiagnal()
    {
        return this == NORTHEAST || this == NORTHWEST || this == SOUTHEAST || this == SOUTHWEST;
    }

}
