//Comment out the following package statement to compile separately.
//package com.javaworld.media.j2d;
package cmpt275.stolys;

/*
  Example01 illustrates some basics of Java 2D.
  This version is compliant with Java 1.2 Beta 3, Jun 1998.
  Please refer to: <BR>
  http://www.javaworld.com/javaworld/jw-07-1998/jw-07-media.html
  <P>
  @author Bill Day <bill.day@javaworld.com>
 * @version 1.0
 * @see java.awt.Graphics2D
 */


import java.awt.*;
import java.awt.event.*;

public class example01 extends Frame
    {
    /**
     * Instantiates an Example01 object.
     **/
    public static void main(String args[])
        {
        new example01();
        }

    /**
     * Our Example01 constructor sets the frame's size, adds the
     * visual components, and then makes them visible to the user.
     * It uses an adapter class to deal with the user closing
     * the frame.
     **/
    public example01()
        {
        //Title our frame.
        super("Java 2D Example01");
        
        //Set the size for the frame.
        setSize(400,300);
        
        //We need to turn on the visibility of our frame
        //by setting the Visible parameter to true.
        setVisible(true);
        
        //Now, we want to be sure we properly dispose of resources
        //this frame is using when the window is closed.  We use
        //an anonymous inner class adapter for this.
        addWindowListener(new WindowAdapter()
            {
            public void windowClosing(WindowEvent e)
                {dispose(); System.exit(0);}
            });
        }

    /**
     * The paint method provides the real magic.  Here we
     * cast the Graphics object to Graphics2D to illustrate
     * that we may use the same old graphics capabilities with
     * Graphics2D that we are used to using with Graphics.
     **/
    public void paint(Graphics g)
        {
        //Here is how we used to draw a square with width
        //of 200, height of 200, and starting at x=50, y=50.
        g.setColor(Color.red);
        g.drawRect(50,50,200,200);
        
        //Let's set the Color to blue and then use the Graphics2D
        //object to draw a rectangle, offset from the square.
        //So far, we've not done anything using Graphics2D that
        //we could not also do using Graphics.  (We are actually
        //using Graphics2D methods inherited from Graphics.)
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        g2d.drawRect(75,75,300,200);
        }
    }

