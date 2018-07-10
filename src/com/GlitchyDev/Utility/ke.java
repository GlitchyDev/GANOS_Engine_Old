package com.GlitchyDev.Utility;

import org.lwjgl.opengl.ARBMultitexture;
import org.newdawn.slick.*;

public class ke extends BasicGame {

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new ke(), 800, 600, false).start();
    }

    public ke() {
        super("Advanced Shader Test");
    }

    private Image originalImage;
    private Image filterImage;
    private Image errorImage;


    private ShaderProgram blurHoriz;
    private String log;
    private boolean supported = false;

    private Image postImageA;
    private Graphics postGraphicsA;





    @Override
    public void init(GameContainer container) throws SlickException {

        container.setTargetFrameRate(60);
        originalImage = new Image("GameAssets/Sprites/Debug/Test.png");
        errorImage = new Image("GameAssets/Sprites/Debug/Error.png");


        container.setClearEachFrame(false);

        supported = ShaderProgram.isSupported();


        //first we will try to create our offscreen image
        //this may fail in very very rare cases if the user has no FBO/PBuffer support
        postImageA = new Image(container.getWidth(), container.getHeight());
        postGraphicsA = postImageA.getGraphics();


        String h = "GameAssets/Shaders/Default/Default.frag";
        String vert = "GameAssets/Shaders/Default/Default.vert";

        blurHoriz = ShaderProgram.loadProgram(vert, h);

        //good idea to print/display the log anyways incase there are warnings..

        //note that strict mode is ENABLED so these uniforms must be active in shader

        //set up our uniforms for horizontal blur...
        blurHoriz.bind();
        blurHoriz.setUniform1i("tex0", 0); //texture 0
       //blurHoriz.setUniform1i("filter", filterImage.getTexture().getTextureID()); //texture ?
        System.out.println("Tex: " + errorImage.getTexture().getTextureID());
        blurHoriz.setUniform1i("glitch", errorImage.getTexture().getTextureID()); //texture ?
        //blurHoriz.setUniform4f("color",new Color(1.0f,0.0f,0.0f,1.0f));


        //blurHoriz.setUniform4f("col",new Color(1.0f,0.0f,0.0f,1.0f));
        //blurHoriz.setUniform1f("resolution", container.getWidth()); //width of img
        //blurHoriz.setUniform1f("radius", radius);


    }

    public void renderScene(GameContainer container, Graphics g) throws SlickException {


        g.drawImage(originalImage, 100, 300);
        g.drawImage(originalImage, 400, 200);

    }

    @Override
    public void render(GameContainer container, Graphics screenGraphics) throws SlickException {
        screenGraphics.clear();

        //for sake of example, only bother rendering if shader works

        //1. first we render our scene without blur into an off-screen buffer

        // this is just to be safe when using multiple contexts

        Graphics.setCurrent(postGraphicsA);

        postGraphicsA.clear();

        // this is where we'd draw sprites, entities, etc.


        errorImage.getTexture().bind();
        System.out.println();


        blurHoriz.bind();
        renderScene(container, postGraphicsA);
        blurHoriz.unbind();

        // flush it after drawing
        postGraphicsA.flush();

        //if we were using a single pass (i.e. only horizontal blur) then we could render
        //directly into the screen at this point. but since we are using two passes, we first need to
        //sample from the normal texture, then blur it horizontally onto to another texture, then sample
        //from the blurred texture as we blur it again (vertically) onto the screen.



        //4. enable our second shader, the vertical blur


        //5. sample from B, render to screen
        Graphics.setCurrent(screenGraphics);
        screenGraphics.drawImage(postImageA, 0, 0);
        //flushing the screen graphics doesn't do anything, so it's unnecessary
        //screenGraphics.flush();

        //stop using shaders
        ShaderProgram.unbindAll();

        //simply render the scene to the screen
        // renderScene(container, screenGraphics);

        screenGraphics.setColor(Color.white);

        //now we can render on top of post processing (on screen)

        screenGraphics.drawString("A",0,0);

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

    }
}
