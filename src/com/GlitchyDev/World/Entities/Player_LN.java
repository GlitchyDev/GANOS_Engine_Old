package com.GlitchyDev.World.Entities;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.Rendering.Assets.WorldElements.Camera;
import com.GlitchyDev.Rendering.Assets.WorldElements.SpriteItem;
import com.GlitchyDev.Rendering.Renderer;
import com.GlitchyDev.Utility.GameWindow;
import com.GlitchyDev.World.Direction;
import com.GlitchyDev.World.Location;

public class Player_LN extends MovingEntity implements InteractableEntity {
    private SpriteItem spriteItem;

    public Player_LN(Location location) {
        super(EntityType.PLAYER, location);
        spriteItem = new SpriteItem(AssetLoader.getTextureAsset("Allen"),4,10,true, true, true);
    }

    @Override
    public void render(Renderer renderer, GameWindow gameWindow, Camera camera) {
        renderer.render3DElement(gameWindow,"Glitchy3D",camera, spriteItem);
    }

    @Override
    public void tick() {
        spriteItem.setPosition((float) (getLocation().getX() + getMovementProgress()),getLocation().getY(),getLocation().getZ());

    }

    @Override
    public void completedMove() {

    }
}
