package com.GlitchyDev.World;

import com.GlitchyDev.World.Blocks.BlankBlock;
import org.joml.Vector3f;

public class World {
    private final String worldName;
    private BlockBase[][][] blocks;
    // Entities

    public World(String worldName, int width, int length, int height) {
        this.worldName = worldName;
        blocks = new BlockBase[width][height][length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < length; z++) {
                    blocks[x][y][z] = new BlankBlock(new Location(),new Vector3f());
                }
            }
        }
    }

    public BlockBase getBlockAtLocation(Location location)
    {
        return blocks[location.getX()][location.getY()][location.getZ()];
    }

    public void setBlockAtLocation(BlockBase block, Location location)
    {
        blocks[location.getX()][location.getY()][location.getZ()] = block;
    }

    public String getWorldName() {
        return worldName;
    }
}
