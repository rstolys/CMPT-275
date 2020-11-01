package cmpt275.stolys;

import java.lang.Math;

public class triangle implements Shape
    {
    //
    // Class Variables
    //
    //
    // All variables are private since all variables are accessed
    // only through getters and setters.
    //
    private String name;
    
    private String name_p1;
    private double param1;
    
    
    //
    // Function Definitions
    //

    /////////////////////////////////////////////////////////////////
    ///
    /// triangle class constructor
    ///
    /////////////////////////////////////////////////////////////////
    public triangle()
        {
        // Set default values
        name = "";
        name_p1 = "";
        param1 = 0.0;
        }
   
    /////////////////////////////////////////////////////////////////
    ///
    /// setter for name of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public void setName(String n) { name = n; }


    /////////////////////////////////////////////////////////////////
    ///
    /// getter for name of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public String getName() { return name; }


    /////////////////////////////////////////////////////////////////
    ///
    /// setter for first parameter of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public void setParam1(double p1)
        {
        if(p1 >= 0.0)        //Only change if it is a valid number
            {
            param1 = p1;
            }
        else
            {
            param1 = 0.0;
            }
        }
    
    /////////////////////////////////////////////////////////////////
    ///
    /// setter for second parameter of the shape NOT applicable
    ///
    /////////////////////////////////////////////////////////////////
    public void setParam2(double p2) { return; }


    /////////////////////////////////////////////////////////////////
    ///
    /// getter for first parameter of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public double getParam1() { return param1; }


    /////////////////////////////////////////////////////////////////
    ///
    /// getter for second parameter of the shape NOT applicable
    ///
    /////////////////////////////////////////////////////////////////
    public double getParam2() { return 0.0; }


    /////////////////////////////////////////////////////////////////
    ///
    /// setter for the name of the first parameter
    ///
    /////////////////////////////////////////////////////////////////
    public void setName_p1(String n1) { name_p1 = n1; }


    /////////////////////////////////////////////////////////////////
    ///
    /// setter for the name of the second parameter NOT applicable
    ///
    /////////////////////////////////////////////////////////////////
    public void setName_p2(String n2) { return; }


    /////////////////////////////////////////////////////////////////
    ///
    /// getter for the name of the first parameter
    ///
    /////////////////////////////////////////////////////////////////
    public String getName_p1() { return name_p1; }


    /////////////////////////////////////////////////////////////////
    ///
    /// getter for the name of the second parameter NOT applicable
    ///
    /////////////////////////////////////////////////////////////////
    public String getName_p2() { return ""; }


    /////////////////////////////////////////////////////////////////
    ///
    /// calculate the circumference of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public double calculateCircumference()
        {
        return (param1 * 3);
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// calculate the area of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public double calculateArea()
        {
        return (Math.sqrt(3) / 4) * Math.pow(param1, 2);
        }
    
    }
