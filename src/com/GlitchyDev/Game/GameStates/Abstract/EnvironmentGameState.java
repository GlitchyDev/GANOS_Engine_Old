package com.GlitchyDev.Game.GameStates.Abstract;

import com.GlitchyDev.Game.GameStates.GameStateType;
import com.GlitchyDev.Rendering.WorldElements.Camera;
import com.GlitchyDev.Rendering.WorldElements.GameItem;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.Utility.GlobalGameData;
import org.joml.*;

import java.util.List;

public abstract class EnvironmentGameState extends InputGameStateBase {
    private Vector3f dir = new Vector3f();
    private Vector3f max = new Vector3f();
    private Vector3f min = new Vector3f();
    private Vector2f nearFar = new Vector2f();
    //
    private Matrix4f invProjectionMatrix = new Matrix4f();
    private Matrix4f invViewMatrix = new Matrix4f();
    private Vector3f mouseDir = new Vector3f();
    private Vector4f tmpVec = new Vector4f();

    public EnvironmentGameState(GlobalGameData globalGameDataBase, GameStateType gameStateType) {
        super(globalGameDataBase, gameStateType);
    }



    /*
    Allows for the Selection of 3D Game Items through the view of a Camera
     */
    protected GameItem selectGameItem3D(List<GameItem> gameItems, Camera camera) {
        dir = camera.getViewMatrix().positiveZ(dir).negate();
        return selectGameItem3D(gameItems, camera.getPosition(), dir);
    }

    protected GameItem selectGameItem3D(List<GameItem> gameItems, Vector3f center, Vector3f dir) {
        GameItem selectedGameItem = null;
        float closestDistance = Float.POSITIVE_INFINITY;

        for (GameItem gameItem : gameItems) {
            min.set(gameItem.getPosition());
            max.set(gameItem.getPosition());
            min.add(-gameItem.getScale(), -gameItem.getScale(), -gameItem.getScale());
            max.add(gameItem.getScale(), gameItem.getScale(), gameItem.getScale());
            if (Intersectionf.intersectRayAab(center, dir, min, max, nearFar) && nearFar.x < closestDistance) {
                closestDistance = nearFar.x;
                selectedGameItem = gameItem;
            }
        }

        return selectedGameItem;
    }


    protected GameItem selectGameItem2D(List<GameItem> gameItems, GameWindow window, Vector2d mousePos, Camera camera) {
        // Transform mouse coordinates into normalized spaze [-1, 1]
        int wdwWitdh = window.getWidth();
        int wdwHeight = window.getHeight();

        float x = (float)(2 * mousePos.x) / (float)wdwWitdh - 1.0f;
        float y = 1.0f - (float)(2 * mousePos.y) / (float)wdwHeight;
        float z = -1.0f;

        invProjectionMatrix.set(window.getProjectionMatrix());
        invProjectionMatrix.invert();

        tmpVec.set(x, y, z, 1.0f);
        tmpVec.mul(invProjectionMatrix);
        tmpVec.z = -1.0f;
        tmpVec.w = 0.0f;

        Matrix4f viewMatrix = camera.getViewMatrix();
        invViewMatrix.set(viewMatrix);
        invViewMatrix.invert();
        tmpVec.mul(invViewMatrix);

        mouseDir.set(tmpVec.x, tmpVec.y, tmpVec.z);

        return selectGameItem3D(gameItems, camera.getPosition(), mouseDir);
    }
}
