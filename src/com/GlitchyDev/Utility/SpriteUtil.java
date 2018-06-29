package com.GlitchyDev.Utility;

import com.GlitchyDev.GANOS_Client;
import com.GlitchyDev.IO.AssetLoader;
import org.newdawn.slick.Color;

public class SpriteUtil {

    public static void drawSprite(String name, int x, int y, double alpha)
    {
        checkNull(name);
        AssetLoader.getSprite(name).draw(x * GANOS_Client.SCALE,y * GANOS_Client.SCALE,GANOS_Client.SCALE,new Color(1.0f,1.0f,1.0f,(float)(alpha)));
    }

    public static void drawFlippedSprite(String name, int x, int y, double alpha)
    {
        checkNull(name);
        AssetLoader.getSprite(name).getFlippedCopy(true,false).draw(x * GANOS_Client.SCALE,y * GANOS_Client.SCALE,GANOS_Client.SCALE,new Color(1.0f,1.0f,1.0f,(float)(alpha)));
    }

    public static void drawSprite(String name, int x, int y, double alpha, boolean flipped)
    {
        checkNull(name);
        if(flipped)
        {
            drawFlippedSprite(name, x, y, alpha);
        }
        else
        {
            drawSprite(name, x, y, alpha);
        }
    }

    public static void drawBottemCenteredSprite(String name, int x, int y, double alpha, boolean flipped)
    {
        checkNull(name);
        int height = AssetLoader.getSprite(name).getHeight();
        int width = AssetLoader.getSprite(name).getWidth();
        drawSprite(name,x-width/2,y-height,alpha,flipped);
    }

    public static void checkNull(String name)
    {
        if(AssetLoader.getSprite(name) == null)
        {
            System.out.println("AssetLoader: Missing Asset " + name);
        }
    }
}
