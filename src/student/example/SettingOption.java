/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.example;

import game.v2.Console;
import game.v2.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Culmination
 */
public class SettingOption
{

    //implements static things
    private static long startTime = System.currentTimeMillis();
    private static int number[] = new int[2];
    private static int choice = 0;
    private static int levelMode=1;
    private static boolean stop = false;
    private static int accumulatedStopTimeLength = 0;
    private static int score=0;
    private static boolean gameOver=false;
    private static int bestScore; 
    private static boolean playAgain;
    private static boolean askOnce=true;


    public static String timeLength()
    {
        long endTime = System.currentTimeMillis();
        long difference = 0;
        //System.out.println(accumulatedStopTimeLength);
        difference = endTime - startTime;
        int differenceInSecond=(int) (difference * 1e-3-accumulatedStopTimeLength);
//        int second = (int) ((difference * 1e-3) % 60) - (accumulatedStopTimeLength % 60);
//        int minute = (int) ((difference * 1e-3) / 60 - (accumulatedStopTimeLength / 60));
        int second = (int) (differenceInSecond % 60);
        int minute = (int) (differenceInSecond / 60 );
        String time = String.format("%d : %d ", minute, second);
        return time;
    }//to display in the game

    public static int getNumForCurrent()
    {
        //clear the value and generate new 
        if (choice == 1)
        {
            number[0] = 0;
        } else
        {
            number[1] = 0;
        }

        for (int i = 0; i < 2; i++)    //initial generating number
        {
            if (number[i] == 0)
            {
                number[i] = levelMode(levelMode);
            }
        }

        if (choice == 0)
        {
            choice = 1;   //next tile is number[1]
            return number[0];   //current number, next time will generate new
        } else
        {
            choice = 0;   //next tile is ,return number[0]
            return number[1];
        }
    }

    public static int levelMode(int chooseMode)
    {
        Random rnd = new Random();
        int randomResult = rnd.nextInt(10);

        if (chooseMode==1)//true for easy, false for hard
        {
            if (randomResult == 0)
            {
                return 2;
            } else if (randomResult >= 1 && randomResult <= 3)
            {
                return 4;
            } else
            {
                return 8;
            }
        }
        else if(chooseMode==3)
        {
          if (randomResult == 0)
          {
              return 8;
          } 
          else if (randomResult >= 1 && randomResult <= 3)
          {
              return 4;
          } 
          else
          {
              return 2;
          }
        }
        return 0;
    }
    public static void setLevelModeEasy()
    {
        levelMode=1;
    }
    
    public static void setLevelModeHard()
    {
        levelMode=3;
    }
    public static int getWaitTimeSecond()
    {
        if(levelMode==1)
        {
            return 1000;
        }
        else if(levelMode==3)
        {
            return 500;
        }
        return 0;
    }
//these three functions are about stop the game and calculation the stopped time
    public static void stopAndResume()
    {
        Console.pause(1000);
        accumulatedStopTimeLength = accumulatedStopTimeLength + 1;
        
    }

    public static void toggleStop()
    {
        stop = !stop;
    }

    public static boolean getStop()
    {
        return stop;
    }
/////////////////////////////////////////////////////////////////
    
    public static void calculationOnScore(MTile tile)
    {
        try
        {
            score=score+tile.getNum();
            MusicAndSound music=new MusicAndSound();
            music.playScoreEffect();
        } catch (Exception ex)
        {
            Logger.getLogger(SettingOption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//the following will animate the movement of tile
    public static int getScore()
    {
        return score;
    }
    
       public static int bestScore() throws IOException  //show best score
    {
        try
        {
            File f=new File("bestScore.txt");
            if(f.exists()==false)
            {
                FileWriter out = new FileWriter(f);
                out.close();
            }
            Scanner scan = new Scanner(f);
            bestScore = scan.nextInt();
            //System.out.println(bestScore);

            if(getScore()<bestScore)
            {
                bestScore=bestScore;
            }
            
            if(getScore()>bestScore)
            {
                //System.out.println("get");
                FileWriter out = new FileWriter(f);
                bestScore=score;
                out.write(String.valueOf(bestScore));
                out.close();
            }

        }
        catch(Exception e)
        {
            
            System.err.println("Error:"+e);
        }
           
 
        return bestScore;

    }

    
    public static void display()
    {
        Console.getInstance().drawText(120, 120, "Time: " + SettingOption.timeLength(), new Font("Arial", Font.BOLD, 16), Color.BLACK);
        Console.getInstance().drawText(377, 120, String.format("%d", number[choice]), new Font("Arial", Font.BOLD, 20), Color.BLACK);
        Console.getInstance().drawText(187, 62, String.format("%d", score), new Font("Arial", Font.BOLD, 20), Color.BLACK);
        try
        {
            Console.getInstance().drawText(355, 62, String.format("%d",bestScore()), new Font("Arial", Font.BOLD, 20), Color.BLACK);
        } catch (IOException ex)
        {
            Logger.getLogger(SettingOption.class.getName()).log(Level.SEVERE, null, ex);
        }
        Console.getInstance().drawText(123, 87, "Click here for: ", new Font("Arial", Font.BOLD, 14), Color.darkGray);
        Console.getInstance().drawText(226, 87, "Easy", new Font("Arial", Font.BOLD, 14), Color.cyan);
        Console.getInstance().drawText(266, 87, "Hard", new Font("Arial", Font.BOLD, 14), Color.RED);
    }
    
    public static void gameIsOver() throws IOException
    {
        JOptionPane.showMessageDialog(null,"Game is Over, you lose. Best Score: "+bestScore());
        toggleStop();
        playAgainAsk();
    }
    public static boolean getPlayAgain()
    {
        return playAgain;
    }
    
    public static void playAgainAsk()
    {
        if (JOptionPane.showConfirmDialog(null, "Confirm to play again", "", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
        {
            playAgain=true;
        }
        else
        {
            Console.getInstance().close();
            System.exit(0);
        }
    }
    
    public static void setPlayAgain(boolean c)
    {
        playAgain=c;
    }
    
    public static void gameIsWin() throws IOException
    {

        System.out.println("Executed");
        JOptionPane.showMessageDialog(null,"Congraulation, you win. Best Score: "+bestScore());
        toggleStop();
        playAgainAsk();


    }
////////////////////////////////////
    public static void settingClear()
    {
        score=0;
        for(int i=0;i<number.length;i++)
        {
            number[i]=0;
        }
        startTime = System.currentTimeMillis();
        accumulatedStopTimeLength=0;
        
    }

}
