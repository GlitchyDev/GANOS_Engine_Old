package com.GlitchyDev.Utility;

import com.GlitchyDev.GANOS_Client;
import com.GlitchyDev.IO.AssetLoader;
import org.newdawn.slick.Color;

public class TextureSampler {


    public static Color getColor(String image, int x, int y)
    {
        return AssetLoader.getSprite(image).getColor(x * GANOS_Client.SCALE,y * GANOS_Client.SCALE);
    }

    public static int getWidth(String image)
    {
        return AssetLoader.getSprite(image).getWidth()/GANOS_Client.SCALE;
    }

    public static int getHeight(String image)
    {
        return AssetLoader.getSprite(image).getWidth()/GANOS_Client.SCALE;
    }
}
