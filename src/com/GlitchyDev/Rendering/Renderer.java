package com.GlitchyDev.Rendering;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.ShaderProgram;
import com.GlitchyDev.Rendering.WorldElements.*;
import com.GlitchyDev.Utility.GameWindow;
import org.joml.Matrix4f;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation transformation = new Transformation();

    private ShaderProgram environmentShaderProgram;

    private ShaderProgram hudShaderProgram;

    private HashMap<String,ShaderProgram> loadedShaders = new HashMap<>();

    public Renderer() {
        for(String shaderName: AssetLoader.getConfigListAsset("Shaders"))
        {
            try {
                loadedShaders.put(shaderName,new ShaderProgram(shaderName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void prepRender(GameWindow window)
    {
        clear();
        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }


    public void render3DElements(GameWindow window, String shaderName, Camera camera, GameItem[] gameItems)
    {
        ShaderProgram shader = loadedShaders.get(shaderName);
        shader.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shader.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        shader.setUniform("texture_sampler", 0);
        // Render each gameItem
        for(GameItem gameItem : gameItems) {
            Mesh mesh = gameItem.getMesh();
            // Set model view matrix for this item
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
            shader.setUniform("modelViewMatrix", modelViewMatrix);
            // Render the mesh for this game item
            mesh.render();
        }

        shader.unbind();
    }




    public void renderHUD(GameWindow window, String shaderName, TextItem[] hudItems)
    {
        ShaderProgram shader = loadedShaders.get(shaderName);
        shader.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);

        shader.setUniform("texture_sampler", 0);


        for (GameItem gameItem : hudItems) {

            Mesh mesh = gameItem.getMesh();
            // Set ortohtaphic and model matrix for this HUD item
            Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(gameItem, ortho);
            shader.setUniform("projModelMatrix", projModelMatrix);
            mesh.render();
        }


        shader.unbind();
    }

    public void renderSprites(GameWindow window, String shaderName, SpriteItem[] spriteItems)
    {
        ShaderProgram shader = loadedShaders.get(shaderName);
        shader.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);

        shader.setUniform("texture_sampler", 0);


        for (GameItem gameItem : spriteItems) {

            Mesh mesh = gameItem.getMesh();
            // Set ortohtaphic and model matrix for this HUD item
            Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(gameItem, ortho);
            shader.setUniform("projModelMatrix", projModelMatrix);
            mesh.render();

        }


        shader.unbind();
    }

    public void cleanup() {
        if (environmentShaderProgram != null) {
            environmentShaderProgram.cleanup();
        }
        if (hudShaderProgram != null) {
            hudShaderProgram.cleanup();
        }
    }
}
