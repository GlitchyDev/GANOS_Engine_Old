package com.GlitchyDev.IO;

import com.GlitchyDev.graph.Mesh;
import com.GlitchyDev.graph.OBJLoader;
import com.GlitchyDev.graph.Texture;
import com.GlitchyDev.graph.Utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class AssetLoader {
    private static HashMap<String,InputStream> totalAssets = new HashMap<>();

    private static HashMap<String,Texture> textureAssets = new HashMap<>();
    private static HashMap<String,Mesh> meshAssets = new HashMap<>();
    private static HashMap<String,String> vertexAssets = new HashMap<>();
    private static HashMap<String,String> fragmentAssets = new HashMap<>();
    private static HashMap<String,HashMap<String,String>> configAssets = new HashMap<>();

    private static boolean isLoadedFromJar;



    public static void loadAssets() throws Exception {
        long startTime = System.currentTimeMillis();
        detectIfJar();
        if(!isLoadedFromJar) {
            updateAssetRegistry();
        }

        InputStream is = AssetLoader.class.getResourceAsStream("/Configs/General/AssetRegistry.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        while(br.ready())
        {
            String path = br.readLine();
            loadAsset(path);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time : " + (endTime - startTime)/1000.0);


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
                System.out.println("Loading Mesh Asset: " + name + " " + fileType);
                meshAssets.put(name, OBJLoader.loadMesh(inputStream));
                break;
            case "png":

                System.out.println("Loading Texture Asset: " + name + " " + fileType);
                textureAssets.put(name,new Texture(inputStream));
                break;
            case "wav":
                // Load Sounds
                break;
            case "vs":
                System.out.println("Loading Vertex Shader Asset: " + name + " " + fileType);
                vertexAssets.put(name, Utils.loadResource(inputStream));
                break;
            case "fs":
                System.out.println("Loading Fragment Shader Asset: " + name + " " + fileType);
                fragmentAssets.put(name,Utils.loadResource(inputStream));
                break;
            case "config":
                System.out.println("Loading Config Asset: " + name + " " + fileType);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                HashMap<String,String> configMap = new HashMap<>();
                while(reader.ready())
                {
                    String line = reader.readLine();
                    String[] configList = line.trim().split(":");
                    configMap.put(configList[0],configList[1]);
                }
                configAssets.put(name,configMap);
                break;
        }
        inputStream = AssetLoader.class.getResourceAsStream(filePath);
        totalAssets.put(fullName,inputStream);
    }


    private static void updateAssetRegistry() throws IOException {
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
                writer.println(file.getPath());
            }
        }
    }

    private static void detectIfJar()
    {
        isLoadedFromJar = AssetLoader.class.getResource("AssetLoader.class").toString().contains("jar");
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

    public static HashMap<String,String> getConfigAsset(String name)
    {
        return configAssets.get(name);
    }
}
