package com.GlitchyDev.GameStates;

import com.GlitchyDev.Networking.NetworkDisconnectType;
import com.GlitchyDev.Networking.ServerNetworkConnection;
import com.GlitchyDev.Utility.BasicMonitoredGameState;
import com.GlitchyDev.Utility.GButtons;
import com.GlitchyDev.Utility.GameController;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Collection;
import java.util.Iterator;

public class NetworkServerState extends BasicMonitoredGameState {
    ServerNetworkConnection serverNetworkConnection;
    private Collection<String> currentUsers;
    private Collection<String> approvedUsers;

    public NetworkServerState(ServerNetworkConnection serverNetworkConnection)
    {
        this.serverNetworkConnection = serverNetworkConnection;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        currentUsers = serverNetworkConnection.getConnectedUsers();
        approvedUsers = serverNetworkConnection.getApprovedUsers();
    }

    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {


        graphics.setColor(Color.white);
        graphics.drawString("Connected Users",0,0);
        int i = 1;
        Iterator<String> currentUserIterator = currentUsers.iterator();
        while(currentUserIterator.hasNext())
        {
            graphics.drawString(currentUserIterator.next(),0,20*i);
            i++;
        }
        graphics.drawString("Whitelist",150,0);
        i = 1;
        Iterator<String> approvedUserIterator = approvedUsers.iterator();
        while(approvedUserIterator.hasNext())
        {
            graphics.drawString(approvedUserIterator.next(),150,20*i);
            i++;
        }

        graphics.setColor(Color.blue);
        graphics.drawString(String.valueOf(getTotalUtilization()),400,0);

    }

    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        serverNetworkConnection.updateConnections();
        if(GameController.isButtonPressed(GButtons.START))
        {
            serverNetworkConnection.disconnectAll(NetworkDisconnectType.DEBUG);
        }
    }

    @Override
    public int getID() {
        return 0;
    }



}
