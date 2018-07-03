package com.GlitchyDev.IO;

import com.GlitchyDev.Utility.GameType;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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

}
