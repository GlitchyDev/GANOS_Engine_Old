package com.GlitchyDev.Game.GameStates.General;

import com.GlitchyDev.Networking.Sockets.GameSocketBase;
import com.GlitchyDev.Utility.GlobalDataBase;

public abstract class NetworkedGameState extends MonitoredGameState {
    protected GameSocketBase gameSocketBase;

    public NetworkedGameState(GlobalDataBase globalDataBase, GameSocketBase gameSocketBase) {
        super(globalDataBase);
        this.gameSocketBase = gameSocketBase;
    }
}
