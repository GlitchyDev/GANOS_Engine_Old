package com.GlitchyDev.Rendering.Assets.WorldElements;

import com.GlitchyDev.Rendering.Assets.Mesh;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * A object that can be represented in game in 3D or 2D ( See SpriteItem or HudItem )
 */
public class GameItem {

    private ArrayList<Mesh> meshes;
    
    private final Vector3f position;
    
    private float scale;

    private final Vector3f rotation;

    public GameItem(Mesh mesh) {
        meshes = new ArrayList<>();
        meshes.add(mesh);
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public GameItem() {
        meshes = new ArrayList<>();
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }

    public void setMeshes(ArrayList<Mesh> meshes) {
        this.meshes = meshes;
    }
}