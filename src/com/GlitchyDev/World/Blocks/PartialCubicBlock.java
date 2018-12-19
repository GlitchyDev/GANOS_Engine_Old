package com.GlitchyDev.World.Blocks;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.InstancedGridTexture;
import com.GlitchyDev.World.BlockBase;
import com.GlitchyDev.World.BlockType;
import com.GlitchyDev.World.Location;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;

public class PartialCubicBlock extends BlockBase {
    // Assigned Pool Grid Texture
    private InstancedGridTexture instancedGridTexture;
    // Use as an internal identifier of textures for saving
    private String textureName;
    // Be 6 sized,
    private boolean[] faceStates;
    // -1 for non texture
    private int[] assignedTextures;
    //private ArrayList<String> modifiers;



    public PartialCubicBlock(Location location, ObjectInputStream aInputStream) throws IOException {
        super(BlockType.PARTIAL_CUBIC_BLOCK, location);
        //readObject(aInputStream);

    }

    public PartialCubicBlock(Location location, Vector3f rotation, InstancedGridTexture instancedGridTexture, String textureName, boolean[] faceStates, int[] assignedTextures)
    {
        super(BlockType.PARTIAL_CUBIC_BLOCK, location, rotation);
        this.instancedGridTexture = instancedGridTexture;
        this.textureName = textureName;
        this.faceStates = faceStates;
        this.assignedTextures = assignedTextures;
        //this.modifiers = modifiers;
    }

    public PartialCubicBlock(Location location, InstancedGridTexture instancedGridTexture, String textureName, boolean[] faceStates, int[] assignedTextures)
    {
        super(BlockType.PARTIAL_CUBIC_BLOCK, location);
        this.instancedGridTexture = instancedGridTexture;
        this.textureName = textureName;
        this.faceStates = faceStates;
        this.assignedTextures = assignedTextures;
        //this.modifiers = modifiers;
    }


    /** Undo once proper stuff has been made

    public void save(File file)
    {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
            writeObject(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readObject(ObjectInputStream aInputStream) throws IOException
    {
        // boolean already taken
        // BlockType already taken
        // Read 6 boolean state of present sides of our partial cube
        aInputStream.readBoolean();
        aInputStream.readInt();


        this.faceStates = new boolean[6];
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
        // Read Assigned Texture
        textureName = aInputStream.readUTF();
        int textureWidth = aInputStream.readInt();
        int textureHeight = aInputStream.readInt();
        this.instancedGridTexture = new InstancedGridTexture(AssetLoader.getTextureAsset(textureName),textureWidth, textureHeight);
        // Read Assigned texture to valid meshes
        this.assignedTextures = new int[validStates];
        // Loaded in from State 0->Max
        for(int i = 0; i < validStates; i++)
        {
            assignedTextures[i] = aInputStream.readInt();
        }
        // Required Since we can't determine the # of regiment modifiers

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
        aOutputStream.writeUTF(textureName);
        aOutputStream.writeInt(instancedGridTexture.getTextureGridWidth());
        aOutputStream.writeInt(instancedGridTexture.getTextureGridHeight());
        for(int i = 0; i < this.assignedTextures.length; i++)
        {
            aOutputStream.writeInt(assignedTextures[i]);
        }

    }

    **/


    public boolean[] getFaceStates() {
        return faceStates;
    }

    public int[] getAssignedTextures() {
        return assignedTextures;
    }

    public void setFaceStates(boolean[] faceStates) {
        this.faceStates = faceStates;
    }

    public void setTopFaceState(boolean state)
    {
        faceStates[0] = state;
    }
    public void setBottomFaceState(boolean state)
    {
        faceStates[1] = state;
    }
    public void setNorthFaceState(boolean state)
    {
        faceStates[2] = state;
    }
    public void setEastFaceState(boolean state)
    {
        faceStates[3] = state;
    }
    public void setSouthFaceState(boolean state)
    {
        faceStates[4] = state;
    }
    public void setWestFaceState(boolean state)
    {
        faceStates[5] = state;
    }

    public void setAssignedTextures(int[] assignedTextures) {
        this.assignedTextures = assignedTextures;
    }

    public void setTopTexture(int texture)
    {
        assignedTextures[0] = texture;
    }
    public void setBottomTexture(int texture)
    {
        assignedTextures[1] = texture;
    }
    public void setNorthTexture(int texture)
    {
        assignedTextures[2] = texture;
    }
    public void setEastTexture(int texture)
    {
        assignedTextures[3] = texture;
    }
    public void setSouthTexture(int texture)
    {
        assignedTextures[4] = texture;
    }
    public void setWestTexture(int texture)
    {
        assignedTextures[5] = texture;
    }
    /*
    public ArrayList<String> getModifiers() {
        return modifiers;
    }

    public void setModifiers(ArrayList<String> modifiers) {
        this.modifiers = modifiers;
    }
    */
}
