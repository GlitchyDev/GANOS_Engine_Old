package com.GlitchyDev.World.Blocks;

import com.GlitchyDev.World.BlockBase;
import com.GlitchyDev.World.BlockType;
import com.GlitchyDev.World.Location;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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
