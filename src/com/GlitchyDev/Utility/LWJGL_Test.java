package com.GlitchyDev.Utility;


import com.GlitchyDev.IO.AssetLoader;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class LWJGL_Test {


    public static void main(String[] args) {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }


        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        long window = glfwCreateWindow(500, 500, "Fuck you", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create Window");
        }


        // Retrieves Window Information GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, 100, 100);
        glfwShowWindow(window);


        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);


        AssetLoader.loadAssets();
        Texture main_Texture = new Texture(AssetLoader.getFile("Error.png"));
        Texture sub_Texture = new Texture(AssetLoader.getFile("Filter.png"));


        Shader shader = new Shader(AssetLoader.getFile("Test.vert"),AssetLoader.getFile("Test.frag"));

        // Make Sprites scale vertices to window size, knowing 1.0f is the width/height of the window, first cord is X
        float[] vertices = new float[]{
                -0.5f, 0.5f, 0,
                0.5f, 0.5f, 0f,
                0.5f, -0.5f, 0f,
                -0.5f, -0.5f, 0f,
        };

        float[] tex_coords = new float[] {
                0,0,
                1,0,
                1,1,
                0,1
        };

        int[] indices = new int[]{
                0,1,2,
                2,3,0,
        };

        Model model = new Model(vertices,tex_coords,indices);



        while(!glfwWindowShouldClose(window))
        {
            glfwPollEvents();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);



            shader.bind();
            shader.setUniform("main_Texture",0);
            shader.setUniform("sub_Texture",1);
            main_Texture.bind(0);
            sub_Texture.bind(1);


            model.render();

            





            glfwSwapBuffers(window);

            if(glfwGetKey(window,GLFW_KEY_1) == 1) {
                glfwSetWindowShouldClose(window,true);
            }
        }

        glfwTerminate();




    }





}
