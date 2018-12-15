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
    private InstancedGridTexture instancedGridTexture;
    private String textureName;
    private boolean[] faceStates;
    private int[] assignedTextures;
    //private ArrayList<String> modifiers;



    public PartialCubicBlock(Location location, Vector3f rotation, ObjectInputStream aInputStream) throws IOException {
        super(BlockType.PARTIAL_CUBIC_BLOCK, location, rotation);
        readObject(aInputStream);

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

    public PartialCubicBlock(Location location, Vector3f rotation, InstancedGridTexture instancedGridTexture, String textureName, boolean[] faceStates, int[] assignedTextures, ArrayList<String> modifiers)
    {
        super(BlockType.PARTIAL_CUBIC_BLOCK, location, rotation);
        this.instancedGridTexture = instancedGridTexture;
        this.textureName = textureName;
        this.faceStates = faceStates;
        this.assignedTextures = assignedTextures;
        //this.modifiers = modifiers;
    }



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

        /*
        int numModifiers = aInputStream.readInt();
        this.modifiers = new ArrayList<>();
        for(int i = 0; i < numModifiers; i++)
        {
            modifiers.add(aInputStream.readUTF());
        }
        */
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

        /*
        aOutputStream.writeInt(this.modifiers.size());
        for(String modifier: modifiers)
        {
            aOutputStream.writeUTF(modifier);
        }
        */
    }


    public boolean[] getFaceStates() {
        return faceStates;
    }

    public int[] getAssignedTextures() {
        return assignedTextures;
    }

    public void setFaceStates(boolean[] faceStates) {
        this.faceStates = faceStates;
    }

    public void setAssignedTextures(int[] assignedTextures) {
        this.assignedTextures = assignedTextures;
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
