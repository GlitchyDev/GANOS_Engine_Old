package com.GlitchyDev.IO;

import com.GlitchyDev.World.Blocks.AbstractBlocks.BlockBase;
import com.GlitchyDev.World.Blocks.BlockType;
import com.GlitchyDev.World.Entities.AbstractEntities.EntityBase;
import com.GlitchyDev.World.Entities.EntityType;
import com.GlitchyDev.World.Location;
import com.GlitchyDev.World.Region;
import com.GlitchyDev.World.Utility.HuffmanTreeUtility;
import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RegionIO {
    private static final int CURRENT_VERSION = 1;
    private static final int LEAST_SUPPORTED_VERSION = 1;



    public static void writeRegion(File file, Region region) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));


    }

    public static Region readRegion(File file) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));

        // Use to confirm compatibility
        int versionNum = getCorrectedByteValue(objectInputStream.readByte());
        if(!(versionNum <= CURRENT_VERSION && versionNum >= LEAST_SUPPORTED_VERSION)) {
            System.out.println("RegionIO: Error, Version " + versionNum + " not supported");
        }
        /*
        // Use to get fileType, like XL palette, ect. Don't support until needed
        byte fileType = objectInputStream.readByte();
        */

        int width = objectInputStream.readByte();
        int length = objectInputStream.readByte();
        int height = objectInputStream.readByte();

        int blockPaletteSize = getCorrectedByteValue(objectInputStream.readByte());
        int entityPaletteSize = getCorrectedByteValue(objectInputStream.readByte());


        Object[] sharedPallete = new Object[blockPaletteSize + entityPaletteSize];
        int[] sharedFrequency = new int[blockPaletteSize + entityPaletteSize];

        for(int i = 0; i < blockPaletteSize; i++) {
            // Check Blocks
            BlockType blockType = BlockType.values()[getCorrectedByteValue(objectInputStream.readByte())];
            BlockBase block = blockType.getBlockClass();
            block.readData(objectInputStream);
            sharedPallete[i] = block;
            sharedFrequency[i] = getCorrectedByteValue(objectInputStream.readByte());
        }

        int entityCount = 0;
        for(int i = blockPaletteSize; i < blockPaletteSize + entityPaletteSize; i++) {
            // Add Entities
            EntityType entityType = EntityType.values()[getCorrectedByteValue(objectInputStream.readByte())];
            EntityBase entity = entityType.getEntityClass();
            entity.readData(objectInputStream);
            sharedPallete[i] = entity;
            sharedFrequency[i] = getCorrectedByteValue(objectInputStream.readByte());
            entityCount += sharedFrequency[i];

        }


        HashMap<String,Object> encodeMap = HuffmanTreeUtility.getHuffmanValues(sharedPallete,sharedFrequency);
        BlockBase[][][] blocks = new BlockBase[height][width][length];
        ArrayList<EntityBase> entities = new ArrayList<>(entityCount);

        byte currentByte = 0;
        int pos = 8;
        int blockCount = 0;

        int x = 0;
        int y = 0;
        int z = 0;
        for(int entryCount = 0; entryCount < (width * length * height) + entityCount; entityCount++) {
            String currentCode = "";


            while(!encodeMap.containsKey(currentCode)) {
                currentCode += getByteValue(currentByte,pos);
                pos++;
                if(pos == 8) {
                    currentByte = objectInputStream.readByte();
                }
            }
            Object readObject = encodeMap.get(currentCode);

            if(readObject instanceof BlockBase) {
                // REQUIRE CLONE
                BlockBase readBlock = ((BlockBase) readObject).clone();

                y = blockCount / (width * length);
                x = blockCount % width;
                z = blockCount / width;
                blocks[y][x][z] = readBlock;

                blockCount++;
            } else {
                if(readObject instanceof EntityBase) {
                    // REQUIRE CLONE
                    EntityBase readEntity = ((EntityBase) readObject).clone();
                    readEntity.setLocation(new Location(x,y,z,null));
                    // Set Entity Position from x y z from last read block
                    entities.add(readEntity);
                }
            }
            // Grab object read
        }





        // Make a huffington tree for both blocks and entities

        // make a giant buffer of boolean values or something so one can actually read the input incoming




        //

        return new Region(blocks, entities);
    }






    private static byte setByteValue(byte b, int pos) {
        if(pos == 0) {
            return b |= 1 << 7;
        }
        return b |= 1 << (pos-1);
    }

    private static boolean getByteValue(byte b, int pos) {
        return ((b >> (pos - 1)) & 1) == 1;
    }

    private static int getCorrectedByteValue(byte b) {
        return (b>=0) ? b : (127 + (129 + b));
    }
}
