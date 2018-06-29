package com.GlitchyDev.Utility;

import com.GlitchyDev.Utility.BasicMonitoredGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public abstract class BasicControllerGameState extends BasicMonitoredGameState {

    protected final long startingValue = -2;
    protected final long enabledValue = -1;
    private final int cacheInputDelay = 3;
    private ArrayList<long[]> cachedInputs = new ArrayList<>();
    private long[] inputMapping;

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        inputMapping = new long[GButtons.values().length];
        for(int i = 0; i < inputMapping.length; i++)
        {
            inputMapping[i] = startingValue;
        }
        for(int i = 0; i < cacheInputDelay; i++) {
            cachedInputs.add(inputMapping);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        pollInputs();
        super.update(gameContainer, stateBasedGame, i);
    }

    protected long[] getCurrentInputMapping()
    {
        return cachedInputs.get(0);
    }



    private void pollInputs()
    {
        // Remove the previous "Delayed" input
        if (cachedInputs.size() > cacheInputDelay) {
            cachedInputs.remove(0);
        }

        /*
        Understanding Behavior
        - Button is pressed, behavior is enabled and button can be further updated, some buttons overide others abilities to
        actually run, but not to be enabled. EX. You can push UP and DOWN, only UP will actually register but the movement up
        is lifted, Down will begin polling correctly
         */
        for (GButtons button : GButtons.values()) {

            if (inputMapping[button.ordinal()] == startingValue) {
                if (GameController.isButtonPressed(button)) {
                    inputMapping[button.ordinal()] = enabledValue;
                }
            }

            switch (button) {
                case UP:
                case LEFT:
                    if (GameController.isButtonDown(button)) {
                        if (inputMapping[button.ordinal()] == enabledValue) {
                            inputMapping[button.ordinal()] = 1;
                        } else {
                            inputMapping[button.ordinal()]++;
                        }
                        if (inputMapping[button.getReverse().ordinal()] > enabledValue) {
                            inputMapping[button.getReverse().ordinal()] = enabledValue;
                        }
                    } else {
                        inputMapping[button.ordinal()] = startingValue;
                    }
                    break;
                // Can be overruled by above
                case RIGHT:
                case DOWN:
                    if (GameController.isButtonDown(button)) {
                        if (!GameController.isButtonDown(button.getReverse())) {
                            if (inputMapping[button.ordinal()] == enabledValue) {
                                inputMapping[button.ordinal()] = 1;
                            } else {
                                inputMapping[button.ordinal()]++;
                            }
                        }
                    } else {
                        inputMapping[button.ordinal()] = startingValue;
                    }
                    break;
                case A:
                case B:
                case START:
                case SELECT:
                case L:
                case R:
                    if (GameController.isButtonDown(button)) {
                        if (inputMapping[button.ordinal()] == enabledValue) {
                            inputMapping[button.ordinal()] = 1;
                        } else {
                            inputMapping[button.ordinal()]++;
                        }
                    } else {
                        inputMapping[button.ordinal()] = startingValue;
                    }
                    break;
            }

        }
        cachedInputs.add(inputMapping.clone());

        // You can access input index 0 at any point before now, its removed after here
    }


    protected void drawDebugInputMapping(Graphics graphics)
    {
        graphics.setColor(Color.blue);
        graphics.fillRect(0, 0, 60, 140);

        graphics.setColor(Color.red);
        int i = 0;
        for (long l : inputMapping) {
            graphics.drawString(String.valueOf(l), 0, i * 12);
            i++;
        }
        i = 0;
        if(cachedInputs.size() != 0) {
            for (long l : cachedInputs.get(0)) {
                graphics.drawString(String.valueOf(l), 35, i * 12);
                i++;
            }
        }

        graphics.drawString(Direction.getDirection(inputMapping).toString(),70,0);
    }
}
