package com.GlitchyDev.World;

import com.GlitchyDev.World.Blocks.BlankBlock;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class Chunk {
    // Store Y X Z so slices can be taken out
    private final BlockBase[][][] blocks;
    private final ChunkCord chunkCord;

    public Chunk(ChunkCord chunkCord)
    {
        blocks = new BlockBase[World.STANDARD_CHUNK_HEIGHT][World.STANDARD_CHUNK_SIDE_LENGTH][World.STANDARD_CHUNK_SIDE_LENGTH];
        for(int y = 0; y < World.STANDARD_CHUNK_HEIGHT; y++)
        {
            for(int x = 0; x < World.STANDARD_CHUNK_SIDE_LENGTH; x++)
            {
                for(int z = 0; z < World.STANDARD_CHUNK_SIDE_LENGTH; z++)
                {
                    setBlock(x,y,z,new BlankBlock(new Location(x + World.getPosNumFromChunkNum(chunkCord.getX()),y,z + World.getPosNumFromChunkNum(chunkCord.getZ()))));
                }
            }
        }

        this.chunkCord = chunkCord;
    }

    public Chunk(ChunkCord chunkCord, int height)
    {
        blocks = new BlockBase[height][World.STANDARD_CHUNK_SIDE_LENGTH][World.STANDARD_CHUNK_SIDE_LENGTH];
        this.chunkCord = chunkCord;
    }

    /* LOAD FROM IO

        public Chunk(ChunkCord chunkCord, int height) {
             blocks = new BlockBase[height][World.STANDARD_CHUNK_SIDE_LENGTH][World.STANDARD_CHUNK_SIDE_LENGTH];
             this.chunkCord = chunkCord;
         }
     */

    public BlockBase getBlock(int relativeX, int relativeY, int relativeZ)
    {
        return blocks[relativeY][relativeX][relativeZ];
    }



    public void setBlock(int relativeX, int relativeY, int relativeZ, BlockBase block)
    {
        blocks[relativeY][relativeX][relativeZ] = block;
    }

    public ChunkCord getChunkCord() {
        return chunkCord;
    }

    public BlockBase[][][] getBlocks() {
        return blocks;
    }


}
