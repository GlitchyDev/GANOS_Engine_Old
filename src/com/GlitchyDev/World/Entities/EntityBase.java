package com.GlitchyDev.World.Entities;

import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.World.Direction;
import com.GlitchyDev.World.Location;

public abstract class EntityBase {
    private final EntityType entityType;
    private Location location;
    private Direction direction;

    public EntityBase(EntityType entityType, Location location) {
        this.entityType = entityType;
        this.location = location;
        this.direction = Direction.NORTH;
    }

    public EntityBase(EntityType entityType, Location location, Direction direction) {
        this.entityType = entityType;
        this.location = location;
        this.direction = direction;
    }


    /**
     * Implement Rendering Logic
     */
    public abstract void render(Renderer renderer);

    public abstract void tick();
}
