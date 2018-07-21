package com.GlitchyDev.Utility;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow {

    private String title;
    private long windowHandle;

    private int width;
    private int height;

    private boolean resized;
    private boolean isVSync;

    private final int targetFPS = 60;

    public GameWindow(String title, int width, int height, boolean isVSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isVSync = isVSync;
        this.resized = false;
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Create the window
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        /*
        // Setup resize callback
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });
        */

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);

        if (isVSync()) {
            // Enable v-sync
            glfwSwapInterval(1);
        }

        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        //glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

        // Support for transparencies
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        setIcon(windowHandle, new File("GameAssets/Textures/Icon/Icon16x16.png") , new File("GameAssets/Textures/Icon/Icon24x24.png"));



    }

    public void showWindow()
    {
        glfwShowWindow(windowHandle);
    }

    /*
       Required at the beginning of each loop
    */
    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public void setIcon(long window, File icon1, File icon2) {

        BufferedImage img = null;
        BufferedImage img2 = null;
        try {
            img = ImageIO.read(icon1);
            img2 = ImageIO.read(icon2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GLFWImage image = GLFWImage.malloc();
        image.set(img.getWidth(), img.getHeight(), loadImageToByteBuffer(img));

        GLFWImage image2 = GLFWImage.malloc();
        image2.set(img2.getWidth(), img2.getHeight(), loadImageToByteBuffer(img2));


        GLFWImage.Buffer images = GLFWImage.malloc(2);
        images.put(0, image);
        images.put(1, image2);

        glfwSetWindowIcon(window, images);


        images.free();
        image.free();
        image2.free();
    }

    private ByteBuffer loadImageToByteBuffer(BufferedImage image) {
        final byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
        int counter = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                final int c = image.getRGB(j, i);
                buffer[counter + 0] = (byte) (c << 8 >> 24);
                buffer[counter + 1] = (byte) (c << 16 >> 24);
                buffer[counter + 2] = (byte) (c << 24 >> 24);
                buffer[counter + 3] = (byte) (c >> 24);
                counter += 4;
            }
        }
        ByteBuffer bb = BufferUtils.createByteBuffer(buffer.length);
        bb.put(buffer);
        bb.flip();
        return bb;
    }



    // Getter and Setters

    public long getWindowHandle() {
        return windowHandle;
    }

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean getWindowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public boolean isVSync() {
        return isVSync;
    }

    public int getTargetFPS() {
        return targetFPS;
    }
}