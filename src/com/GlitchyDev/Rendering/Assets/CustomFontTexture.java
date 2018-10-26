package com.GlitchyDev.Rendering.Assets;

import com.GlitchyDev.IO.AssetLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

public class CustomFontTexture extends HudTexture {


    public CustomFontTexture(String customFont) {
        super(customFont);
        buildTexture();
    }


    public Texture getTexture() {
        return texture;
    }

    public CharInfo getCharInfo(char c) {
        return charMap.get(c);
    }

    protected String getAllAvailableChars(String charsetName) {
        return String.join("@",AssetLoader.getConfigListAsset(charsetName + "Config"));
    }

    protected void buildTexture() {
        // Get the font metrics for each character for the selected font by using image


        String allChars = getAllAvailableChars(fontName);
        this.width = 0;
        this.height = 0;
        for (String s : allChars.split("@")) {
            // Get the size for each character and update global image size
            CharInfo charInfo = new CharInfo(width, 7);
            for(char c: s.toCharArray()) {
                charMap.put(c, charInfo);
            }
            width += charInfo.getWidth() + 1;
            height = 10;

        }
        texture = AssetLoader.getTextureAsset(fontName);
    }


}
