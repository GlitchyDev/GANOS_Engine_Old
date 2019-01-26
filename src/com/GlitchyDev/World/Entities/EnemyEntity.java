package com.GlitchyDev.World.Entities;

import com.GlitchyDev.Rendering.Assets.WorldElements.Camera;
import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.World.Location;

public class EnemyEntity extends MovingEntity{
    public EnemyEntity(EntityType entityType, Location location) {
        super(entityType, location);
    }


    @Override
    public void render(Renderer renderer, GameWindow gameWindow, Camera camera) {

    }

    @Override
    public void tick() {

    }

    @Override
    public void completedMove() {

    }
}
