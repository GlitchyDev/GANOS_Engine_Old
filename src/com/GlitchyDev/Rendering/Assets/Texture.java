package com.GlitchyDev.Rendering.Assets;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL14.GL_GENERATE_MIPMAP;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(InputStream stream) {
        // Load Texture file
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
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 2);
            glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);

            // Upload the texture data
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0,
                    GL_RGBA, GL_UNSIGNED_BYTE, buf);

            // Generate Mip Map
            //glGenerateMipmap(GL_TEXTURE_2D);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Texture(RenderBuffer renderBuffer)
    {
        this.width = renderBuffer.getWidth();
        this.height = renderBuffer.getHeight();
        this.id = renderBuffer.getTextureID();
    }

    public Texture(int id, int width, int height)
    {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public Texture(Texture texture)
    {
        this.id = texture.getId();
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }
    public Texture getSubTexture(int x, int y, int width, int height)
    {
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,0,0,50,50,0);
        return new Texture(id, width, height);

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
