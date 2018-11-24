package com.GlitchyDev.Rendering.Assets;

import com.GlitchyDev.Rendering.Assets.WorldElements.GameItem;
import com.GlitchyDev.Rendering.Assets.WorldElements.Transformation;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL31C.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33C.glVertexAttribDivisor;

public class InstancedMesh extends Mesh {
    private final int instanceChunkSize;

    private static final int FLOAT_SIZE_BYTES = 4;
    private static final int VECTOR4F_SIZE_BYTES = 4 * FLOAT_SIZE_BYTES;
    private static final int MATRIX_SIZE_FLOATS = 4 * 4;
    private static final int MATRIX_SIZE_BYTES = MATRIX_SIZE_FLOATS * FLOAT_SIZE_BYTES;
    private static final int INSTANCE_SIZE_BYTES = MATRIX_SIZE_BYTES;
    private static final int INSTANCE_SIZE_FLOATS = MATRIX_SIZE_FLOATS;

    private final int instanceDataVBO;
    private FloatBuffer instanceDataBuffer;

    public InstancedMesh(Mesh mesh) {
        super(mesh);
        instanceChunkSize = 100;

        glBindVertexArray(vaoId);
        // Model View Matrix
        instanceDataVBO = glGenBuffers();
        vboIdList.add(instanceDataVBO);
        instanceDataBuffer = MemoryUtil.memAllocFloat(instanceChunkSize * INSTANCE_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        int start = 2;
        int strideStart = 0;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;

        }
    }

    public InstancedMesh(Mesh mesh, int instanceChunkSize) {
        super(mesh);
        this.instanceChunkSize = instanceChunkSize;

        glBindVertexArray(vaoId);
        // Model View Matrix
        instanceDataVBO = glGenBuffers();
        vboIdList.add(instanceDataVBO);
        instanceDataBuffer = MemoryUtil.memAllocFloat(instanceChunkSize * INSTANCE_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        int start = 2;
        int strideStart = 0;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;

        }
    }

    public InstancedMesh(float[] positions, float[] textCoords, int[] indices) {
        super(positions, textCoords, indices);
        instanceChunkSize = 100;

        glBindVertexArray(vaoId);
        // Model View Matrix
        instanceDataVBO = glGenBuffers();
        vboIdList.add(instanceDataVBO);
        instanceDataBuffer = MemoryUtil.memAllocFloat(instanceChunkSize * INSTANCE_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        int start = 2;
        int strideStart = 0;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;

        }
    }

    public InstancedMesh(int vaoId, List<Integer> vboIdList, int vertexCount, Texture texture) {
        super(vaoId, vboIdList, vertexCount, texture);
        instanceChunkSize = 100;

        glBindVertexArray(vaoId);
        // Model View Matrix
        instanceDataVBO = glGenBuffers();
        vboIdList.add(instanceDataVBO);
        instanceDataBuffer = MemoryUtil.memAllocFloat(instanceChunkSize * INSTANCE_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        int start = 2;
        int strideStart = 0;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;

        }
    }

    public InstancedMesh(float[] positions, float[] textCoords, int[] indices, int instanceChunkSize) {
        super(positions, textCoords, indices);
        this.instanceChunkSize = instanceChunkSize;

        glBindVertexArray(vaoId);
        // Model View Matrix
        instanceDataVBO = glGenBuffers();
        vboIdList.add(instanceDataVBO);
        instanceDataBuffer = MemoryUtil.memAllocFloat(instanceChunkSize * INSTANCE_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        int start = 2;
        int strideStart = 0;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;

        }
    }

    public InstancedMesh(int vaoId, List<Integer> vboIdList, int vertexCount, Texture texture, int instanceChunkSize) {
        super(vaoId, vboIdList, vertexCount, texture);
        this.instanceChunkSize = instanceChunkSize;

        glBindVertexArray(vaoId);
        // Model View Matrix
        instanceDataVBO = glGenBuffers();
        vboIdList.add(instanceDataVBO);
        instanceDataBuffer = MemoryUtil.memAllocFloat(instanceChunkSize * INSTANCE_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        int start = 2;
        int strideStart = 0;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;

        }

    }


    @Override
    public void preRender() {
        super.preRender();

        int start = 2;
        int numElements = 4;
        for (int i = 0; i < numElements; i++) {
            glEnableVertexAttribArray(start + i);
        }
    }

    @Override
    public void postRender() {
        int start = 2;
        int numElements = 4;
        for (int i = 0; i < numElements; i++) {
            glDisableVertexAttribArray(start + i);
        }

        super.postRender();
    }


    public void renderListInstanced(List<GameItem> gameItems, Transformation transformation, Matrix4f viewMatrix) {
        preRender();

        int length = gameItems.size();
        for (int i = 0; i < length; i += instanceChunkSize) {
            int end = Math.min(length, i + instanceChunkSize);
            List<GameItem> subList = gameItems.subList(i, end);
            renderChunkInstanced(subList, transformation, viewMatrix);
        }

        postRender();
    }

    private void renderChunkInstanced(List<GameItem> gameItems, Transformation transformation, Matrix4f viewMatrix) {
        this.instanceDataBuffer.clear();

        int i = 0;
        for (GameItem gameItem : gameItems) {

            if (viewMatrix != null) {
                Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
                modelViewMatrix.get(INSTANCE_SIZE_FLOATS * i, instanceDataBuffer);
            }
            i++;
        }

        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        glBufferData(GL_ARRAY_BUFFER, instanceDataBuffer, GL_STATIC_DRAW);

        glDrawElementsInstanced(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0, gameItems.size());

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
