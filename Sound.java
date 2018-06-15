//Sound.java
//Jakir Ansari/ Alex Shi/ Jason Wong
//Sound.java is used to play music in the game

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//Sound is used to store audio clips and soundtracks

class Sound {
    private Clip myClip;                //myClip holds the audio clip to be stored
    private boolean isPlaying = true;   //isPlaying holds whether or not the audio clip is playing

    public Sound(String songName) {
        //This try/except statement loads an audio file
        try {
            //This opens an audio input stream
            File file = new File(songName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            this.myClip = AudioSystem.getClip();
            this.myClip.open(ais);
        }
        //These catch statements return specific errors that may have occured
        catch(IOException e) {
            e.printStackTrace();    //This displays the specific lines that the error has occured within the program
            System.out.println("Do you have the right file?");
        }
        catch(UnsupportedAudioFileException e) {
            e.printStackTrace();    //This displays the specific lines that the error has occured within the program
            System.out.println("Unsupported audio file");
        }
        catch(LineUnavailableException e) {
            e.printStackTrace();    //This displays the specific lines that the error has occured within the program
            System.out.println("Line is unavailable");
        }
    }

    //This method loopd the audio clip
    public void loop() {
        this.myClip.loop(Clip.LOOP_CONTINUOUSLY);
        isPlaying = true;
    }

    //This method plays the audio clip from the beginning
    public void play() {
        this.myClip.setFramePosition(0);
        this.myClip.start();
    }

    //This method stops the music file
    public void stop() {
    	this.myClip.stop();
    }

    //This method resets song position
    public void setPosition() {
    	this.myClip.setFramePosition(0);
    }

    //This method plays/pauses the audio clip
    public void playPause() {
        if(this.myClip.isRunning()) {
            this.myClip.stop();
            isPlaying = false;
        }
        else {
            this.myClip.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying = true;
        }
    }

    //This method returns whether or not the audio clip is playing
    public boolean getIsPlaying() {
        return isPlaying;
    }

    //This is something necessary in JCreator
    public static void main(String[] args){}
}