package com.GlitchyDev.Rendering.Elements;

import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.Texture;
import com.GlitchyDev.Rendering.Assets.Utils;

import java.util.ArrayList;
import java.util.List;

public class SpriteItem extends GameItem {

    public SpriteItem(Texture spriteTexture) {
        super();
        setMesh(buildMesh(spriteTexture));
    }

    private Mesh buildMesh(Texture spriteTexture) {
        List<Float> positions = new ArrayList();
        List<Float> textCoords = new ArrayList();
        float[] normals = new float[0];
        List<Integer> indices = new ArrayList();




        // Left Top vertex
        positions.add(0.0f); // x
        positions.add(0.0f); //y
        positions.add(0.0f); //z
        textCoords.add(0.0f);
        textCoords.add(0.0f);
        indices.add(0);

        // Left Bottom vertex
        positions.add(0.0f); // x
        positions.add((float) spriteTexture.getHeight()); //y
        positions.add(0.0f); //z
        textCoords.add(0.0f);
        textCoords.add(1.0f);
        indices.add(1);

        // Right Bottom vertex
        positions.add((float) spriteTexture.getWidth()); // x
        positions.add((float) spriteTexture.getHeight()); //y
        positions.add(0.0f); //z
        textCoords.add(1.0f);
        textCoords.add(1.0f);
        indices.add(2);

        // Right Top vertex
        positions.add((float) spriteTexture.getHeight()); // x
        positions.add(0.0f); //y
        positions.add(0.0f); //z
        textCoords.add(1.0f);
        textCoords.add(0.0f);
        indices.add(3);

        // Add indices por left top and bottom right vertices
        indices.add(0);
        indices.add(2);


        float[] posArr = Utils.listToArray(positions);
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i->i).toArray();
        Mesh mesh = new Mesh(posArr, textCoordsArr, normals, indicesArr);
        try {
            mesh.setTexture(spriteTexture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesh;
    }



}
