

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.text.DecimalFormat;

public class main
    {
    public static void main(String[] args)
        {
        SwingUtilities.invokeLater(new Runnable()
            {
            //@Override
            public void run()
                {
                createBaseUI();
                }
            });
        }

    private static void createBaseUI()
        {
        int frameWidth = 500;
        int frameHeight = 600;
        JFrame window = new JFrame("Shape Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(frameWidth, frameHeight);
        window.add(new UIelements(frameWidth, frameHeight));
        window.pack();
        window.setVisible(true);
        }
    }


/////////////////////////////////////////////////////////////
//
// UI element class, will be responsible for updating the UI as needed
//
/////////////////////////////////////////////////////////////
class UIelements extends JPanel implements ActionListener
    {
    //
    // Class Variables
    //
    private int frameWidth;
    private int frameHeight;
    
    
    //private compute cylinder;
    
    
    //Shape types
    private final int TRIANGLE = 1;
    private final int RECTANGLE = 1;
    private final int ELLIPSE = 1;
    private final int SQUARE = 1;
    private final int CIRCLE = 1;


    /////////////////////////////////////////////////////////////////
    ///
    /// Constructor for the cylinder class
    ///
    /////////////////////////////////////////////////////////////////
    public UIelements(int width, int height)
        {
        //Set the global frame size variables
        frameWidth = width;
        frameHeight = height;
        
        
        //Set no layout in JPanel
        this.setLayout(null);
    
        // Create generic shapes to be displayed -- give dimensions of area to be created
        //addGenericShapes(frameWidth, (int) Math.floor(frameHeight / 2));
    
        // Create middle section
        //addInputSection();
        }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// Gets the preferred size of the JFrame
    ///
    /////////////////////////////////////////////////////////////////
    public Dimension getPreferredSize()
        {
        return new Dimension(frameWidth, frameHeight);
        }

    
    /////////////////////////////////////////////////////////////////
    ///
    /// Overrides paintComponent to add our custom shape
    ///
    /////////////////////////////////////////////////////////////////
    public void paintComponent(Graphics g)
        {
        super.paintComponent(g);

        Graphics2D graphic = (Graphics2D) g;

        addGenericShapes(graphic, frameWidth, (int) Math.floor(frameHeight / 2));
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Will call function to handle calculate event
    ///
    /////////////////////////////////////////////////////////////////
    public void actionPerformed(ActionEvent e)
        {
        if(e.getSource() == null)
            {
            //Call function to handle the button pressed
            shapeOptionSelected(TRIANGLE);
            }
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Will handle a generic shape button click
    ///
    /////////////////////////////////////////////////////////////////
    public void shapeOptionSelected(int type)
        {
        switch(type)
            {
            default:
                //do something for each type
            }
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Will add the generic shapes section of the GUI
    ///
    /////////////////////////////////////////////////////////////////
    private void addGenericShapes(Graphics2D graphic, int width, int height)
        {
        int buffer = 10;
        
        //Create shape prompt label -- will take up 1/5 of top section
        JLabel genericShapePrompt = new JLabel("Please Select a Shape");
        genericShapePrompt.setBounds(buffer, buffer, width, (int) Math.floor(height/5));
        //                                x,      y, width,         height
        
        return;
        }
    
    }
