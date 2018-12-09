package com.GlitchyDev.Rendering.Assets;

import java.io.InputStream;

public class InstancedGridTexture extends Texture {
    private final int texWidth;
    private final int texHeight;

    public InstancedGridTexture(Texture texture, int texWidth, int texHeight) {
        super(texture);
        this.texWidth = texWidth;
        this.texHeight = texHeight;
    }


    public int getTextureGridWidth() {
        return texWidth;
    }

    public int getTextureGridHeight() {
        return texHeight;
    }


    public float getTexturePortionWidth() {
        return 1.0f/super.getWidth();
    }


    public float getTexturePortionHeight() {
        return 1.0f/super.getHeight();
    }
}
