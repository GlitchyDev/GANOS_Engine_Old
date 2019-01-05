package com.GlitchyDev.World;

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
