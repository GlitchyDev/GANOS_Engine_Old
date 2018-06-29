package com.GlitchyDev;



import org.newdawn.slick.*;

import javax.imageio.ImageIO;
import javax.sound.midi.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;

public class Main {


    public static void main(String[] args) {

        System.out.println("Initializing Synthesiser");
        long startSynth = System.currentTimeMillis();
        Synthesizer midiSynth = null;
        try {
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }


        midiSynth.unloadAllInstruments(midiSynth.getDefaultSoundbank());
        try {
            midiSynth.loadAllInstruments(MidiSystem.getSoundbank(new File( "DebugAssets/MMBN1.sf2" )));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        //get and load default instrument and channel lists
        Instrument[] instr = sbDefault.getInstruments();
        int a = 0;
        for(Instrument ins: instr)
        {
            System.out.println(ins.getName() + " " + a);
            a++;
        }
        MidiChannel[] mChannels = midiSynth.getChannels();

        midiSynth.loadInstrument(instr[0]);//load an instrument
        */

        Sequencer sequencer = null;
        try {
            sequencer = MidiSystem.getSequencer(false);

            sequencer.open();
            sequencer.getTransmitter().setReceiver(midiSynth.getReceiver());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(new File("DebugAssets/song000.mid")));
            sequencer.setSequence(inputStream);



            Sequencer finalSequencer = sequencer;
            sequencer.addMetaEventListener(event -> {
                switch(event.getType())
                {
                    case 81:
                        System.out.println("Sequence Started Playing");
                        break;
                    case 47:
                        System.out.println("Sequencer finished playing");
                        finalSequencer.close();
                        break;
                    case 6:
                        //-finalSequencer.setLoopStartPoint(finalSequencer.getLoopStartPoint() + 1);
                        System.out.println("Adjusted Time " + finalSequencer.getLoopStartPoint());


                        break;
                    default:
                        System.out.println("Unknown Sequencer Event " + event.getType());

                }

            });

            long endSynth = System.currentTimeMillis();
            System.out.println("Timing " + (endSynth-startSynth)/1000.0 + " " + sequencer.getMicrosecondLength() + " " + sequencer.getTickLength());
            sequencer.setTickPosition(1100);
            sequencer.setLoopStartPoint(1150);
            sequencer.setLoopEndPoint(1920);
            sequencer.setLoopCount(100);
            sequencer.start();

            // 1142







        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        /*

        for(int i = 1; i < 100; i++) {
            mChannels[0].noteOn(i, 100);//On channel 0, play note number 60 with velocity 100
            try {
                Thread.sleep(500); // wait time in milliseconds to control duration
            } catch (InterruptedException e) {
            }
            mChannels[0].noteOff(60);//turn of the note
        }
        */

    }
}



        /*
        try {
            BufferedImage source = ImageIO.read(new File("GameAssets/Sprites/Font/Lettering.png"));


            int count = 0;
            for(int sy = 0; sy < source.getHeight()/16.0; sy++) {
                for (int sx = 0; sx < source.getWidth() / 8.0; sx++) {
                    BufferedImage image = new BufferedImage(7,16,BufferedImage.TYPE_INT_ARGB);

                    for(int x = 0; x < 7; x++)
                    {
                        for(int y = 0; y < 16; y++)
                        {
                            image.setRGB(x,y,source.getRGB(sx * 8 + x, sy * 16 + y));
                        }
                    }
                    ImageIO.write(image,"PNG",new File("GameAssets/Sprites/Font/" + count + ".png"));
                    count++;

                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        */


