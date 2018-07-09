package student.example;

import java.awt.Image;

import game.v2.Console;

public class Tile {

    public static final int TOP = 150;
    public static final int LEFT = 10;
    public static final int ROW = 5;
    public static final int COL = 4;
    public static final int SIZE = 110;
    protected final Console console = Console.getInstance();

    protected Image image;
    protected int row;
    protected int col;
    protected int num=0;

    public Tile(int n, int row, int col) {
        this(n); 	//calling the constructor with int n only
        this.row = row;
        this.col = col;
    }//end of constructor with three parameters

    public Tile(int n) {
        if (n<2 || n > 2048) {
            
            throw new IllegalArgumentException("invalid tile value: " + n);
        }
        if(n!=0)
        {
            num = 1 << (31 - Integer.numberOfLeadingZeros(n));
            image = Console.loadImage("/assets/tiles/" + num + ".png");
        }
        

    }//end of constructor

    public void draw() {
        draw(LEFT + col * SIZE, (int)(TOP + row * SIZE));
       //draw(LEFT + col * SIZE, TOP + row * SIZE);
       
    }//two draw mehods which suits different situations
    
    public void draw(int x, int y) {
        console.drawImage(x, y, image);
    }//end of draw methods
    
    //newly add,not default
    public void draw(int n,int x,int y)
    {
        image= Console.loadImage("/assets/tiles/" + n*2 + ".png");
        draw(x+1,y);
    }

    public Image getImg() {
        return image;
    }

    public void setImg(Image image) {
        this.image = image;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public String toString()
    {
        String s=getNum()+" "+getRow()+" "+getCol();
        return s;
    }

}
