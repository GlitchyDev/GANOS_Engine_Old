package com.GlitchyDev.graph;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(String fileName) {
        // Load Texture file
        try {
            PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(fileName));
            width = decoder.getWidth();
            height = decoder.getHeight();

            // Load texture contents into a byte buffer
            ByteBuffer buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();

            // Create a new OpenGL texture
            id = glGenTextures();
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, id);

            // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            // Upload the texture data
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0,
                    GL_RGBA, GL_UNSIGNED_BYTE, buf);
            // Generate Mip Map
            glGenerateMipmap(GL_TEXTURE_2D);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Texture(InputStream stream) {
        try {
            PNGDecoder decoder = new PNGDecoder(stream);
            width = decoder.getWidth();
            height = decoder.getHeight();

            // Load texture contents into a byte buffer
            ByteBuffer buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();

            // Create a new OpenGL texture
            id = glGenTextures();
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, id);

            // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            // Upload the texture data
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0,
                    GL_RGBA, GL_UNSIGNED_BYTE, buf);
            // Generate Mip Map
            glGenerateMipmap(GL_TEXTURE_2D);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //public void bind() {glBindTexture(GL_TEXTURE_2D, id);}

    public int getId() {
        return id;
    }


    public void cleanup() {
        glDeleteTextures(id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
