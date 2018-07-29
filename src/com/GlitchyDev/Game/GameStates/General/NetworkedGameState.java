package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.Networking.BasicSocket;
import com.GlitchyDev.Utility.GlobalGameDataBase;

public abstract class NetworkedGameState extends InputGameState {
    protected BasicSocket gameSocketBase;

    public NetworkedGameState(GlobalGameDataBase globalGameDataBase, BasicSocket gameSocketBase) {
        super(globalGameDataBase);
        this.gameSocketBase = gameSocketBase;
    }

    @Override
    public void doLogic() {
        // Code
        super.doLogic();
    }
}
