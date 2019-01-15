package com.GlitchyDev.World;


/**
 * An Rectangle of blocks and entities that can be loaded into the world map. Used to load/build structures
 */
public class Region {
    private BlockBase[][][] blocks;
    // Y X Z
    // Entities

    public Region(BlockBase[][][] blocks) {
        this.blocks = blocks;
    }

    public BlockBase[][][] getBlocks() {
        return blocks;
    }


    // Test

}
