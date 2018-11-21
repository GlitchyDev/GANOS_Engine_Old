package com.GlitchyDev.GameInput;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public abstract class GameController {
    protected final int controllerID;
    protected boolean currentlyActive;
    protected ByteBuffer buttons;
    protected FloatBuffer axes;
    protected String name;

    public GameController(int controllerID)
    {
        this.controllerID = controllerID;
    }

    public void tick()
    {
        currentlyActive = glfwJoystickPresent(controllerID);
        if(currentlyActive) {
            buttons = glfwGetJoystickButtons(controllerID);
            axes = glfwGetJoystickAxes(controllerID);
            name = glfwGetJoystickName(controllerID);
        }

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
    public int getControllerID() {
        return controllerID;
    }
}
