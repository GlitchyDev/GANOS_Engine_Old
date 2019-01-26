package com.GlitchyDev.World.Entities;

public enum EntityType {
    DEBUG,
    PLAYER,
    STATIONARY,
    ENEMY;


    public boolean hasAI() {
        return false;
    }

}
