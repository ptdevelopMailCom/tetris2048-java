/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.example;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author Culmination
 */
public class Manage
{

    private Tile[][] tile = new Tile[5][4];

    public Manage()
    {

    }//empty constructor

    public Manage(int n, int x, int y, int row) throws IOException
    {
        assignNonMovingTileToArray(n, x, y, row);
    }

    public void assignNonMovingTileToArray(int n, int x, int y, int row) throws IOException
    {
        try
        {
            if (tile[x][y] == null)
            {
                tile[x][y] = new Tile(n, x, y);
            }//case if the position of tile is empty
            else
            {
                int i = 1;
                while (tile[x - i][y] != null)
                {
                    i++;
                }
                tile[x - i][y] = new Tile(n, x - i, y);
            }
            
            for(int i=0;i<4;i++)
            {
                if(tile[0][i]!=null)
                {
                    System.out.println(tile[0][i]);
                    SettingOption.gameIsOver();
                }
            }

        }
        catch(Exception e)
        {
            SettingOption.gameIsOver();
        }

    }

    public boolean checkNextRowSameColEmpty(int n, int x, int y)
    {

        if (x < 4)
        {
            return tile[x + 1][y] == null;
        } else
        {
            return false;
        }

    }

    public boolean checkEqual(MTile aThis)
    {
        for (int i = 0; i < 5; i++)
        {
            if (tile[i][aThis.getCol()] != null)
            {
                return tile[i][aThis.getCol()].getNum() == aThis.getNum();  //check whether value are same
            }
        }
        return false;   //the whole column is null,then no equality, return false

    }

    public int removeTile(int n, int x, int y, int row)
    {

        for (int i = 0; i < 5; i++)
        {
            if (tile[i][y] != null)
            {
                x = i;
                tile[i][y] = null;
                break;
            }

        }
        return x;
    }

    public void drawInManage() throws IOException
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (tile[i][j] != null)
                {

                    tile[i][j].draw();
//                    String s = "tile at " + i + "," + j + " can be drawed";
//                    System.out.println(s);
                    if(tile[i][j].getNum()==2048)
                    {
                        SettingOption.gameIsWin();
                    }
                }
            }
        }
    }

    public boolean checkNextColEmptyMinus(int x, int y, Tile tile1)
    {
        if (y <= 0)
        {
            return false;
        }
        boolean isEmpty = tile[x][y] == null;
        if (isEmpty)
        {
            return true;
        } else
        {
            if (tile1.getNum() == tile[x][y].getNum() && tile[x - 1][y] == null)
            {
                return true;
            }
            return false;
        }

    }

    public boolean checkNextColEmptyPlus(int x, int y, Tile tile1)
    {
        if (y >= 4)
        {
            return false;
        }
        boolean isEmpty = tile[x][y] == null;
        if (isEmpty)
        {
            return true;
        } else
        {
            if (tile1.getNum() == tile[x][y].getNum() && tile[x - 1][y] == null)
            {
                return true;
            }
            return false;
        }

    }
    
    public int maxRowCanFall(int y)//receive the column index
    {
        for(int i=0;i<=4;i++)
        {
            if(tile[i][y]!=null)
            {
                return i-1;
            }
        }
        return 4;
    }

    
    public void clear()
    {
        tile = new Tile[5][4];
        //create a new null array
    }

    public static void main(String[] args) throws IOException
    {
        Manage tileToTest = new Manage();
        tileToTest.assignNonMovingTileToArray(8, 2, 1, 1);
        //tileToTest.drawInManage();
    }


}
