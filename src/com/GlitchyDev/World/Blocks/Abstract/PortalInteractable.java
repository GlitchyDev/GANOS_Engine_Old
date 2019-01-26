package com.GlitchyDev.World.Blocks.Abstract;

import com.GlitchyDev.World.Blocks.BlockType;
import com.GlitchyDev.World.Direction;
import com.GlitchyDev.World.Entities.EnemyEntity;
import com.GlitchyDev.World.Entities.EntityBase;
import com.GlitchyDev.World.Entities.MovementType;
import com.GlitchyDev.World.Entities.Player_LN;
import com.GlitchyDev.World.Location;

public class PortalInteractable extends BlockBase implements InteractableBlock {
    private boolean isOpen = false;
    private Location targetLocation;
    private HallwayIdentificationBlock currentHallway;
    private HallwayIdentificationBlock targetHallway;

    public PortalInteractable(BlockType blockType, Location location, Location targetLocation, HallwayIdentificationBlock currentHallway, HallwayIdentificationBlock nextHallway) {
        super(BlockType.PORTAL, location);
        this.targetLocation = targetLocation;
        this.currentHallway = currentHallway;
        this.targetHallway = nextHallway;
    }


    @Override
    public boolean isUseless() {
        return false;
    }

    @Override
    public void rotate(Direction direction) {

    }

    @Override
    public void interact(EntityBase entity, BlockInteractionType blockInteractionType) {
        switch(blockInteractionType) {
            case USE_PORTAL:
                entity.forceMove(MovementType.TELEPORT,targetLocation,Direction.NORTH);
                if(entity instanceof Player_LN) {
                    currentHallway.setHasPlayer(false);
                    targetHallway.setHasPlayer(true);
                }
                if(entity instanceof EnemyEntity) {
                    currentHallway.getEnemyEntities().remove(entity);
                    targetHallway.getEnemyEntities().add((EnemyEntity) entity);
                }
                break;
            case OPEN_PORTAL:
                // Make Portal Blocks Open
                isOpen = true;
                break;
            case CLOSE_PORTAL:
                // Make Portal Blocks Close
                isOpen = false;

                break;
        }

    }
}
