package com.GlitchyDev.Rendering.Assets;

import com.GlitchyDev.Rendering.Assets.WorldElements.GameItem;
import com.GlitchyDev.Rendering.Assets.WorldElements.Transformation;
import com.GlitchyDev.World.Blocks.PartialCubicBlock;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL31C.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33C.glVertexAttribDivisor;

public class InstancedMesh extends Mesh {
    private InstancedGridTexture instancedGridTexture;
    private final int width = 100;
    private final int instanceChunkSize =  width*width;

    private final int matrixVboId;
    private FloatBuffer matrixBuffer;
    private FloatBuffer matrixVboData;

    private final int textureVboId;
    private FloatBuffer textureBuffer;
    private FloatBuffer textureVboData;

    public InstancedMesh(Mesh mesh, InstancedGridTexture instancedGridTexture) {
        super(mesh);

        this.instancedGridTexture = instancedGridTexture;



        matrixBuffer = BufferUtils.createFloatBuffer(16 * instanceChunkSize);
        matrixVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, matrixVboId);
        glBufferData(GL_ARRAY_BUFFER, 16 * 4 * instanceChunkSize, GL_STREAM_DRAW);
        vboIdList.add(matrixVboId);
        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        addInstancedAttribute(getVaoId(), matrixVboId, 2, 4, 16, 0);
        addInstancedAttribute(getVaoId(), matrixVboId, 3, 4, 16, 4);
        addInstancedAttribute(getVaoId(), matrixVboId, 4, 4, 16, 8);
        addInstancedAttribute(getVaoId(), matrixVboId, 5, 4, 16, 12);


        textureBuffer = BufferUtils.createFloatBuffer((2 * 6) * instanceChunkSize);
        textureVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureVboId);
        glBufferData(GL_ARRAY_BUFFER, (2 * 6) * 4 * instanceChunkSize, GL_STREAM_DRAW);
        vboIdList.add(textureVboId);
        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        addInstancedAttribute(getVaoId(), textureVboId, 6, 2, 2, 0);

        matrixVboData = BufferUtils.createFloatBuffer(instanceChunkSize * 16);
        textureVboData = BufferUtils.createFloatBuffer(instanceChunkSize * 2);

    }

    public void updateVBO(int vbo, FloatBuffer data, FloatBuffer buffer)
    {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //glBufferData(GL_ARRAY_BUFFER, matrixBuffer.capacity() * 4, GL_STREAM_DRAW);
        glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * 4, GL_DYNAMIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }


    @Override
    public void preRender()
    {
        // Activate firs texture bank
        // Bind the texture

        // Draw the mesh
        GL30.glBindVertexArray(getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        GL20.glEnableVertexAttribArray(4);
        GL20.glEnableVertexAttribArray(5);
        GL20.glEnableVertexAttribArray(6);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, instancedGridTexture.getId());

    }

    @Override
    public void postRender()
    {
        // Restore state
        glBindTexture(GL_TEXTURE_2D, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
        glDisableVertexAttribArray(5);
        glDisableVertexAttribArray(6);
        GL30.glBindVertexArray(0);
    }

    public void addInstancedAttribute(int vao, int vbo, int attribute, int dataSize, int instancedDataLength, int offset)
    {
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBindVertexArray(vao);
        glVertexAttribPointer(attribute,dataSize, GL_FLOAT, false, instancedDataLength * 4, offset * 4);
        glVertexAttribDivisor(attribute,1);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        glBindVertexArray(0);
    }


    public void renderMeshInstanceList(List<GameItem> gameItems, Transformation transformation, Matrix4f viewMatrix)
    {
        preRender();
        int length = gameItems.size();
        for (int i = 0; i < length; i += instanceChunkSize) {
            //System.out.println(i);
            int end = Math.min(length, i + instanceChunkSize);
            renderMeshInstanced(gameItems.subList(i, end),  transformation, viewMatrix, end-i);
        }

    }

    public void renderMeshInstanced(List<GameItem> gameItems, Transformation transformation, Matrix4f viewMatrix, int size)
    {
        matrixVboData = BufferUtils.createFloatBuffer(size * 16);
        int offset = 0;
        for(int i = 0; i < size; i++)
        {
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItems.get(i), viewMatrix);
            modelViewMatrix.get(offset * 16, matrixVboData);
            offset++;
        }
        updateVBO(matrixVboId, matrixVboData, matrixBuffer);
        glDrawElementsInstanced(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0, size);
    }


    private ArrayList<Matrix4f> modelViewMatrixes;
    private ArrayList<Vector2f> textureCords;
    boolean[] faces;
    Vector3f rotation;
    public void renderPartialCubicBlocksInstanceList(List<PartialCubicBlock> blocks, Transformation transformation, Matrix4f viewMatrix)
    {
        preRender();


        // Redo begining to get all view matrixes

        // Collect all the rotations from each block

        modelViewMatrixes = new ArrayList<>();
        textureCords = new ArrayList<>();

        for(PartialCubicBlock block: blocks)
        {
            int validStates = 0;
            faces = block.getFaceStates();
            for(int i = 0; i < 6; i++)
            {
                if(faces[i])
                {
                    switch(i)
                    {
                        case 0:
                            rotation = new Vector3f(block.getRotation());
                            rotation.add(0,0,0);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix));
                            break;
                        case 1:
                            rotation = new Vector3f(block.getRotation());
                            rotation.add(180,0,0);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix));
                            break;



                        case 2:

                            rotation = new Vector3f(block.getRotation());
                            rotation.add(0, 0,90);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix));
                            break;
                        case 3:
                            rotation = new Vector3f(block.getRotation());
                            rotation.add(90,-90,0);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix));
                            break;
                        case 4:
                            rotation = new Vector3f(block.getRotation());
                            rotation.add(180, 0,270);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix));
                            break;
                        case 5:
                            rotation = new Vector3f(block.getRotation());
                            rotation.add(90, -270,180);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix));
                            break;
                    }

                    int num = block.getAssignedTextures()[validStates];
                    int x = num % instancedGridTexture.getTextureGridWidth();
                    int y = num / instancedGridTexture.getTextureGridWidth();

                    textureCords.add(new Vector2f(x,y));
                    validStates++;
                }
            }

        }

        int length = modelViewMatrixes.size();
        for (int i = 0; i < length; i += instanceChunkSize) {
            //System.out.println(i);
            int end = Math.min(length, i + instanceChunkSize);
            renderPartialCubicBlocksInstanced(modelViewMatrixes.subList(i, end), textureCords.subList(i, end), viewMatrix, end-i);
        }

    }

    public void renderPartialCubicBlocksInstanced(List<Matrix4f> blocks, List<Vector2f> textureCords, Matrix4f viewMatrix, int size)
    {
        matrixVboData.clear();
        textureVboData.clear();

        int offset = 0;
        for(int i = 0; i < size; i++)
        {
            blocks.get(i).get(offset * 16, matrixVboData);
            textureCords.get(i).get(offset * 2, textureVboData);
            offset++;
        }
        updateVBO(matrixVboId, matrixVboData, matrixBuffer);
        updateVBO(textureVboId, textureVboData, textureBuffer);
        glDrawElementsInstanced(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0, size);
    }


    public InstancedGridTexture getInstancedGridTexture() {
        return instancedGridTexture;
    }
}
