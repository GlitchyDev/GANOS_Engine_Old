package com.GlitchyDev.World.Blocks;

import com.GlitchyDev.World.Blocks.Abstract.BlockBase;
import com.GlitchyDev.World.Location;
import org.joml.Vector3f;

/**
 * A unit representation of air, however since air is currently left NULL on arrays, such use is currently not required
 */
public class BlankBlock extends BlockBase {

    public BlankBlock(Location location, Vector3f rotation) {
        super(BlockType.NONE, location, rotation);
    }

    public BlankBlock(Location location) {
        super(BlockType.NONE, location);
    }

    @Override
    public boolean isUseless() {
        return false;
    }



}
