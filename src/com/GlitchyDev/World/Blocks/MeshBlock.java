package com.GlitchyDev.World.Blocks;

import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.World.BlockBase;
import com.GlitchyDev.World.BlockType;
import com.GlitchyDev.World.Location;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * A block with a specified mesh
 */
public class MeshBlock extends BlockBase {
    private Mesh mesh;

    public MeshBlock(BlockType blockType, Location location, Vector3f rotation, Mesh mesh) {
        super(BlockType.MESH, location, rotation);
        this.mesh = mesh;
    }

    @Override
    public boolean isUseless() {
        return true;
    }


    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
