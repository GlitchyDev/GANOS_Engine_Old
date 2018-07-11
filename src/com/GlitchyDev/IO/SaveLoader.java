package com.GlitchyDev.IO;

import com.GlitchyDev.Utility.GameType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SaveLoader {
    private static boolean doesSaveExists = false;
    private static HashMap<String,String> saveData;
    private static final String DEFAULTSAVEPATH = "GameAssets/Saves/";
    // Add Save Stuff here


    public static void loadSave(GameType gameType)
    {
        saveData = new HashMap<>();
        switch(gameType)
        {
            case CLIENT:
                File saveFile = new File(DEFAULTSAVEPATH + "ClientSave/SaveFile.Dev");
                break;
            case SERVER:

                break;
        }
        saveData.put("SUUID","Jam");
        saveData = new HashMap<>();
        doesSaveExists = true;
    }

    public static void createSave()
    {
        // Add Save functionality
    }


    public static boolean doesSaveExists() {
        return doesSaveExists;
    }

    public static String getSaveValue(String value)
    {
        if(doesSaveExists()) {
            return saveData.get(value);
        }
        else
        {
            return null;
        }
    }

    public static ArrayList<String> getConfigList(File file)
    {
        ArrayList<String> configList = new ArrayList<>();
        System.out.println("ServerNetwork: Loading File " + file.getName());

        try {
            Scanner scanner = new  Scanner(file);
            while(scanner.hasNext())
            {
                String config = scanner.nextLine();
                if(!config.contains("#")) {
                    configList.add(config);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return configList;
    }

    public static HashMap<String,String> getConfigSettings(File file)
    {
        HashMap<String,String> configSettings = new HashMap<>();
        System.out.println("ServerNetwork: Loading File " + file.getName());

        try {
            Scanner scanner = new  Scanner(file);
            while(scanner.hasNext())
            {
                String configLine = scanner.nextLine();
                if(!configLine.contains("#")) {
                    configSettings.put(configLine.split(": ")[0],configLine.split(": ")[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return configSettings;
    }

}
