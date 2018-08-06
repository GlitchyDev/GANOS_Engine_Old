package com.GlitchyDev.IO;

import com.GlitchyDev.Rendering.Assets.Mesh;
import com.GlitchyDev.Rendering.Assets.OBJLoader;
import com.GlitchyDev.Rendering.Assets.Texture;
import com.GlitchyDev.Rendering.Assets.Utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class AssetLoader {
    private static HashMap<String,InputStream> totalAssets = new HashMap<>();

    private static HashMap<String,Texture> textureAssets = new HashMap<>();
    private static HashMap<String,Mesh> meshAssets = new HashMap<>();
    private static HashMap<String,String> vertexAssets = new HashMap<>();
    private static HashMap<String,String> fragmentAssets = new HashMap<>();
    private static HashMap<String,HashMap<String,String>> configOptionAssets = new HashMap<>();
    private static HashMap<String, ArrayList<String>> configListAssets = new HashMap<>();


    private static boolean isLoadedFromJar;



    public static void loadAssets() throws Exception {
        System.out.println("AssetLoader: Loading Assets");
        long startTime = System.currentTimeMillis();
        detectIfJar();
        if(!isLoadedFromJar) {
            updateAssetRegistry();
        }

        System.out.println("AssetLoader: Initialing Assets");
        InputStream is = AssetLoader.class.getResourceAsStream("/Configs/General/AssetRegistry.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while(br.ready())
        {
            String path = br.readLine();
            loadAsset(path);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("AssetLoader: Assets Created in " + (endTime - startTime)/1000.0 + "s");


    }

    private static void loadAsset(String fileName) throws Exception {

        Path path = Paths.get(fileName);
        String filePath = fileName.replace("\\","/").replace("GameAssets","");
        String fullName = path.getFileName().toString();
        String fileType = fullName.split("\\.")[1];
        String name = fullName.replace("." + fileType,"");

        InputStream inputStream = AssetLoader.class.getResourceAsStream(filePath);

        switch(fileType)
        {
            case "obj":
                System.out.println("AssetLoader: Loading Mesh Asset: " + name + " " + fileType);
                meshAssets.put(name, OBJLoader.loadMesh(inputStream));
                break;
            case "png":

                System.out.println("AssetLoader: Loading Texture Asset: " + name + " " + fileType);
                textureAssets.put(name,new Texture(inputStream));
                break;
            case "wav":
                // Load Sounds
                break;
            case "vs":
                System.out.println("AssetLoader: Loading Vertex Shader Asset: " + name + " " + fileType);
                vertexAssets.put(name, Utils.loadResource(inputStream));
                break;
            case "fs":
                System.out.println("AssetLoader: Loading Fragment Shader Asset: " + name + " " + fileType);
                fragmentAssets.put(name,Utils.loadResource(inputStream));
                break;
            case "configOptions":
                System.out.println("AssetLoader: Loading Config Option Asset: " + name + " " + fileType);
                BufferedReader configOptionReader = new BufferedReader(new InputStreamReader(inputStream));
                HashMap<String,String> configMap = new HashMap<>();
                while(configOptionReader.ready())
                {
                    String line = configOptionReader.readLine();
                    String[] configList = line.trim().split(":");
                    configMap.put(configList[0],configList[1]);
                }
                configOptionAssets.put(name,configMap);
                break;
            case "configList":
                System.out.println("AssetLoader: Loading Config List Asset: " + name + " " + fileType);
                BufferedReader configListReader = new BufferedReader(new InputStreamReader(inputStream));
                ArrayList<String> configList = new ArrayList<>();
                while(configListReader.ready())
                {
                    configList.add(configListReader.readLine());
                }
                configListAssets.put(name,configList);
                break;
        }
        inputStream = AssetLoader.class.getResourceAsStream(filePath);
        totalAssets.put(fullName,inputStream);
    }


    private static void updateAssetRegistry() throws IOException {
        System.out.println("AssetLoader: Updating Asset Registry");

        File sourceFolder = new File("GameAssets");

        File assetRegistry = new File("GameAssets/Configs/General/AssetRegistry.txt");
        PrintWriter writer = new PrintWriter(assetRegistry);

        processFileToAssetRegistry(writer,sourceFolder);
        writer.close();
    }

    private static void processFileToAssetRegistry(PrintWriter writer, File file) {
        if(file.isDirectory())
        {
            for(File subFiles: file.listFiles())
            {
                processFileToAssetRegistry(writer,subFiles);
            }
        }
        else
        {
            if(!file.getName().equals("AssetRegistry.txt")) {
                System.out.println("AssetLoader: Registered Asset " + file.getName());
                writer.println(file.getPath());
            }
        }
    }

    private static void detectIfJar()
    {
        isLoadedFromJar = AssetLoader.class.getResource("AssetLoader.class").toString().contains("jar");
        if(isLoadedFromJar) {
            System.out.println("AssetLoader: Loading from Jar");
        }
        else
        {
            System.out.println("AssetLoader: Loading Assets from IDE, update AssetRegistry");
        }
    }

    public static InputStream getGeneralAsset(String name)
    {
        return totalAssets.get(name);
    }

    public static Texture getTextureAsset(String name)
    {
        return textureAssets.get(name);
    }

    public static Mesh getMeshAsset(String name)
    {
        return meshAssets.get(name);
    }

    public static String getVertexAsset(String name)
    {
        return vertexAssets.get(name);
    }

    public static String getFragmentAsset(String name)
    {
        return fragmentAssets.get(name);
    }

    public static HashMap<String,String> getConfigOptionAsset(String name)
    {
        return configOptionAssets.get(name);
    }

    public static ArrayList<String> getConfigListAsset(String name)
    {
        return configListAssets.get(name);
    }

}
