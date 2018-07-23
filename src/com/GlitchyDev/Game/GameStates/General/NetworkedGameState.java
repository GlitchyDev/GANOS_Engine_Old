package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.Networking.Sockets.GameSocketBase;
import com.GlitchyDev.Utility.GlobalDataBase;

public abstract class NetworkedGameState extends InputGameState {
    protected GameSocketBase gameSocketBase;

    public NetworkedGameState(GlobalDataBase globalDataBase, GameSocketBase gameSocketBase) {
        super(globalDataBase);
        this.gameSocketBase = gameSocketBase;
    }

    @Override
    public void doLogic() {
        // Code
        super.doLogic();
    }
}
