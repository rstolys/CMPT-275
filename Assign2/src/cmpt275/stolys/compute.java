package cmpt275.stolys;

import java.lang.Math;

////////////////////////////////////////////////////
///
/// This class wil determine the volume of the shape provided
/// The class will also perform calculations needed for the 2D graphics library
///
////////////////////////////////////////////////////
public class compute
    {
    //
    // Class Variables
    //
    static private double height;
    static private double thickness;
    static private double innerRadius;
    static public double totalVolume;

    static private double outerRadius;         //Will be innerRadius + thickness
    
    static private int startingX;
    static private int startingY;
    static private int xStartIn;
    static private int yStartIn;
    
    static private double pixelConverison;
    static private int maxHeight;
    static private int maxWidth;
    
    static final double ANGLE_RATIO = 3.0;
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// Class constructor -- Initialize values to default cylinder
    ///
    /////////////////////////////////////////////////////////////////
    public compute(int xStart, int yStart, int maxHeightInPX, int maxWidthInPX)
        {
        maxHeight = maxHeightInPX;      //Indicates the maximum number of pixels for height
        maxWidth = maxWidthInPX;        //Indicates the maximum number of pixels for width

        //Set starting values
        xStartIn = xStart;
        yStartIn = yStart;

        //Set default values to draw a shape
        volume(2, 0.2, 0.3);
        }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// Will compute the volume of the shape specified by the parameters
    ///
    /////////////////////////////////////////////////////////////////
    static public double volume(double heightIn, double thicknessIn, double innerRadiusIn)
        {
        setValues(heightIn, thicknessIn, innerRadiusIn);
        
        //Compute total volume
        double volumeOfFullCylinder = (Math.PI * Math.pow(outerRadius, 2)) * height;
        
        //compute smaller volume
        double volumeOfInnerCylinder = (Math.PI * Math.pow(innerRadius, 2)) * height;
        
        //find difference to get desired volume
        totalVolume = volumeOfFullCylinder - volumeOfInnerCylinder;
        
        return totalVolume;
        }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// Will set the class variables based on the input values
    ///
    /////////////////////////////////////////////////////////////////
    static private void setValues(double setHeight, double setThickness, double setInnerRadius)
        {
        height = setHeight;
        thickness = setThickness;
        innerRadius = setInnerRadius;
        
        outerRadius = setThickness + setInnerRadius;
        
        double widthRatio = maxWidth / (2 * outerRadius);
        double heightRatio = maxHeight / height;
        
        //The smaller ratio will be the pixel conversion we use
        // also Set a new starting Y and X to center the object
        if(widthRatio < heightRatio)
            {
            pixelConverison = widthRatio;
            startingY = (int) (yStartIn + ((maxHeight - (height * pixelConverison)) / 2));   //Modify the Y starting location
            startingX = xStartIn;
            }
        else
            {
            pixelConverison = heightRatio;
            startingX = (int) (xStartIn + ((maxWidth - (2 * outerRadius * pixelConverison)) / 2));   //Modify the X starting location
            startingY = yStartIn;
            }
        
        }

    /////////////////////////////////////////////////////////////////
    ///
    /// Will compute and return the dimensions of the rectangle
    ///     to make the side of the cylinder
    ///
    /////////////////////////////////////////////////////////////////
    static public int[] sideOfCylinderDim()
        {
        int[] values = new int[4];
        
        values[0] = startingX;                                      //Top left x component of rectangle
        values[1] = startingY;                                      //Top left y component of rectangle
        values[2] = (int) (2 * outerRadius *  pixelConverison);     //Width of cylinder
        values[3] = (int) (height * pixelConverison);               //Height of cylinder
        
        return values;
        }

    /////////////////////////////////////////////////////////////////
    ///
    /// Will compute and return the dimensions of the large circle
    ///     to make the top of the cylinder
    ///
    /////////////////////////////////////////////////////////////////
    static public int[] largeCircleDim()
        {
        int[] values = new int [4];
    
        values[0] = startingX;                                                                          //Top left x component of rectangle
        values[1] = (int) (startingY - ((outerRadius / ANGLE_RATIO) * pixelConverison));      //Top left y component of rectangle
        values[2] = (int) (2 * outerRadius *  pixelConverison);                                         //Width of cylinder
        values[3] = (int) (2 * (outerRadius / ANGLE_RATIO) * pixelConverison);                            //Height of cylinder
    
        return values;
        }

    /////////////////////////////////////////////////////////////////
    ///
    /// Will compute and return the dimensions of the large circle
    ///     to make the top of the cylinder
    ///
    /////////////////////////////////////////////////////////////////
    static public int[] smallCircleDim()
        {
        int[] values = new int [4];
    
        values[0] = (int) (startingX + (thickness * pixelConverison));                                      //Top left x component of rectangle
        values[1] = (int) (startingY - ((innerRadius / ANGLE_RATIO) * pixelConverison));      //Top left y component of rectangle
        values[2] = (int) (2 * innerRadius * pixelConverison);                                             //Width of cylinder
        values[3] = (int) (2 * (innerRadius / ANGLE_RATIO) * pixelConverison);                    //Height of cylinder
    
        return values;
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Will compute and return the dimensions of the large circle
    ///     to make the top of the cylinder
    ///
    /////////////////////////////////////////////////////////////////
    static public int[] arcDim()
        {
        int[] values = new int [6];
    
        values[0] = startingX;                                                                                  //Top left x component of arc
        values[1] = (int) (startingY + ((height - (outerRadius / ANGLE_RATIO)) * pixelConverison));   //Top left y component of arc
        values[2] = (int) (2 * outerRadius *  pixelConverison);                                                 //Width of arc
        values[3] = (int) (2 * (outerRadius / ANGLE_RATIO) * pixelConverison);                        //Height of arc
        values[4] = 0;                                                                                          //Starting point of arc in deg
        values[5] = -180;                                                                                       //Ending point of arc in deg
        
        return values;
        }
    }


