package com.GlitchyDev.World;

import com.GlitchyDev.World.Blocks.BlankBlock;
import org.joml.Vector3f;

import java.util.HashMap;

public class World {
    private final String worldName;
    private HashMap<String,Chunk> chunks;

    public static final int STANDARD_CHUNK_SIDE_LENGTH = 10;
    public static final int STANDARD_CHUNK_HEIGHT = 5;
    // Entities

    public World(String worldName) {
        this.worldName = worldName;
        chunks = new HashMap<>();
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean doesChunkExist(ChunkCord chunkCord)
    {
        return chunks.containsKey(chunkCord.toString());
    }

    public Chunk getChunk(ChunkCord chunkCord) {
        return chunks.get(chunkCord.toString());
    }

    public void addChunk(ChunkCord chunkCord, Chunk chunk)
    {
        chunks.put(chunkCord.toString(),chunk);
    }

    private final ChunkCord chunkCord = new ChunkCord(0,0);
    public void setBlock(Location location, BlockBase block)
    {
        System.out.print(location.getX() + " " + location.getY() + " " + location.getZ());
        int chunkX = getChunkNumfromCordNum(location.getX());
        int chunkZ = getChunkNumfromCordNum(location.getZ());
        System.out.println(" " + chunkX + " " + chunkZ + " ");
        chunkCord.setX(chunkX);
        chunkCord.setZ(chunkZ);
        if(!doesChunkExist(chunkCord))
        {
            addChunk(chunkCord,new Chunk(chunkCord));
        }
        getChunk(chunkCord).setBlock(Math.abs(location.getX() - getPosNumFromChunkNum(chunkX)), location.getY(), Math.abs(location.getZ() - getPosNumFromChunkNum(chunkZ)), block);

    }

    public BlockBase getBlock(Location location)
    {
        int chunkX = getChunkNumfromCordNum(location.getX());
        int chunkZ = getChunkNumfromCordNum(location.getZ());
        System.out.println(" " + chunkX + " " + chunkZ + " ");
        chunkCord.setX(chunkX);
        chunkCord.setZ(chunkZ);
        if(!doesChunkExist(chunkCord))
        {
            addChunk(chunkCord,new Chunk(chunkCord));
        }
        return getChunk(chunkCord).getBlock(Math.abs(location.getX() - getPosNumFromChunkNum(chunkX)), location.getY(), Math.abs(location.getZ() - getPosNumFromChunkNum(chunkZ)));
    }

    public static int getChunkNumfromCordNum(int z)
    {
        boolean isNeg = z < 0;
        if((z / World.STANDARD_CHUNK_SIDE_LENGTH == 0))
        {
            return (isNeg ? -1 : 0);
        }
        else
        {
            return (isNeg ? z / World.STANDARD_CHUNK_SIDE_LENGTH + -1 : z / World.STANDARD_CHUNK_SIDE_LENGTH);
        }

    }

    public static int getPosNumFromChunkNum(int z)
    {
        if(z == 0)
        {
            return 0;
        }
        else {
            if(z >= 0)
            {
                return z * World.STANDARD_CHUNK_SIDE_LENGTH;
            }
            else
            {
                return z * World.STANDARD_CHUNK_SIDE_LENGTH + 1;
            }

        }
    }

    public HashMap<String, Chunk> getChunks() {
        return chunks;
    }
}
