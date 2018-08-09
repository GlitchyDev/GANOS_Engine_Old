package com.GlitchyDev.Rendering;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.ShaderProgram;
import com.GlitchyDev.Rendering.WorldElements.*;
import com.GlitchyDev.Utility.GameWindow;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation transformation;

    private ShaderProgram environmentShaderProgram;

    private ShaderProgram hudShaderProgram;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init() {
        // Create shader

        setupEnvironmentShader();
        setupHudShader();
    }


    private void setupEnvironmentShader() {
        try {
            environmentShaderProgram = new ShaderProgram();
            environmentShaderProgram.createVertexShader(AssetLoader.getVertexAsset("Default3D"));
            environmentShaderProgram.createFragmentShader(AssetLoader.getFragmentAsset("Default3D"));
            environmentShaderProgram.link();

            // Create uniforms for modelView and projection matrices and texture
            environmentShaderProgram.createUniform("projectionMatrix");
            environmentShaderProgram.createUniform("modelViewMatrix");
            environmentShaderProgram.createUniform("texture_sampler");
            // Create uniform for default colour and the flag that controls it

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupHudShader() {
        try {
            hudShaderProgram = new ShaderProgram();
            hudShaderProgram.createVertexShader(AssetLoader.getVertexAsset("Default2D"));
            hudShaderProgram.createFragmentShader(AssetLoader.getFragmentAsset("Default2D"));
            hudShaderProgram.link();

            // Create uniforms for Ortographic-model projection matrix and base colour
            hudShaderProgram.createUniform("texture_sampler");
            hudShaderProgram.createUniform("projModelMatrix");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(GameWindow window, Camera camera, GameItem[] gameItems, TextItem[] hudItems, SpriteItem[] spriteItems) {
        clear();

        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        renderEnvironment(window,camera,gameItems);
        renderHUD(window, hudItems);
        renderSprites(window,spriteItems);

    }


    private void renderEnvironment(GameWindow window, Camera camera, GameItem[] gameItems)
    {
        environmentShaderProgram.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        environmentShaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        environmentShaderProgram.setUniform("texture_sampler", 0);
        // Render each gameItem
        for(GameItem gameItem : gameItems) {
            Mesh mesh = gameItem.getMesh();
            // Set model view matrix for this item
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
            environmentShaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            // Render the mesh for this game item

            mesh.render();

        }

        environmentShaderProgram.unbind();
    }




    private void renderHUD(GameWindow window, TextItem[] hudItems)
    {
        hudShaderProgram.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);

        hudShaderProgram.setUniform("texture_sampler", 0);


        for (GameItem gameItem : hudItems) {

            Mesh mesh = gameItem.getMesh();
            // Set ortohtaphic and model matrix for this HUD item
            Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(gameItem, ortho);
            hudShaderProgram.setUniform("projModelMatrix", projModelMatrix);
            mesh.render();


        }


        hudShaderProgram.unbind();
    }

    private void renderSprites(GameWindow window, SpriteItem[] spriteItems)
    {
        hudShaderProgram.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);

        hudShaderProgram.setUniform("texture_sampler", 0);


        for (GameItem gameItem : spriteItems) {

            Mesh mesh = gameItem.getMesh();
            // Set ortohtaphic and model matrix for this HUD item
            Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(gameItem, ortho);
            hudShaderProgram.setUniform("projModelMatrix", projModelMatrix);
            mesh.render();


        }


        hudShaderProgram.unbind();
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
