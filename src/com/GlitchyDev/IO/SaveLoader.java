package com.GlitchyDev.IO;

import java.util.HashMap;

public class SaveLoader {
    private static boolean doesSaveExists = true;
    private static HashMap<String,String> rawSaveData;
    // Add Save Stuff here


    public static void loadSave()
    {
        rawSaveData = new HashMap<>();
        rawSaveData.put("SUUID","James");
        doesSaveExists = true;
        // Do loading from bianary file
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
            return rawSaveData.get(value);
        }
        else
        {
            return null;
        }
    }

}
