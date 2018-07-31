package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.General.GameInput;
import com.GlitchyDev.General.GameInputTimings;
import com.GlitchyDev.Utility.GlobalGameData;

public abstract class InputGameState extends MonitoredGameState {
    protected GameInput gameInput;
    protected GameInputTimings gameInputTimings;


    public InputGameState(GlobalGameData globalGameDataBase) {
        super(globalGameDataBase);
        gameInput = new GameInput();
        gameInput.bind(getWindowHandle());
        gameInputTimings = new GameInputTimings(gameInput);
    }


    @Override
    public void doLogic() {
        gameInputTimings.updateTimings();
        super.doLogic();
    }
}
