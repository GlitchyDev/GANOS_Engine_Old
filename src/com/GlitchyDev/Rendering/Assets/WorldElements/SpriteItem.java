package com.GlitchyDev.Rendering.Assets.WorldElements;

import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.RenderBuffer;
import com.GlitchyDev.Rendering.Assets.Texture;
import com.GlitchyDev.Rendering.Assets.Utils;

import java.util.ArrayList;

/**
 * A GameItem that loads a Texture for use as a 2D image
 */
public class SpriteItem extends GameItem {


    public SpriteItem(Texture spriteTexture, boolean loadedTexture) {
        super();
        getMeshes().add(buildMesh(spriteTexture,loadedTexture));
    }

    /**
     * Use for loading Render Buffers as Textures
     * @param renderBuffer
     */
    public SpriteItem(RenderBuffer renderBuffer) {
        super();
        getMeshes().add(buildMesh(new Texture(renderBuffer),false));
    }


    private Mesh buildMesh(Texture spriteTexture, boolean loadedTexture) {
        ArrayList<Float> positions = new ArrayList();
        ArrayList<Float> textCoords = new ArrayList();
        ArrayList<Integer> indices = new ArrayList();




        // Left Top vertex
        positions.add(0.0f); // x
        positions.add(0.0f); //y
        positions.add(0.0f); //z
        textCoords.add(0.0f);
        textCoords.add(1.0f);
        indices.add(0);

        // Left Bottom vertex
        positions.add(0.0f); // x
        positions.add((float) spriteTexture.getHeight()); //y
        positions.add(0.0f); //z
        textCoords.add(0.0f);
        textCoords.add(0.0f);
        indices.add(1);

        // Right Bottom vertex
        positions.add((float) spriteTexture.getWidth()); // x
        positions.add((float) spriteTexture.getHeight()); //y
        positions.add(0.0f); //z
        textCoords.add(1.0f);
        textCoords.add(0.0f);
        indices.add(2);

        // Right Top vertex
        positions.add((float) spriteTexture.getWidth()); // x
        positions.add(0.0f); //y
        positions.add(0.0f); //z
        textCoords.add(1.0f);
        textCoords.add(1.0f);
        indices.add(3);

        // Add indices por left top and bottom right vertices
        indices.add(0);
        indices.add(2);


        float[] posArr = Utils.listToArray(positions);
        if(loadedTexture)
        {
            for(int i = 0; i < textCoords.size()/4; i++) {
                float bufferX = textCoords.get(0 + i * 4);
                float bufferY = textCoords.get(1 + i * 4);
                textCoords.set(0 + i * 4,textCoords.get(2 + i * 4));
                textCoords.set(1 + i * 4,textCoords.get(3 + i * 4));
                textCoords.set(2 + i * 4,bufferX);
                textCoords.set(3 + i * 4,bufferY);
            }
        }
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i->i).toArray();
        Mesh mesh = new Mesh(posArr, textCoordsArr, indicesArr);
        try {
            mesh.setTexture(spriteTexture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesh;
    }



}
