package com.GlitchyDev.GameStates;


import com.GlitchyDev.Networking.ClientNetworkConnection;
import com.GlitchyDev.Utility.BasicMonitoredGameState;
import com.GlitchyDev.Utility.GButtons;
import com.GlitchyDev.Utility.GameController;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class NetworkClientState extends BasicMonitoredGameState {

    private ClientNetworkConnection clientNetworkConnection;


    public NetworkClientState(ClientNetworkConnection clientNetworkConnection) {
        this.clientNetworkConnection = clientNetworkConnection;
    }


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {


    }





    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {



        graphics.drawString("Utilization " + getTotalUtilization(),0,0);

    }


    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (GameController.isButtonPressed(GButtons.START) && !clientNetworkConnection.isAuthenticated()) {
            //clientNetworkConnection.connect("192.168.1.3", textField.getText());
        }
        //clientNetworkConnection.updateConnections();

    }

    @Override
    public int getID() {
        return 0;
    }

}


      /*
        graphics.setColor(Color.blue);
        graphics.drawString(String.valueOf(getTotalUtilization()),400,0);
        graphics.setColor(Color.white);
        */


        /*
        for (int i = 0; i < 1000; i++) {

            SpriteUtil.drawSprite("Test",(int)(Math.random()*500),(int)(Math.random()*500),0.2);
        }
        */



        /*
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

        graphics.setColor(Color.blue);
        graphics.drawString(String.valueOf(getTotalUtilization()),400,0);

        //SpriteUtil.drawSprite("Test",250,0,1.0f);
        SpriteUtil.drawSprite("Test",100,0,1.0f);


        Image i = AssetLoader.getSprite("Test");

        for(int x = 0; x < i.getWidth(); x++)
        {
            for(int y = 0; y < i.getHeight(); y++)
            {
                Color color = i.getColor(x,y);
                //System.out.println(x + " " + y + ": " + color.a + color.r + color.g + color.b);

                if (color.a <= 0.25) {
                    i.getGraphics().setColor(Color.green);
                    if (color.a <= 0.15) {
                        i.getGraphics().setColor(Color.blue);
                        if (color.a <= 0.10) {
                            i.getGraphics().setColor(Color.red);
                            if (i.getColor(x, y).a <= 0.05) {
                                i.getGraphics().setColor(Color.yellow);
                            }
                        }
                    }
                    //int width = (int) (25 * Math.random());
                    //int height = (int) (25 * Math.random());
                    i.getGraphics().fillRect(x, y, 1, 1);
                }
            }
        }
        */
