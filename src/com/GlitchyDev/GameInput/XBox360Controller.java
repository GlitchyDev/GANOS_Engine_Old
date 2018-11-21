package com.GlitchyDev.GameInput;

public class XBox360Controller extends GameController {
    public XBox360Controller(int controllerID) {
        super(controllerID);
    }

    public boolean getSouthButton()
    {
        return buttons.get(0) == 1;
    }
    public boolean getEastButton()
    {
        return buttons.get(1) == 1;
    }
    public boolean getWestButton()
    {
        return buttons.get(2) == 1;
    }
    public boolean getNorthButton()
    {
        return buttons.get(3) == 1;
    }
    public boolean getLeftBumperButton()
    {
        return buttons.get(4) == 1;
    }
    public boolean getRightBumperButton()
    {
        return buttons.get(5) == 1;
    }
    public boolean getLeftHomeButton()
    {
        return buttons.get(6) == 1;
    }
    public boolean getRightHomeButton()
    {
        return buttons.get(7) == 1;
    }
    public boolean getLeftJoyStickButton()
    {
        return buttons.get(8) == 1;
    }
    public boolean getRightJoyStickButton()
    {
        return buttons.get(9) == 1;
    }

    public ControllerDirectionPad getDirectionPad()
    {
        if(buttons.get(10) == 1)
        {
            if(buttons.get(11) == 1)
            {
                return ControllerDirectionPad.NORTH_EAST;
            }
            if(buttons.get(13) == 1)
            {
                return ControllerDirectionPad.NORTH_WEST;
            }
            return ControllerDirectionPad.NORTH;
        }
        if(buttons.get(12) == 1)
        {
            if(buttons.get(11) == 1)
            {
                return ControllerDirectionPad.SOUTH_EAST;
            }
            if(buttons.get(13) == 1)
            {
                return ControllerDirectionPad.SOUTH_WEST;
            }
            return ControllerDirectionPad.SOUTH;
        }
        if(buttons.get(11) == 1)
        {
            return ControllerDirectionPad.EAST;
        }
        if(buttons.get(13) == 1)
        {
            return ControllerDirectionPad.WEST;
        }
        return ControllerDirectionPad.NONE;
    }

    public float getLeftJoyStickX()
    {
        return axes.get(0);
    }
    public float getLeftJoyStickY()
    {
        return axes.get(1);
    }

    public float getRightJoyStickX()
    {
        return axes.get(2);
    }
    public float getRightJoyStickY()
    {
        return axes.get(3);
    }

    public float getLeftTrigger()
    {
        return axes.get(4);
    }
    public float getRightTrigger()
    {
        return axes.get(5);
    }
}
