package com.GlitchyDev.World.Blocks;

import com.GlitchyDev.World.BlockBase;
import com.GlitchyDev.World.BlockType;
import com.GlitchyDev.World.Location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BlankBlock extends BlockBase implements Serializable {

    public BlankBlock(BlockType blockType, Location location) {
        super(blockType, location);
    }



    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {

    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException
    {
        // Write Valid Block Bit
        aOutputStream.writeBoolean(false);
    }
}
