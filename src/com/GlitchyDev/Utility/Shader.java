package com.GlitchyDev.Utility;

import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int program;
    private int vs;
    private int fs;

    public Shader(File vertexShader, File fragmentShader)
    {
        System.out.println("Shader: Initializing");
        program = glCreateProgram();

        vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs,readFile(vertexShader));
        glCompileShader(vs);
        if(glGetShaderi(vs,GL_COMPILE_STATUS) != 1)
        {
            System.err.println("Vertex Shader \n" + readFile(vertexShader) + glGetShaderInfoLog(vs));
            System.exit(1);
        }


        fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs,readFile(fragmentShader));
        glCompileShader(fs);
        if(glGetShaderi(fs,GL_COMPILE_STATUS) != 1)
        {
            System.err.println("Fragment Shader \n" + readFile(fragmentShader) + glGetShaderInfoLog(fs));
            System.exit(1);
        }

        glAttachShader(program,vs);
        glAttachShader(program,fs);

        glBindAttribLocation(program,0,"vertices");
        glBindAttribLocation(program,1,"textures");


        glLinkProgram(program);
        if(glGetProgrami(program,GL_LINK_STATUS) != 1)
        {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if(glGetProgrami(program,GL_VALIDATE_STATUS) != 1)
        {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    public void bind()
    {
        glUseProgram(program);
    }

    private String readFile(File file)
    {
        StringBuilder string = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                string.append(line + "\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string.toString();
    }

    public void setUniform(String name, int value)
    {
        int location = glGetUniformLocation(program,name);
        if(location != -1)
        {
            glUniform1i(location,value);
        }
    }

    public void setUniform(String name, float value)
    {
        int location = glGetUniformLocation(program,name);
        if(location != -1)
        {
            glUniform1f(location,value);
        }
    }


}
