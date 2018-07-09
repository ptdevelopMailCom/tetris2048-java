/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.example;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.audio.*;

/**
 *
 * @author Culmination
 */
public class MusicAndSound extends Applet
{
    String BGM = "BGM1.wav";
    String soundEffectFalling="fallingSound.wav";
    String soundEffectScore="score.wav";
    AudioStream backgroundMusic,falling,score;
            
    public MusicAndSound() throws Exception
    {
        
    }//empty constructor
    
    public void playScoreEffect() throws Exception
    {
        InputStream scoreI=new FileInputStream(soundEffectScore);
        score=new AudioStream(scoreI);
        AudioPlayer.player.start(score);
    }
    
    public void playFallingEffect() throws Exception
    {
        InputStream fallingI=new FileInputStream(soundEffectFalling);
        AudioStream falling=new AudioStream(fallingI);
        AudioPlayer.player.start(falling);
    }
    
    public void playBGM() throws Exception
    {
        InputStream backgroundMusicI = new FileInputStream(BGM);
        backgroundMusic=new AudioStream(backgroundMusicI);
        AudioPlayer.player.start(backgroundMusic);
        //AudioPlayer.player.start (cas);
//        AudioClip audioClip = getAudioClip(getCodeBase(), "BGM1.wav");
//        audioClip.play();
        
    }
    public void stopBGM()
    {
        AudioPlayer.player.stop(backgroundMusic);
    }



}
