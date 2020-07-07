import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
Rubiks Cube Simulator
Created by Allison Quinlan
2017 ish

**/

public class CubeFrame extends JFrame implements ActionListener, KeyListener
{
    private final int LEFT=37;
    private final int RIGHT=39;
    private final int UP=38;
    private final int DOWN=40;
    
    private ControlFrame control;
    private JPanel buttonPanel;
    private Easel cubePanel;
    private Cube cube;
    private CubeSolver solver;    
    public static void main(String [] args)
    {
        CubeFrame frame=new CubeFrame();
    }
    /**
     * Configs frame and initilizes class variables
     */
    public CubeFrame()
    {
        super("Rubik's Cube");
        setPreferredSize(new Dimension(600,600));
        setResizable(false);
        setLocation(20,20);
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        pack();
        setVisible(true); 
        cube=new Cube();
        setupButtonPanel();
        setupCubePanel();


    }
    /**
     * Creates buttons, sets config for buttons and panel
     */
    private void setupButtonPanel()
    {
        buttonPanel=new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0,0,600,100);
        buttonPanel.setVisible(true);
        
        JButton controls=new JButton("Controls");
        JButton solve=new JButton("Solve");
        JButton scramble=new JButton("Scramble");
        
        controls.setBounds(30,30,150,40);
        controls.addActionListener(this);
        solve.setBounds(190,30,150,40);
        solve.addActionListener(this);
        scramble.setBounds(350,30,150,40);
        scramble.addActionListener(this);
        
        buttonPanel.add(controls);
        buttonPanel.add(solve);
        buttonPanel.add(scramble);
        
        add(buttonPanel);
    }
    /**
     * Creates panel and sets config
     */
    private void setupCubePanel()
    {    
        cubePanel=new Easel(cube);
        cubePanel.setLayout(null);
        cubePanel.setBounds(0,100,600,500);
        cubePanel.setVisible(true);
        
        add(cubePanel);
                
        repaint();
        revalidate();
    }
    /**
     * Does several random moves
     */
    public void scramble()
    {
        Thread thread = new Thread(){
            public void run(){
               animateScramble();
            }
         };
         thread.start();
    }
    
    public void animateScramble() {
        int rand;
        KeyEvent key;
        char[] chars={'Q','W','E','R','T','Y','A','S','D','F','G','H'};

      for(int i=0;i<30;i++)
      {
         rand=(int)Math.round(Math.random()*11); 
         key=new KeyEvent(this, 0, 0, 0, (int) chars[rand]);
         keyPressed(key);
         try {
            Thread.sleep(10);
         } catch (Exception e) {}
       }
     } 
     
     public void animateSolve() {
        KeyEvent key;

         /*while(solver.solved == false)
         {
             solver.stepForward(cube);
             key=new KeyEvent(this, 0, 0, 0, solver.getNextKey());
             keyPressed(key);
            try {
               Thread.sleep(1);
            } catch (Exception e) {}
          }*/
     } 
    
    /**
     * 
     */
    public void solve()
    {
      solver = new CubeSolver(cube);
      
      Thread thread = new Thread(){
            public void run(){
               animateSolve();
            }
         };
         thread.start();
    }
    /**
     * Determine result of button press
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand()=="Controls"){
            control=new ControlFrame();
            Thread t = new Thread(control);
            t.start(); 
            requestFocusInWindow();   
        }
        if(e.getActionCommand()=="Solve") {
            solve();
            requestFocusInWindow();  
        } 
        if(e.getActionCommand()=="Scramble") {
            scramble(); 
            requestFocusInWindow();   
        }
    }
    /**
     * Change based on user input
     */
    public void keyPressed(KeyEvent e)
    {       
        switch(e.getKeyCode())
        {
            case UP:     cube.rotateUp();
                         break;
            case DOWN:   cube.rotateDown();
                         break;
            case RIGHT:  cube.rotateRight();
                         break;
            case LEFT:   cube.rotateLeft();
                         break;
            case (int)'Q':   cube.moveLeft(UP);
                             break;
            case (int)'W':   cube.moveBack(LEFT);
                             break;
            case (int)'E':   cube.moveBack(RIGHT);
                             break;
            case (int)'R':   cube.moveTop(RIGHT);
                             break;
            case (int)'T':   cube.moveTop(LEFT);
                             break;
            case (int)'Y':   cube.moveRight(UP);
                             break;
            case (int)'A':   cube.moveLeft(DOWN);
                             break;
            case (int)'S':   cube.moveFront(LEFT);
                             break;
            case (int)'D':   cube.moveFront(RIGHT);
                             break;
            case (int)'F':   cube.moveBottom(LEFT);
                             break;
            case (int)'G':   cube.moveBottom(RIGHT);
                             break;
            case (int)'H':   cube.moveRight(DOWN);
                             break;
            case (int)'P': solver.stepForward(cube);
                           KeyEvent key=new KeyEvent(this, 0, 0, 0, solver.getNextKey());
                           keyPressed(key);
                           break;
            case (int)'O':  System.out.println(cube.getLocation(new Piece(Color.black, Color.red, Color.blue, Color.black, Color.black, Color.white))[0]);
                            System.out.println(cube.getLocation(new Piece(Color.blue, Color.red, Color.white))[1]);
                            System.out.println(cube.getLocation(new Piece(Color.blue, Color.red, Color.white))[2]);
        }
        setupCubePanel();
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
