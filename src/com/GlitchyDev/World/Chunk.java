package com.GlitchyDev.World;

import com.GlitchyDev.World.Blocks.BlankBlock;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class Chunk {
    // Store Y X Z so slices can be taken out
    private BlockBase[][][] blocks;
    private int chunkHeight;
    private final ChunkCord chunkCord;

    public Chunk(ChunkCord chunkCord)
    {
        blocks = new BlockBase[World.STANDARD_CHUNK_HEIGHT][World.STANDARD_CHUNK_SIDE_LENGTH][World.STANDARD_CHUNK_SIDE_LENGTH];
        chunkHeight = World.STANDARD_CHUNK_HEIGHT;
        this.chunkCord = chunkCord;
    }

    public Chunk(ChunkCord chunkCord, int height)
    {
        blocks = new BlockBase[height][World.STANDARD_CHUNK_SIDE_LENGTH][World.STANDARD_CHUNK_SIDE_LENGTH];
        chunkHeight = height;
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
        if(relativeY >= chunkHeight)
        {
            chunkHeight = relativeY+1;
            BlockBase[][][] blocksAdjustedHeight = new BlockBase[chunkHeight][blocks[0].length][blocks[0][0].length];
            int index = 0;
            for(BlockBase[][] area: blocks)
            {
                blocksAdjustedHeight[index] = area;
                index++;
            }
            blocks = blocksAdjustedHeight;
        }
        if(relativeY < 0)
        {
            return null;
        }
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
