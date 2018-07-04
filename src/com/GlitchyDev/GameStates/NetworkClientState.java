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

    private ClientNetworkConnection clientNetworkConnection;
    private TextField textField;

    public NetworkClientState(ClientNetworkConnection clientNetworkConnection) {
        this.clientNetworkConnection = clientNetworkConnection;
    }


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        Font font = getNewFont("Arial",20);
        ((UnicodeFont) font).loadGlyphs();
        textField = new TextField(gameContainer, font, 0, 0, 300, 50);
    }

    public UnicodeFont getNewFont(String fontName , int fontSize){
        UnicodeFont returnFont = new UnicodeFont(new java.awt.Font(fontName , java.awt.Font.PLAIN , fontSize));
        returnFont.addAsciiGlyphs();
        returnFont.getEffects().add(new ColorEffect(java.awt.Color.black));
        return (returnFont);
    }

    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                double random = Math.random();
                if (clientNetworkConnection.isAuthenticated()) {
                    graphics.setColor(new Color((int) (random * 255), 0,0));
                } else {
                    graphics.setColor(new Color((int) (random * 255), (int) (random * 255), (int) (random * 255)));
                }
                graphics.fillRect(x * 50, y * 50, 50, 50);
            }
        }

        graphics.setColor(Color.white);
        textField.setTextColor(Color.blue);
        textField.setBackgroundColor(Color.lightGray);
        textField.setBorderColor(Color.red);
        textField.render(gameContainer, graphics);

    }






    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(GameController.isButtonPressed(GButtons.START) && !clientNetworkConnection.isAuthenticated())
        {
            clientNetworkConnection.connect("192.168.1.3",textField.getText());
        }

    }

    @Override
    public int getID() {
        return 0;
    }



}
