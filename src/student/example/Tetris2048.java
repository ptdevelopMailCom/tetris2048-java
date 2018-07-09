package student.example;

import game.v2.Console;
import game.v2.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JFrame;
public class Tetris2048 extends Game
{
	private MTile mtile=new MTile(SettingOption.getNumForCurrent());	//new MTile(num)
        //private MTile mtile=new MTile(2048);
	//private Tile tile=new Tile(9,1,1);	//new Tile(num,x,y);
        //MusicAndSound music;
        boolean isPlayBGM=true;
        MusicAndSound music;
        
	public static void main(String[] args)
	{

                Console.getInstance()
                        .setTitle("Tetris 2048")
                        .setWidth(450)
                        .setHeight(700)
                        .setTheme(Console.Theme.LIGHT);
                


                Tetris2048 tetris=new Tetris2048();
                tetris.setFps(60);
                tetris.setShowFps(true);
                tetris.setBackground(Console.loadImage("/assets/board.png"));
                tetris.start();
                
                //start method from thread will call run method and it calls
                //cycle() method
	}
	

	@Override
	protected void cycle()
	{
            if(isPlayBGM&&!SettingOption.getStop())
            {
                try {
                    music=new MusicAndSound();
                    music.playBGM();
                    isPlayBGM=false;
                } catch (Exception ex) {}
            }
            if(SettingOption.getStop())
            {
                music.stopBGM();
                isPlayBGM=true;
            }
            
                mtile.draw();
		//tile.draw();
                SettingOption.display();
                Console.getInstance().update();
                
                if(SettingOption.getPlayAgain()==true)
                {
                    mtile.clear();
                    SettingOption.settingClear();
                    mtile=new MTile(SettingOption.getNumForCurrent());
                    SettingOption.setPlayAgain(false);
                    SettingOption.toggleStop();
                }
	}

	@Override
	protected void keyPressed(KeyEvent e)
	{
            switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
                    mtile.move(MTile.Direction.LEFT);
                    break;
		case KeyEvent.VK_RIGHT:
                    mtile.move(MTile.Direction.RIGHT);
                    break;
		case KeyEvent.VK_DOWN:
                    mtile.move(MTile.Direction.DOWN);
                    break;
                case KeyEvent.VK_SPACE:
                    SettingOption.toggleStop();
                    break;
                case KeyEvent.VK_E:
                    SettingOption.setLevelModeEasy();
                    break;
                case KeyEvent.VK_H:
                    SettingOption.setLevelModeHard();
                    break;
		}
	}

	@Override
	protected void mouseClicked(MouseEvent arg0)
	{
		if((arg0.getX()>200&&arg0.getX()<250) && (arg0.getY()>70&&arg0.getY()<100))
                {
                    System.out.println(arg0.getPoint());
                    SettingOption.setLevelModeEasy();
                }
                if((arg0.getX()>260&&arg0.getX()<290) && (arg0.getY()>70&&arg0.getY()<100))
                {
                    System.out.println(arg0.getPoint());
                    SettingOption.setLevelModeHard();
                }
		
	}



}
