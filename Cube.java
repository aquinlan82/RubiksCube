import javax.swing.*;
import java.awt.*;

public class Cube
{
    private Piece[][][] pieces;
    private final int LEFT=37;
    private final int RIGHT=39;
    private final int UP=38;
    private final int DOWN=40;
    
    /**
     * Initializes each piece of cube
     */
    public Cube()
    {
        pieces=new Piece[3][3][3];
        initColor();
        arrangePieces();
        
    }
    /**
     * Sets colors for each piece
     */
    public void initColor()
    {
        Color[] tops={Color.red,Color.black,Color.orange};
        Color[] sides={Color.yellow,Color.black,Color.white};
        Color[] front={Color.blue,Color.black,Color.green};
        
        for(int dep=0;dep<3;dep++)
        {
            for(int row=0;row<3;row++)
            {
                for(int col=0;col<3;col++)
                {
                    pieces[dep][row][col]=new Piece(front[dep],tops[row],sides[col]);
                }
            }
        }     
        
    }
    /**
     * Rotates pieces to face correct way
     */
    public void arrangePieces()
    {
        //flips left and bottom of each depth
        for(int dep=0;dep<3;dep++)
        {
            pieces[dep][0][0].flip("side");
            pieces[dep][1][0].flip("side");
            pieces[dep][2][0].flip("side");
            pieces[dep][2][0].flip("top");
            pieces[dep][2][1].flip("top");
            pieces[dep][2][2].flip("top");
        }
        //flips back depth
        for(int row=0;row<3;row++)
        {
            for(int col=0;col<3;col++)
            {
                pieces[2][row][col].flip("front");
            }
        }
    }
    /**
     * Draws Cube
     */
    public void drawCube(Graphics g)
    {
        int xPos=130;
        int yPos=330;
        int vSize=75;
        int hSize=75;
        int skew=35;
        
        int[] xPoints=new int[4];
        int[] yPoints=new int[4];
        
        //draw front face
        for(int x=0;x<3;x++)
        {
            for(int y=0;y<3;y++)
            {
                xPoints[0]=xPos+x*hSize;
                xPoints[1]=xPos+x*hSize+hSize;
                xPoints[2]=xPos+x*hSize+hSize;
                xPoints[3]=xPos+x*hSize;
                
                yPoints[0]=yPos+y*vSize;
                yPoints[1]=yPos+y*vSize;
                yPoints[2]=yPos+y*vSize-vSize;
                yPoints[3]=yPos+y*vSize-vSize;
                
                g.setColor(pieces[0][y][x].getFront());
                g.fillPolygon(xPoints, yPoints, 4);
                g.setColor(Color.black);
                g.drawPolygon(xPoints,yPoints,4);
            }
        }
        //draw top face
        //probably need to be altered if cube size changes
        xPos+=hSize-5;
        vSize*=0.5;
        yPos-=vSize*3+38;
        for(int x=0;x<3;x++)
        {
            for(int y=0;y<3;y++)
            {
                xPoints[0]=xPos+x*hSize-(skew*y);
                xPoints[1]=xPos+x*hSize+hSize-(skew*y);
                xPoints[2]=(xPos+x*hSize+hSize)+skew-(skew*y);
                xPoints[3]=(xPos+x*hSize)+skew-(skew*y);
                
                yPoints[0]=yPos+y*vSize;
                yPoints[1]=yPos+y*vSize;
                yPoints[2]=yPos+y*vSize-vSize;
                yPoints[3]=yPos+y*vSize-vSize;
                
                //change to use top
                g.setColor(pieces[Math.abs(2-y)][0][x].getTop());
                g.fillPolygon(xPoints, yPoints, 4);
                g.setColor(Color.black);
                g.drawPolygon(xPoints,yPoints,4);
            }
        }
        //draw right face
        //probably need to be altered if cube size changes
        xPos=xPos+5+hSize*2;
        yPos+=vSize*3+38;
        vSize*=2;
        hSize*=0.48;
        skew+=3;
        for(int x=0;x<3;x++)
        {
            for(int y=0;y<3;y++)
            {
                xPoints[0]=xPos+x*hSize;
                xPoints[1]=xPos+x*hSize+hSize;
                xPoints[2]=xPos+x*hSize+hSize;
                xPoints[3]=xPos+x*hSize;
                
                yPoints[0]=yPos+y*vSize-(skew*x);
                yPoints[1]=yPos+y*vSize-skew-(skew*x);
                yPoints[2]=yPos+y*vSize-vSize-skew-(skew*x);
                yPoints[3]=yPos+y*vSize-vSize-(skew*x);
                
                //change to use right
                g.setColor(pieces[x][y][2].getRight());
                g.fillPolygon(xPoints, yPoints, 4);
                g.setColor(Color.black);
                g.drawPolygon(xPoints,yPoints,4);
            }
        }
    }
    /**
     * Draw Cube as map
     */
    public void drawOutline(Graphics g)
    {
        int size=30;
        int x=270;
        int y=230; 
        
        drawFace(g,x,y,size,"front");
        drawFace(g,x,y+size*6,size,"back");
        drawFace(g,x,y-size*3,size,"top");
        drawFace(g,x,y+size*3,size,"bottom");
        drawFace(g,x-size*3,y,size,"left");
        drawFace(g,x+size*3,y,size,"right");
        
    }
    /**
     * Draws specified face
     */
    private void drawFace(Graphics g,int x,int y,int size,String side)
    {    
        int rowIndex=0;
        int colIndex=0;
        int depIndex=0;
        
        for(int row=0;row<3;row++)
        {
            for(int col=0;col<3;col++)
            {
                rowIndex=getRowIndex(side,row);
                colIndex=getColIndex(side,col);
                depIndex=getDepIndex(side,row,col);

                g.setColor(pieces[depIndex][rowIndex][colIndex].getSide(side));
                g.fillRect(x+col*size,y+row*size,size,size);
                g.setColor(Color.black);
                g.drawRect(x+col*size,y+row*size,size,size);
                g.drawString(depIndex+" "+rowIndex+" "+colIndex,x+col*size,y+row*size+20);
            }
        }
    }
    /**
     * Figures out which index to use in given loaction based in face
     */
    private int getDepIndex(String side,int row,int col)
    {
        switch(side)
        {
            case "front":    return 0;
            case "back":     return 2;
            case "top":      return Math.abs(2-row);
            case "bottom":   return row;
            case "left":     return Math.abs(2-col);
            case "right":    return col;
            default:         return -1;
        }
    }
    /**
     * Figures out which index to use in given location based on face
     */
    private int getRowIndex(String side,int row)
    {
        switch(side)
        {
            case "front":    return row;
            case "back":     return Math.abs(2-row);
            case "top":      return 0;
            case "bottom":   return 2;
            case "left":     return row;
            case "right":    return row;
            default:         return -1;
        }
    }
    /**
     * Figures out which index to use in given location based on face
     */
    private int getColIndex(String side,int col)
    {
        switch(side)
        {
            case "front":    return col;
            case "back":     return col;
            case "top":      return col;
            case "bottom":   return col;
            case "left":     return 0;
            case "right":    return 2;
            default:         return -1;
        }
    }
    /**
     * Returns given piece
     */
    public Piece getPiece(int row, int col, int dep)
    {
        return pieces[row][col][dep];
    }
    /**
     * Rotates entire cube upwards
     */
    public void rotateUp()
    {       
        //Move pieces
        Piece temp1;
        //only do first 2 rows because last row is top of other face
        for(int row=0;row<2;row++)
        {
            for(int col=0;col<3;col++)
            {
                //hold front
                temp1=pieces[0][row][col];
                //change front to bottom
                pieces[0][row][col]=pieces[row][2][col];
                //change bottom to back
                pieces[row][2][col]=pieces[2][Math.abs(2-row)][col];
                //change back to top
                pieces[2][Math.abs(2-row)][col]=pieces[Math.abs(2-row)][0][col];
                //change top to front
                pieces[Math.abs(2-row)][0][col]=temp1;
            }
        } 
        
        //rotate pieces
        for(int dep=0;dep<3;dep++)
        {
            for(int rows=0;rows<3;rows++)
            {
                for(int cols=0;cols<3;cols++)
                {
                    pieces[dep][rows][cols].rotateUp();
                }
            }
        }
    }
    /**
     * Rotates entire cube downwards
     */
    public void rotateDown()
    {
        rotateUp();
        rotateUp();
        rotateUp();
    }
    /**
     * Rotates entire cube right
     */
    public void rotateRight()
    {
        //Move pieces
        Piece temp1;
        //only do first 2 col because last col is side of other face
        for(int row=0;row<3;row++)
        {
            for(int col=0;col<2;col++)
            {
                //hold front
                temp1=pieces[0][row][col];
                //change front to left
                pieces[0][row][col]=pieces[Math.abs(2-col)][row][0];
                //change left to back
                pieces[Math.abs(2-col)][row][0]=pieces[2][row][Math.abs(2-col)];
                //change back to right
                pieces[2][row][Math.abs(2-col)]=pieces[col][row][2];
                //change right to front
                pieces[col][row][2]=temp1;
            }
        } 
        
        //rotate pieces
        for(int dep=0;dep<3;dep++)
        {
            for(int rows=0;rows<3;rows++)
            {
                for(int cols=0;cols<3;cols++)
                {
                    pieces[dep][rows][cols].rotateRight();
                }
            }
        }
    }
    /**
     * Rotates entire cube left
     */
    public void rotateLeft()
    {
        rotateRight();
        rotateRight();
        rotateRight();
    }
    /**
     * Rotates front face
     */
    public void moveFront(int dir)
    {
        //input is the keyCode for arrows, subtract 36 to get 1 or 3 executions
        for(int i=0;i<dir-36;i++)
        {
            //Move pieces
            Piece temp1;
                
            //corners
            temp1=pieces[0][0][0];
            pieces[0][0][0]=pieces[0][0][2];
            pieces[0][0][2]=pieces[0][2][2];
            pieces[0][2][2]=pieces[0][2][0];
            pieces[0][2][0]=temp1;
                
            //sides
            temp1=pieces[0][0][1];
            pieces[0][0][1]=pieces[0][1][2];
            pieces[0][1][2]=pieces[0][2][1];
            pieces[0][2][1]=pieces[0][1][0];
            pieces[0][1][0]=temp1;
               
            //rotate pieces
            for(int rows=0;rows<3;rows++)
            {
                for(int cols=0;cols<3;cols++)
                {
                    pieces[0][rows][cols].rotateDown();
                    pieces[0][rows][cols].rotateLeft();
                    pieces[0][rows][cols].rotateUp();
                }
            }
        }
    }
    /**
     * Rotates back face
     */
    public void moveBack(int dir)
    {
        //input is the keyCode for arrows, subtract 36 to get 1 or 3 executions
        for(int i=0;i<dir-36;i++)
        {
            //Move pieces
            Piece temp1;
                
            //corners
            temp1=pieces[2][0][0];
            pieces[2][0][0]=pieces[2][0][2];
            pieces[2][0][2]=pieces[2][2][2];
            pieces[2][2][2]=pieces[2][2][0];
            pieces[2][2][0]=temp1;
                
            //sides
            temp1=pieces[2][0][1];
            pieces[2][0][1]=pieces[2][1][2];
            pieces[2][1][2]=pieces[2][2][1];
            pieces[2][2][1]=pieces[2][1][0];
            pieces[2][1][0]=temp1;
               
            //rotate pieces
            for(int rows=0;rows<3;rows++)
            {
                for(int cols=0;cols<3;cols++)
                {
                    pieces[2][rows][cols].rotateDown();
                    pieces[2][rows][cols].rotateLeft();
                    pieces[2][rows][cols].rotateUp();
                }
            }
        }
    }
    /**
     * Rotates left face
     */
    public void moveLeft(int dir)
    {
        //input is the keyCode for arrows, subtract 37 to get 1 or 3 executions
        for(int i=0;i<dir-37;i++)
        {
            //Move pieces
            Piece temp1;
            for(int row=0;row<2;row++)
            {
                //hold front
                temp1=pieces[0][row][0];
                //change front to bottom
                pieces[0][row][0]=pieces[row][2][0];
                //change bottom to back
                pieces[row][2][0]=pieces[2][Math.abs(2-row)][0];
                //change back to top
                pieces[2][Math.abs(2-row)][0]=pieces[Math.abs(2-row)][0][0];
                //change top to front
                pieces[Math.abs(2-row)][0][0]=temp1;
                
            } 
            //rotate pieces
            for(int dep=0;dep<3;dep++)
            {
                for(int rows=0;rows<3;rows++)
                {
                    pieces[dep][rows][0].rotateUp();
                }
            }
        }
    }
    /**
     * Rotates right face
     */
    public void moveRight(int dir)
    {
        //input is the keyCode for arrows, subtract 37 to get 1 or 3 executions
        for(int i=0;i<dir-37;i++)
        {
            //Move pieces
            Piece temp1;
            for(int row=0;row<2;row++)
            {
                for(int col=2;col<3;col++)
                {
                    //hold front
                    temp1=pieces[0][row][col];
                    //change front to bottom
                    pieces[0][row][col]=pieces[row][2][col];
                    //change bottom to back
                    pieces[row][2][col]=pieces[2][Math.abs(2-row)][col];
                    //change back to top
                    pieces[2][Math.abs(2-row)][col]=pieces[Math.abs(2-row)][0][col];
                    //change top to front
                    pieces[Math.abs(2-row)][0][col]=temp1;
                }
            } 
            //rotate pieces
            for(int dep=0;dep<3;dep++)
            {
                for(int rows=0;rows<3;rows++)
                {
                    
                    pieces[dep][rows][2].rotateUp();
                    
                }
            }
        }
    }
    /**
     * Rotates top face
     */
    public void moveTop(int dir)
    {
        //input is the keyCode for arrows, subtract 36 to get 1 or 3 executions
        for(int i=0;i<dir-36;i++)
        {
            //Move pieces
            Piece temp1;
            for(int col=0;col<2;col++)
            {
                //hold front
                temp1=pieces[0][0][col];
                //change front to left
                pieces[0][0][col]=pieces[Math.abs(2-col)][0][0];
                //change left to back
                pieces[Math.abs(2-col)][0][0]=pieces[2][0][Math.abs(2-col)];
                //change back to right
                pieces[2][0][Math.abs(2-col)]=pieces[col][0][2];
                //change right to front
                pieces[col][0][2]=temp1;
            }
                        
            //rotate pieces
            for(int dep=0;dep<3;dep++)
            {
                for(int cols=0;cols<3;cols++)
                {
                    pieces[dep][0][cols].rotateRight();
                }
            }
        }
    }
    /**
     * Rotates bottom face
     */
    public void moveBottom(int dir)
    {
        //input is the keyCode for arrows, subtract 36 to get 1 or 3 executions
        for(int i=0;i<dir-36;i++)
        {
            //Move pieces
            Piece temp1;
            for(int col=0;col<2;col++)
            {
                //hold front
                temp1=pieces[0][2][col];
                //change front to left
                pieces[0][2][col]=pieces[Math.abs(2-col)][2][0];
                //change left to back
                pieces[Math.abs(2-col)][2][0]=pieces[2][2][Math.abs(2-col)];
                //change back to right
                pieces[2][2][Math.abs(2-col)]=pieces[col][2][2];
                //change right to front
                pieces[col][2][2]=temp1;
            }
                        
            //rotate pieces
            for(int dep=0;dep<3;dep++)
            {
                for(int cols=0;cols<3;cols++)
                {
                    pieces[dep][2][cols].rotateRight();
                }
            }
        }
    }
    
    public int[] getLocation(Piece piece) {
         for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
               for (int k = 0; k < pieces[i][j].length; k++) {
                  if (piece.equals(pieces[i][j][k])) {
                     return new int[]{i, j, k};  
                  }
               }
            }
         }
         return new int[]{-1, -1, -1};
    }
}
