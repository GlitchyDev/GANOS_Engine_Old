package com.GlitchyDev.GameStates;

import com.GlitchyDev.Networking.GameSocket;
import com.GlitchyDev.Networking.Packets.PacketBase;
import com.GlitchyDev.Networking.Packets.PacketType;
import com.GlitchyDev.Networking.ServerNetworkConnection;
import com.GlitchyDev.Utility.BasicMonitoredGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Iterator;

public class NetworkServerState extends BasicMonitoredGameState {
    private ServerNetworkConnection serverNetworkConnection;



    public NetworkServerState(ServerNetworkConnection serverNetworkConnection)
    {
        this.serverNetworkConnection = serverNetworkConnection;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Iterator<String> itr = serverNetworkConnection.getAllConnectedClients().keys().asIterator();


        //System.out.println(serverNetworkConnection.getAllConnectedClients().size());
        graphics.setColor(Color.red);
        graphics.drawString("Connected Clients:",0,0);
        int i = 1;
        while (itr.hasNext()) {
            String key = itr.next();
            graphics.setColor(Color.red);
            graphics.drawString(key,0,i*20);
            i++;
        }

    }

    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        Iterator<String> uuidIterator = serverNetworkConnection.getAllConnectedClients().keys().asIterator();
        while(uuidIterator.hasNext())
        {
            String uuid = uuidIterator.next();
            if(!serverNetworkConnection.getAllConnectedClients().get(uuid).isConnected())
            {
                serverNetworkConnection.getAllConnectedClients().remove(uuid);
            }
        }

        for(GameSocket gameSocket: serverNetworkConnection.getAllConnectedClients().values())
        {
            // Get packets
            if(gameSocket.getUnprocessedPackets().size() != 0)
            {
                Iterator<PacketBase> packets = gameSocket.getUnprocessedPackets().iterator();
                while(packets.hasNext())
                {
                    PacketBase packet = packets.next();
                    // Do packet Stuff
                }
            }
        }
    }

    @Override
    public int getID() {
        return 0;
    }

}
