package com.GlitchyDev.World.Entities;

public class EntityBase {
    // hasAuthority ( If its a Standalone Client, it has it, if its a Server controlling a non player object it has it, Client with Player
    public boolean hasAuthority;
    // If its connected, either to a server or a client
    public boolean isNetworked;

    public boolean isServer;

    public void doLogic()
    {
        if(hasAuthority)
        {
            // Do AI
            if(isNetworked) {
                // Send any changes to the connected party
            }
        }
        // do animation
    }

    public void authorityLogic()
    {
        // AI, player controls
    }

    public void networkingLogic()
    {
        // Send update packets
        // Replication?
    }

    public void setLocation()
    {

        if(isNetworked) {
            // Send packet
        }
    }

    public void generalLogic()
    {
        // Animations, shading adjustments
    }

    public void doRender()
    {
        // Debug
    }


}
