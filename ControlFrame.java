import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlFrame extends JFrame implements ActionListener, Runnable
{
    private String title;
    private JLayeredPane pane;
    
    /**
     * Configures frame that explains controls
     */
    public ControlFrame()
    {
        super("Controls");
        setPreferredSize(new Dimension(600,600));
        setResizable(false);
        setLocation(300,40);
        pack();
        setVisible(true);
        
        title="Q";
        
        setupPane();
        createPicture();
        createButtons();
    }
    
    public void run() {
    
    }
    
    /**
     * Configure pane
     */
    private void setupPane()
    {
        pane=new JLayeredPane();
        pane.setLayout(null);
        pane.setBounds(0,0,600,350);
        add(pane);
    }
    /**
     * Adds info picture to panel 
     */
    private void createPicture()
    {
        JLabel pic=new JLabel();
        pic.setIcon(createImageIcon("/Resources/"+title+".png"));
        pic.setBounds(150,10,300,300);
        
        pane.add(pic,JLayeredPane.MODAL_LAYER);
    }
    /**
     * Adds words to explain controls
     */
    private void createButtons()
    {
        String[] keys={"Q","W","E","R","T","Y","A","S","D","F","G","H"};
        for(int i=0; i<12;i++)
        {
            //left buffer of 50, space out by 80, start over after 6 buttons
            int x=50+(i%6)*80;
            //divide by # (11) to make decimal, round to 0 or 1, multiply by gap between rows, offset by 350
            int y=((int)Math.round((double)i/11)*60)+350;
            
            JButton temp=new JButton(keys[i]);
            temp.setBounds(x,y,50,50);
            temp.addActionListener(this);
            pane.add(temp);
        }
        JButton right=new JButton("right");
        right.setBounds(325,500,60,50);
        right.addActionListener(this);
        pane.add(right);
       
        JButton left=new JButton("left");
        left.setBounds(180,500,60,50);
        left.addActionListener(this);
        pane.add(left);
     
        JButton up=new JButton("up");
        up.setBounds(250,480,65,30);
        up.addActionListener(this);
        pane.add(up);
        
        JButton down=new JButton("down");
        down.setBounds(250,530,65,30);
        down.addActionListener(this);
        pane.add(down);
        
    }
    /**
     * Gets image resource
     */
    private ImageIcon createImageIcon(String path) 
    {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    /**
     * Change picture when button pressed
     */
    public void actionPerformed(ActionEvent e)
    {
        title=e.getActionCommand();
        pane.remove(pane.getComponentsInLayer(JLayeredPane.MODAL_LAYER)[0]);
        createPicture();
        repaint();
        revalidate();
    }
}
