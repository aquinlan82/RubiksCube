import javax.swing.*;
import java.awt.*;

public class Easel extends JPanel
{
    private Cube cube;
    private Piece piece;
    
    /**
     * Constructor for objects of class Easel
     */
    public Easel(Cube cubeIn)
    {
        cube=cubeIn;
    }
    /**
     * Constructor for objects of class Easel, to be used for diagnoses
     */
    public Easel(Piece pieceIn)
    {
        piece=pieceIn;
    }
    /**
     * Draws things, can change for diagnostic purposes
     */
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //change based on need
        //Options: piece.drawOutline, piece.drawPiece, cube.drawOutline, cube.drawCube
        //Remember to change the constructor used if needed
        cube.drawCube(g);
    }
}
