package com.GlitchyDev.World.Blocks.Abstract;

import com.GlitchyDev.World.Blocks.BlockType;
import com.GlitchyDev.World.Direction;
import com.GlitchyDev.World.Entities.EnemyEntity;
import com.GlitchyDev.World.Location;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public class HallwayIdentificationBlock extends BlockBase {
    private final int hallwayLength;
    private HashMap<HallwayIdentificationBlock,Location> connectedHallways;
    private ArrayList<EnemyEntity> enemyEntities;
    private boolean hasPlayer = false;

    public HallwayIdentificationBlock(Location location, int hallwayLength) {
        super(BlockType.HALLWAY_IDENTIFICATION, location);
        this.hallwayLength = hallwayLength;
        connectedHallways = new HashMap<>();
        enemyEntities = new ArrayList<>();
    }

    @Override
    public boolean isUseless() {
        return false;
    }

    @Override
    public void rotate(Direction direction) {

    }

    public void registerConnection(HallwayIdentificationBlock hallwayIdentificationBlock, Location location) {
        connectedHallways.put(hallwayIdentificationBlock,location);
    }

    public int getHallwayLength() {
        return hallwayLength;
    }

    public ArrayList<EnemyEntity> getEnemyEntities() {
        return enemyEntities;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }
}
