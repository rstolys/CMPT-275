
public interface Shape
    {
    void setName(String n); // setter for name of the shape
    String getName(); // getter for name of the shape
    void setParam1(double p1); // setter for first parameter of the shape
    void setParam2(double p2); // setter for second parameter of the shape (if applicable)
    double getParam1(); // getter for first parameter of the shape
    double getParam2(); // getter for second parameter of the shape (if applicable)
    void setName_p1(String n1);// setter for the name of the first parameter
    void setName_p2(String n2);// setter for the name of the second parameter (if applicable)
    String getName_p1(); // getter for the name of the first parameter
    String getName_p2(); // getter for the name of the second parameter (if applicable)
    double calculateCircumference();
    double calculateArea();
    }
