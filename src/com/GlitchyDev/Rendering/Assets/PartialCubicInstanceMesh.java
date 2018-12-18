package com.GlitchyDev.Rendering.Assets;

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

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL31C.glDrawElementsInstanced;

public class PartialCubicInstanceMesh extends InstancedMesh {

    protected InstancedGridTexture instancedGridTexture;
    protected final int textureVboId;
    protected FloatBuffer textureBuffer;
    protected FloatBuffer textureVboData;

    public PartialCubicInstanceMesh(Mesh mesh, int instanceChunkSize, InstancedGridTexture instancedGridTexture) {
        super(mesh, instanceChunkSize);
        this.instancedGridTexture = instancedGridTexture;

        textureBuffer = BufferUtils.createFloatBuffer((2 * 6) * instanceChunkSize);
        textureVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureVboId);
        glBufferData(GL_ARRAY_BUFFER, (2 * 6) * 4 * instanceChunkSize, GL_STREAM_DRAW);
        vboIdList.add(textureVboId);
        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        addInstancedAttribute(getVaoId(), textureVboId, 6, 2, 2, 0);
        textureVboData = BufferUtils.createFloatBuffer(instanceChunkSize * 2);
    }

    @Override
    public void preRender() {
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
    public void postRender() {
        super.preRender();
        glDisableVertexAttribArray(6);
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
            faces = block.getFaceStates();
            for(int i = 0; i < 6; i++)
            {
                if(faces[i])
                {
                    rotation = new Vector3f(block.getRotation());
                    switch(i)
                    {
                        case 0:
                            // 0
                            rotation.add(0,90,0);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix,2));
                            break;
                        case 1:
                            // 1
                            rotation.add(180,90,0);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix,2));
                            break;
                        case 2:
                            // 2*
                            rotation.add(90,-90,0);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix,2));
                            break;
                        case 3:
                            // 3*
                            rotation.add(0, 0,90);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix,2));
                            break;
                        case 4:
                            // 4*
                            rotation.add(90, -270,180);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix,2));
                            break;
                        case 5:
                            // 5*
                            rotation.add(180, 0,270);
                            modelViewMatrixes.add(transformation.getModelViewMatrix(block.getPosition(), rotation, block.getScale(), viewMatrix,2));
                            break;
                    }

                    int num = block.getAssignedTextures()[i];
                    int x = num % instancedGridTexture.getTextureGridWidth();
                    int y = num / instancedGridTexture.getTextureGridWidth();

                    textureCords.add(new Vector2f(x,y));
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
