package student.example;

import game.v2.Console;
import java.awt.Image;
import java.io.IOException;

import student.example.Tile;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MTile extends Tile implements Movable {

    //public static final int WAIT = 1000;
    private long ts = System.currentTimeMillis();		//find current time
    private Manage manageTile = new Manage();
    boolean changeValue=true;
    
    private int positionX=0;
    private int positionY=0;
    private boolean performColAnimation=false;
    private boolean performWholeRowAnimation=false;
    int storeLastRow;
    

    public MTile(int n) {
        super(n);
    }//similar to superclass constructor

    public MTile(int n, int row, int col) {
        super(n, row, col);
    }//end of constructors

    @Override
    public void fall() {
        if (System.currentTimeMillis() - ts > SettingOption.getWaitTimeSecond())
        {
//            positionX=this.getRow();
//            positionY=this.getCol();
            //System.out.println(manageTile.checkNextRowSameColEmpty(getNum(), getRow(), getCol()));
            if (manageTile.checkNextRowSameColEmpty(getNum(), getRow(), getCol()))
            {
                row++;
                oneTileAnimation();
                

            } 
            else//(!manageTile.checkNextRowSameColEmpty(getNum(),getRow(),getCol())
            {
                //System.out.println(manageTile.checkEqual(getNum(), getRow(), getCol(),row));
                //if(manageTile.checkEqual(getNum(), getRow(), getCol(),row))
                if(manageTile.checkEqual(this))
                {
                    //remove the tile orginally
                    int i=manageTile.removeTile(getNum(), getRow(), getCol(),row);
                    
                    setNum(getNum()*2);
                    SettingOption.calculationOnScore(this);
                    Image image=Console.loadImage("/assets/tiles/" + getNum() + ".png");
                    if (row < 4)
                    {
                        setRow(getRow() + 1);
                    }
                    else
                    {
                        setRow(i);
                    }
                    setImg(image);
                    
                }
                else
                {
                    try {
                        manageTile.assignNonMovingTileToArray(getNum(), getRow(), getCol(), row);
                    } catch (IOException ex) {
                        Logger.getLogger(MTile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    num = SettingOption.getNumForCurrent();    //value to be shown in next image
                    image = Console.loadImage("/assets/tiles/" + num + ".png"); //iamge is an object from Tile class
                    row = 0;
                    super.draw();
                }


            }

            ts = System.currentTimeMillis();
        }
    }

    @Override
    public void move(Direction dir) {
        if(!SettingOption.getStop())    //prevent case that the game is stopped but the user can still control the game
        {
            switch (dir) {
            case DOWN:
                storeLastRow=row;
                performWholeRowAnimation=true;
                wholeAnimationOfRow();
                try
                {
                    MusicAndSound music=new MusicAndSound();
                    music.playFallingEffect();
                } catch (Exception ex){}
                row = ROW - 1;
                break;
            case LEFT:
                if (col > 0 && manageTile.checkNextColEmptyPlus(getRow(),getCol()-1,this)) {
                    col--;
                    colAnimationMinus();
                }
                break;
            case RIGHT:
                if (col < COL - 1&&manageTile.checkNextColEmptyPlus(getRow(),getCol()+1,this)) {
                    col++;
                    colAnimationPlus();
                }
                break;
            //DOWN,LEFT,RIGHT from enum Direction
        }
        
        }

    }//end of move method
    

    @Override()
    public void draw() {
        positionX=this.getRow();
        positionY=this.getCol();
        //System.out.println(this.getRow());
        if(!SettingOption.getStop())
        {
            fall(); 
        }
        else
        {
            SettingOption.stopAndResume();
        }
        if(this.getCol()-positionY>0)
        {
            colAnimationPlus();
        }
        if(this.getCol()-positionY<0)
        {
            colAnimationMinus();
        }
        if(performWholeRowAnimation)
        {
                wholeAnimationOfRow();
                performWholeRowAnimation=false;    
        }

        super.draw();   //fall without animation
        try {
            manageTile.drawInManage();
        } catch (IOException ex) {
            Logger.getLogger(MTile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
        public void oneTileAnimation()
        {
            //System.out.println(row-1);
            if(this.getRow()-positionX!=0)
            {
                for(double i=0.1;i<=1;i=i+0.001)
                {
                    //System.out.println((row-1)+1*i);
                    //LEFT + col * SIZE
                    draw(LEFT + col * SIZE, (int)(TOP + (positionX+i) * SIZE));
                }
            }

        }
        
        public void colAnimationMinus()
        {
            
            if(this.getCol()-positionY<0)
            {   
                //System.out.println("run Left");
                for(double i=0;i<1;i=i+0.001)
                {
                    System.out.println(col+1-i);
                    draw((int)(LEFT + ((col+1)-i) * SIZE), (TOP + row * SIZE));
                }
            }
        }
        
        public void colAnimationPlus()
        {
            if(this.getCol()-positionY>0)
            { 
                System.out.println("run Right");
                for(double i=0;i<1;i=i+0.001)
                {
                    System.out.println(col-1+i);
                    draw((int)(LEFT + ((col-1)+i) * SIZE), (TOP + row * SIZE));
                }
            }
        }
        
        public void wholeAnimationOfRow()
        {
            int indexToFall=manageTile.maxRowCanFall(this.getCol());
                System.out.println(indexToFall);
                for(double i=0.1;i<=indexToFall-storeLastRow;i=i+0.01)
                {
                    System.out.println(storeLastRow+i);
                    draw(LEFT + col * SIZE, (int)(TOP + (storeLastRow+i) * SIZE));
                }
        }
        
        public void clear()
        {
            manageTile.clear();
        }

    


}
