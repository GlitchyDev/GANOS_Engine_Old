package com.GlitchyDev.Game.GameStates;

import com.GlitchyDev.GameInput.GameInput;
import com.GlitchyDev.GameInput.GameInputTimings;
import com.GlitchyDev.Utility.GlobalGameData;

public abstract class InputGameStateBase extends MonitoredGameStateBase {
    protected GameInput gameInput;
    protected GameInputTimings gameInputTimings;


    public InputGameStateBase(GlobalGameData globalGameDataBase, GameStateType gameStateType) {
        super(globalGameDataBase, gameStateType);
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
