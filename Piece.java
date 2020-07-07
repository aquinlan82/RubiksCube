import java.awt.*;

public class Piece
{
    private Color top;
    private Color bottom;
    private Color right;
    private Color left;
    private Color front;
    private Color back;
    
    /**
     * Sets start value of piece
     */
    public Piece(Color first, Color second, Color third)
    {
       front=first;
       top=second;
       right=third;
       bottom=left=back=Color.black;
    }
    /**
     * Sets start value of piece
     */
    public Piece(Color first, Color second, Color third, Color fourth, Color fifth, Color sixth)
    {
       front=first;
       top=second;
       right=third;
       bottom=fourth;
       left=fifth;
       back=sixth;
    }
    /**
     * Draws piece as map
     */
    public void drawOutline(Graphics g, int x, int y, int size)
    {
        g.setColor(front);
        g.fillRect(x,y,size,size);
        g.setColor(right);
        g.fillRect(x+size,y,size,size);
        g.setColor(top);
        g.fillRect(x,y-size,size,size);
        g.setColor(left);
        g.fillRect(x-size,y,size,size);
        g.setColor(bottom);
        g.fillRect(x,y+size,size,size);
        g.setColor(back);
        g.fillRect(x,y+size*2,size,size);
    }
    /**
     * Draws top,right, and front sides of piece
     */
    public void drawPiece(Graphics g)
    {
        g.setColor(front);
        g.fillRect(100,300,100,100);
        g.setColor(right);
        g.fillRect(200,300,100,100);
        g.setColor(top);
        g.fillRect(100,200,100,100);
    }
    /**
     * Rotates sides upwards 
     */
    public void rotateUp()
    {
        Color temp=front;
        front=bottom;
        bottom=back;
        back=top;
        top=temp;
    }
    /**
     * Rotates sides downwards 
     */
    public void rotateDown()
    {
        Color temp=front;
        front=top;
        top=back;
        back=bottom;
        bottom=temp;
    }
    /**
     * Rotates sides left 
     */
    public void rotateLeft()
    {
        Color temp=front;
        front=right;
        right=back;
        back=left;
        left=temp;
    }
    /**
     * Rotates sides right 
     */
    public void rotateRight()
    {
        Color temp=front;
        front=left;
        left=back;
        back=right;
        right=temp;
    }
    /**
     * Flips two sides, impossible in real life
     */
    public void flip(String type)
    {
        Color temp;
        
        switch(type)
        {
            case "side":     temp=left;
                             left=right;
                             right=temp;
                             break;
            case "top":      temp=top;
                             top=bottom;
                             bottom=temp;
                             break;
            case "front":    temp=front;
                             front=back;
                             back=temp;
                             break;
        }
    }
    public Color getFront(){   return front;   }
    public Color getBack(){    return back;   }
    public Color getLeft(){    return left;   }
    public Color getRight(){   return right;   }
    public Color getTop(){     return top;   }
    public Color getBottom(){  return bottom;   }
    public Color getSide(String side)
    {
        switch(side)
        {
            case "front": return front;
            case "back":  return back;
            case "left":  return left;
            case "right": return right;
            case "top":   return top;
            case "bottom":return bottom;
            default:      return null;
        }
    }
    public String getSides()
    {
        return "front:"+front+" back:"+back +" left:"+left+" right:"+right+" top:"+top+" bottom:"+bottom;
    }
    
    public boolean equals(Piece other) {
       for (int i = 0; i < 4; i++) {
          if (other.getTop() == top && other.getBottom() == bottom &&
              other.getRight() == right && other.getLeft() == left &&
              other.getFront() == front && other.getBack() == back) {
              return true;    
          }  
          rotateRight();
       }
       for (int i = 0; i < 4; i++) {
          if (other.getTop() == top && other.getBottom() == bottom &&
              other.getRight() == right && other.getLeft() == left &&
              other.getFront() == front && other.getBack() == back) {
              return true;    
          }  
          rotateUp();
       }
       return false;
    }
    
}
