package cmpt275.stolys;

public class ellipse implements Shape
    {
    //
    // Class Variables
    //
    private String name;
    
    private String name_p1;
    private double param1;
    
    private String name_p2;
    private double param2;
    
    
    //
    // Function Definitions
    //

    /////////////////////////////////////////////////////////////////
    ///
    /// Constructor
    ///
    /////////////////////////////////////////////////////////////////
    public ellipse()
        {
        //Set default values for parameters
        name = "";
        name_p1 = "";
        param1 = 0.0;
        name_p2 = "";
        param2 = 0.0;
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
        if(p1 >= 0.0)
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
    /// setter for second parameter of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public void setParam2(double p2)
        {
        if(p2 >= 0.0)
            {
            param2 = p2;
            }
        else
            {
            param2 = 0.0;
            }
        }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// getter for first parameter of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public double getParam1() { return param1; }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// getter for second parameter of the shape (if applicable)
    ///
    /////////////////////////////////////////////////////////////////
    public double getParam2() { return param2; }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// setter for the name of the first parameter
    ///
    /////////////////////////////////////////////////////////////////
    public void setName_p1(String n1) { name_p1 = n1; }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// setter for the name of the second parameter (if applicable)
    ///
    /////////////////////////////////////////////////////////////////
    public void setName_p2(String n2) { name_p2 = n2; }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// getter for the name of the first parameter
    ///
    /////////////////////////////////////////////////////////////////
    public String getName_p1() { return name_p1; }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// getter for the name of the second parameter (if applicable)
    ///
    /////////////////////////////////////////////////////////////////
    public String getName_p2() { return name_p2; }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// calculate the circumference of the shape
    ///
    /// using the Ramanujan approximation -- found from:
    /// https://www.mathsisfun.com/geometry/ellipse-perimeter.html
    ///
    /////////////////////////////////////////////////////////////////
    public double calculateCircumference()
        {
        double ele1 = 3 * (param1 + param2);
        double ele2 = Math.sqrt((3*param1 + param2) * (param1 + 3*param2));
        return (Math.PI * (ele1 - ele2));
        }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// calculate the area of the shape
    ///
    /////////////////////////////////////////////////////////////////
    public double calculateArea()
        {
        return Math.PI * param1 * param2;
        }
    
    }


