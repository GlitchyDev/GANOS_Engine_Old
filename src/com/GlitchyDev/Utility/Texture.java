package com.GlitchyDev.Utility;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(File texture)
    {
        try {
            BufferedImage bufferedImage = ImageIO.read(texture);
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();

            int[] pixels_raw = new int[width*height];
            pixels_raw = bufferedImage.getRGB(0,0,width,height,null,0,width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    int pixel = pixels_raw[y*width+x];
                    pixels.put((byte)((pixel >> 16) & 0xFF)); // Red
                    pixels.put((byte)((pixel >> 8) & 0xFF)); // Green
                    pixels.put((byte)((pixel) & 0xFF)); // Blue
                    pixels.put((byte)((pixel >> 24) & 0xFF)); // Alpha
                }
            }

            pixels.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D,id);
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST); // Linair???
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void bind(int sampler)
    {
        if(sampler >= 0 && sampler <= 31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }


}
