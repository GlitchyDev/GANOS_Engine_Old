package com.GlitchyDev.GameInput;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public abstract class GameController {
    protected final int controllerID;
    protected boolean currentlyActive = false;
    protected String name;


    protected ByteBuffer[] buttons =  new ByteBuffer[2];
    protected FloatBuffer[] axes = new FloatBuffer[2];

    protected boolean previousCurrentlyActive = false;
    public GameController(int controllerID)
    {

        this.controllerID = controllerID;

    }


    public void tick()
    {
        if(previousCurrentlyActive)
        {
            previousCurrentlyActive = currentlyActive;
            buttons[1] = clone(buttons[0]);
            axes[1] = clone(axes[0]);
        }

        previousCurrentlyActive = currentlyActive;
        currentlyActive = glfwJoystickPresent(controllerID);
        if(currentlyActive) {
            buttons[0] = glfwGetJoystickButtons(controllerID);
            axes[0] = glfwGetJoystickAxes(controllerID);
            name = glfwGetJoystickName(controllerID);
        }


    }

    private ByteBuffer clone(ByteBuffer original) {
        ByteBuffer clone = ByteBuffer.allocate(original.capacity());
        original.rewind();//copy from the beginning
        clone.put(original);
        original.rewind();
        clone.flip();
        return clone;
    }

    private FloatBuffer clone(FloatBuffer original) {
        FloatBuffer clone = FloatBuffer.allocate(original.capacity());
        original.rewind();//copy from the beginning
        clone.put(original);
        original.rewind();
        clone.flip();
        return clone;
    }

    public abstract boolean getSouthButton();
    public abstract boolean getEastButton();
    public abstract boolean getWestButton();
    public abstract boolean getNorthButton();

    public abstract boolean getLeftBumperButton();
    public abstract boolean getRightBumperButton();
    public abstract boolean getLeftHomeButton();
    public abstract boolean getRightHomeButton();

    public abstract boolean getLeftJoyStickButton();
    public abstract boolean getRightJoyStickButton();

    public abstract ControllerDirectionPad getDirectionPad();

    public abstract float getLeftJoyStickX();
    public abstract float getLeftJoyStickY();

    public abstract float getRightJoyStickX();
    public abstract float getRightJoyStickY();

    public abstract float getLeftTrigger();
    public abstract float getRightTrigger();

    public boolean isCurrentlyActive() {
        return currentlyActive;
    }
    public String getName() {
        return name;
    }
    public int getControllerID() { return controllerID; }
// Get Toggled

    public abstract boolean getToggleSouthButton();
    public abstract boolean getToggleEastButton();
    public abstract boolean getToggleWestButton();
    public abstract boolean getToggleNorthButton();

    public abstract boolean getToggleLeftBumperButton();
    public abstract boolean getToggleRightBumperButton();
    public abstract boolean getToggleLeftHomeButton();
    public abstract boolean getToggleRightHomeButton();

    public abstract boolean getToggleLeftJoyStickButton();
    public abstract boolean getToggleRightJoyStickButton();

    public abstract ControllerDirectionPad getToggleDirectionPad();

    public abstract boolean getToggleLeftTrigger();
    public abstract boolean getToggleRightTrigger();



    // Get Previous
    public abstract boolean getPreviousSouthButton();
    public abstract boolean getPreviousEastButton();
    public abstract boolean getPreviousWestButton();
    public abstract boolean getPreviousNorthButton();

    public abstract boolean getPreviousLeftBumperButton();
    public abstract boolean getPreviousRightBumperButton();
    public abstract boolean getPreviousLeftHomeButton();
    public abstract boolean getPreviousRightHomeButton();

    public abstract boolean getPreviousLeftJoyStickButton();
    public abstract boolean getPreviousRightJoyStickButton();

    public abstract ControllerDirectionPad getPreviousDirectionPad();

    public abstract float getPreviousLeftJoyStickX();
    public abstract float getPreviousLeftJoyStickY();

    public abstract float getPreviousRightJoyStickX();
    public abstract float getPreviousRightJoyStickY();

    public abstract float getPreviousLeftTrigger();
    public abstract float getPreviousRightTrigger();

    public boolean isPreviousCurrentlyActive() {
        return previousCurrentlyActive;
    }
}
