package com.GlitchyDev.World.Entities;

import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.World.Direction;
import com.GlitchyDev.World.Location;

public abstract class MovingEntity extends EntityBase {
    private double movementProgress = 0.0;



    public MovingEntity(EntityType entityType, Location location) {
        super(entityType, location);
    }

    public void addModifier(double modifier) {
        movementProgress += modifier;

        if(modifier >= 1.0 || modifier <= 1.0) {
            System.out.println("FORCE MOVE");
            forceMove(MovementType.WALKING,getLocation().getOffsetLocation((int) movementProgress,0,0), Direction.WEST);
            movementProgress %= 1.0;
        }
    }

    public double getMovementProgress() {
        return movementProgress;
    }


    public abstract void completedMove();

}
