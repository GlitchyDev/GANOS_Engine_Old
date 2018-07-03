package com.GlitchyDev.GameStates;

import com.GlitchyDev.Networking.ClientNetworkConnection;
import com.GlitchyDev.Utility.BasicMonitoredGameState;
import com.GlitchyDev.Utility.GButtons;
import com.GlitchyDev.Utility.GameController;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;



public class NetworkClientState extends BasicMonitoredGameState {
    private int x = 0;
    private int y = 0;
    private ClientNetworkConnection clientNetworkConnection;
    private TextField textField;
    private double highestDelay = 0.0;

    public NetworkClientState(ClientNetworkConnection clientNetworkConnection)
    {
        this.clientNetworkConnection = clientNetworkConnection;
    }

    public UnicodeFont getNewFont(String fontName , int fontSize){
        UnicodeFont returnFont = new UnicodeFont(new java.awt.Font(fontName , java.awt.Font.PLAIN , fontSize));
        returnFont.addAsciiGlyphs();
        returnFont.getEffects().add(new ColorEffect(java.awt.Color.black));
        return (returnFont);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {

        Font font = getNewFont("Arial",20);
        ((UnicodeFont) font).loadGlyphs();
        textField = new TextField(gameContainer, font, 0, 0, 300, 50);


        //TextField field = new TextField(font,0,0,50,50);
    }

    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        //int width = GANOS_Client.WIDTH / 50;
        //int height = GANOS_Client.HEIGHT / 50;


        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                double random = Math.random();
                graphics.setColor(new Color((int) (random * 255), (int) (random * 255), (int) (random * 255)));
                graphics.fillRect(x * 50, y * 50, 50, 50);
            }
        }

        if (clientNetworkConnection.isConnected()) {
            graphics.setColor(Color.red);
        } else {
            graphics.setColor(Color.red);
        }
        graphics.fillRect(this.x * 50, this.y * 50, 50, 50);


        graphics.drawString(String.valueOf(this.getRenderingUtilization()), 450, 0);


        graphics.setColor(Color.white);

        textField.setTextColor(Color.blue);
        textField.setBackgroundColor(Color.lightGray);
        textField.setBorderColor(Color.red);

        textField.render(gameContainer, graphics);


        graphics.drawString(String.valueOf(this.getRenderingUtilization()), 450, 0);
    }






    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(GameController.isButtonPressed(GButtons.UP) && !clientNetworkConnection.isConnected() && clientNetworkConnection.getGameSocket() == null)
        {
            clientNetworkConnection.createConnection("192.168.1.3",textField.getText());
        }
        //Display.setTitle(textField.getText());

    }

    @Override
    public int getID() {
        return 0;
    }



}
