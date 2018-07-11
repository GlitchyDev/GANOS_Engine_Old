package com.GlitchyDev.IO;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.io.File;
import java.util.HashMap;

public class AssetLoader {
    private static HashMap<String,File> fileLoader;
    private static HashMap<String,Image> spriteAssets;
    private static HashMap<String,Sound> soundAssets;
    private static String[] icons;


    public static void loadAssets()  {

        fileLoader = new HashMap<>();
        spriteAssets = new HashMap<>();
        soundAssets = new HashMap<>();
        File assetFolder = new File("GameAssets");
        for(File mainFolder: assetFolder.listFiles())
        {
            System.out.println("Loading " + mainFolder.getName() + " Assets");
            for(File subFolder: mainFolder.listFiles())
            {
                System.out.println("Loading Folder " + subFolder.getName());
                for(File file: subFolder.listFiles())
                {
                    System.out.println("Loading Asset " + file.getName());

                    String name = file.getName();
                    String friendlyName = file.getName().split("\\.")[0];




                    switch(mainFolder.getName())
                    {
                        case "Sounds":
                            break;
                        case "Sprites":
                        default:
                            fileLoader.put(name,file);
                    }
                }
            }
        }
    }

    public static void setDefaultIconss(GameContainer gameContainer, String tinyIcon, String largeIcon) throws SlickException {
        icons = new String[2];
        icons[0] = tinyIcon;
        icons[1] = largeIcon;
        gameContainer.setIcons(icons);

    }


    public static Image getSprite(String name)
    {
        return spriteAssets.get(name);
    }
    public static Sound getSound(String name)
    {
        return soundAssets.get(name);
    }
    public static File getFile(String name){return fileLoader.get(name);}



}

