package com.GlitchyDev.World.Blocks;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.World.BlockBase;
import com.GlitchyDev.World.BlockType;
import com.GlitchyDev.World.Location;

import java.io.*;
import java.util.ArrayList;

public class PartialCubicBlock extends BlockBase {
    private Boolean[] faceStates;
    private String[] assignedTextures;
    private ArrayList<String> modifiers;


    public PartialCubicBlock(Location location, ObjectInputStream aInputStream) throws IOException, ClassNotFoundException {
        super(BlockType.PARTIAL_CUBIC_BLOCK, location);
        readObject(aInputStream);
        buildMeshes();

    }

    public PartialCubicBlock(ObjectInputStream aInputStream) throws IOException, ClassNotFoundException {
        super(BlockType.PARTIAL_CUBIC_BLOCK, new Location());
        readObject(aInputStream);
        buildMeshes();
    }

    public PartialCubicBlock(Location location, Boolean[] faceStates, String[] assignedTextures, ArrayList<String> modifiers)
    {
        super(BlockType.PARTIAL_CUBIC_BLOCK, location);
        this.faceStates = faceStates;
        this.assignedTextures = assignedTextures;
        this.modifiers = modifiers;
        buildMeshes();
    }

    public PartialCubicBlock(Boolean[] faceStates, String[] assignedTextures, ArrayList<String> modifiers)
    {
        super(BlockType.PARTIAL_CUBIC_BLOCK, new Location());
        this.faceStates = faceStates;
        this.assignedTextures = assignedTextures;
        this.modifiers = modifiers;
        buildMeshes();
    }

    public void save()
    {
        File file = new File("GameAssets/Configs/PartialCubicBlockDebug.configList");
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
            writeObject(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {
        // boolean already taken
        // BlockType already taken
        // Read 6 boolean state of present sides of our partial cube
        aInputStream.readBoolean();
        aInputStream.readInt();


        this.faceStates = new Boolean[6];
        // The number of present sides, we only have that many textures total
        int validStates = 0;
        for(int i = 0; i < 6; i++)
        {
            faceStates[i] = aInputStream.readBoolean();
            if(faceStates[i])
            {
                validStates++;
            }
        }
        this.assignedTextures = new String[validStates];
        // Loaded in from State 0->Max
        for(int i = 0; i < validStates; i++)
        {
            assignedTextures[i] = aInputStream.readUTF();
        }
        // Required Since we can't determine the # of regiment modifiers
        int numModifiers = aInputStream.readInt();
        this.modifiers = new ArrayList<>();
        for(int i = 0; i < numModifiers; i++)
        {
            modifiers.add(aInputStream.readUTF());
        }
    }

    public void writeObject(ObjectOutputStream aOutputStream) throws IOException
    {
        // Write Valid Block Bit
        aOutputStream.writeBoolean(true);
        // Write BlockType
        aOutputStream.writeInt(this.blockType.ordinal());

        for(int i = 0; i < 6; i++)
        {
            aOutputStream.writeBoolean(this.faceStates[i]);
        }
        for(int i = 0; i < this.assignedTextures.length; i++)
        {
            aOutputStream.writeUTF(assignedTextures[i]);
        }
        aOutputStream.writeInt(this.modifiers.size());
        for(String modifier: modifiers)
        {
            aOutputStream.writeUTF(modifier);
        }
    }

    @Override
    public void buildMeshes() {
        // Implement Valid Sides
        int currentValidFaces = 0;
        for(int i = 0; i < 6; i++)
        {
            if(faceStates[i])
            {
                getMeshes().add(AssetLoader.getMeshAsset("CubicMesh" + (i+1)));
                getMeshes().get(currentValidFaces).setTexture(AssetLoader.getTextureAsset(assignedTextures[currentValidFaces]));
                currentValidFaces++;
            }
        }
    }
}
