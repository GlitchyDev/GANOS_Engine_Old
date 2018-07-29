package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Utility.GlobalGameDataBase;

public abstract class MonitoredGameState extends GameStateBase {
    protected int currentFPS = 0;
    private int fpsCount = 0;
    private long lastFPSCount = 0;
    protected double renderUtilization = 0.0;
    protected double logicUtilization = 0.0;

    public MonitoredGameState(GlobalGameDataBase globalGameDataBase) {
        super(globalGameDataBase);
    }

    // Abstract Functions

    public abstract void render();

    public abstract void logic();

    // Functions
    public void doRender() {
        long renderStart = System.nanoTime();
        render();
        long renderEnd = System.nanoTime();
        renderUtilization = (100.0)/(1000000000.0/60.0) * (renderEnd-renderStart);

        fpsCount++;
        if(System.currentTimeMillis() > lastFPSCount + 1000)
        {
            currentFPS = fpsCount;
            fpsCount = 0;
            lastFPSCount = System.currentTimeMillis();
        }
    }

    public void doLogic() {
        long logicStart = System.nanoTime();
        logic();
        long logicEnd = System.nanoTime();
        logicUtilization = (100.0)/(1000000000.0/60.0) * (logicEnd-logicStart);
    }

    @Override
    public void enterState(GameStateType previousGameState) {
        resetTimings();
    }

    private void resetTimings()
    {
        lastFPSCount = System.currentTimeMillis();
        currentFPS = 0;
        fpsCount = 0;
        renderUtilization = 0;
        logicUtilization = 0;

    }

    // Getters
    public int getCurrentFPS() {
        return currentFPS;
    }

    public int getFPSCount() {
        return fpsCount;
    }

    public double getRenderUtilization() {
        return renderUtilization;
    }

    public double getLogicUtilization() {
        return logicUtilization;
    }
}
