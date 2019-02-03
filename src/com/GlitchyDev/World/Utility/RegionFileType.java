package com.GlitchyDev.World.Utility;

public enum RegionFileType {
    NORMAL,
    LARGE_DIMENSIONS,
    XL_PALETTE,
    LARGE_DIMENSIONS_XL_PALETTE;


    public boolean useLargePallete() {
        return this == XL_PALETTE || this == LARGE_DIMENSIONS_XL_PALETTE;
    }

    public boolean useLargeDimensions() {
        return this == LARGE_DIMENSIONS || this == LARGE_DIMENSIONS_XL_PALETTE;
    }

}
