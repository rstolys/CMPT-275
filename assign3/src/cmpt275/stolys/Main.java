package cmpt275.stolys;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;

public class Main
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
    //
    // All variables are private since there is no need for them
    // to be accessed outside the current class
    //
    private final int frameWidth;
    private final int frameHeight;
    
    private Shape myShape = null;       //Null until assigned
    private int numOfParameters;
    
    private boolean parameterSectionChanged = true;
    private boolean startUpPaintingNeeded = true;
    
    
    //Buttons
    private JButton selectShape;
    private JButton calculateButton;
    
    private int currentBtnSelection;
    
    
    //Labels
    private JLabel shapeSelectedTitle;
    private JLabel shapeSelectedTitle2;
    private JLabel param1;
    private JLabel param2;
    
    
    //Input Fields
    private JTextField param1Entry;
    private JTextField param2Entry;

    private JTextField circumferenceField;
    private JTextField areaField;
    
    
    //Shape types
    private final int TRIANGLE = 1;
    private final int RECTANGLE = 2;
    private final int ELLIPSE = 3;
    private final int SQUARE = 4;
    private final int CIRCLE = 5;

    private final String TRIANGLE_S = "triangle";
    private final String RECTANGLE_S = "rectangle";
    private final String ELLIPSE_S = "ellipse";
    private final String SQUARE_S = "square";
    private final String CIRCLE_S = "circle";

    private final String SHAPE_SELECTED = "shapeSelected";

    private final String CALCULATE = "calculate";
    
    
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
        
        this.setVisible(true);
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
        System.out.println("paintingNow");
        //Call base paint function
        super.paintComponent(g);

        Graphics2D graphic = (Graphics2D) g;
        
        if(startUpPaintingNeeded)
            {
            //Add the top part of the UI
            addGenericShapes(graphic, frameWidth, frameHeight / 2);

            //Modify the parameter entry section
            modifyParameterEntry(graphic, frameWidth, frameHeight / 2, frameHeight / 2);
            
            //Don't need to repaint
            startUpPaintingNeeded = false;
            }
        
        //continually redraw the bottom box
        int buffer = 10;
        
        graphic.setPaint(Color.GRAY);
        graphic.drawRect(frameWidth/8, (frameHeight / 2) + buffer, 6*frameWidth/8, (frameHeight / 2) - 2*buffer);
        }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// Will call function to handle calculate event
    ///
    /////////////////////////////////////////////////////////////////
    public void actionPerformed(ActionEvent e)
        {
        String actionCommand = e.getActionCommand();
        
        //Respond to action
        switch(actionCommand)
            {
            case SHAPE_SELECTED:
                if(currentBtnSelection > 0 && currentBtnSelection <= 5)         //It has a valid value
                    {
                    shapeOptionSelected(currentBtnSelection);                   //Call our function to modify our parameter for entry
                    }
                break;
                
            case CALCULATE:
                computeShapeParameters();
                break;
                
            case ELLIPSE_S:
                currentBtnSelection = ELLIPSE;
                break;

            case CIRCLE_S:
                currentBtnSelection = CIRCLE;
                break;

            case RECTANGLE_S:
                currentBtnSelection = RECTANGLE;
                break;

            case SQUARE_S:
                currentBtnSelection = SQUARE;
                break;

            case TRIANGLE_S:
                currentBtnSelection = TRIANGLE;
                break;
                
            default:
                //Do Nothing
                break;
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
            case ELLIPSE:
                myShape = new ellipse();
                myShape.setName("Ellipse");
                myShape.setName_p1("Major Radius (cm)");
                myShape.setName_p2("Minor Radius (cm)");
                numOfParameters = 2;
                break;
                
            case CIRCLE:
                myShape = new circle();
                myShape.setName("Circle");
                myShape.setName_p1("Radius (cm)");
                numOfParameters = 1;
                break;
                
            case RECTANGLE:
                myShape = new rectangle();
                myShape.setName("Rectangle");
                myShape.setName_p1("Width (cm)");
                myShape.setName_p2("Length (cm)");
                numOfParameters = 2;
                break;
                
            case SQUARE:
                myShape = new square();
                myShape.setName("Square");
                myShape.setName_p1("Side Length (cm)");
                numOfParameters = 1;
                break;
                
            case TRIANGLE:
                myShape = new triangle();
                myShape.setName("Triangle");
                myShape.setName_p1("Side Length (cm)");
                numOfParameters = 1;
                break;
                
            default:
                //Do nothing
                break;
            }
        
        //Update the parameters section
        modifyLabelsForParameterEntry();
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Will modify the labels to match the current shape selected
    ///
    /////////////////////////////////////////////////////////////////
    private void modifyLabelsForParameterEntry()
        {
        //Set the prompt labels
        shapeSelectedTitle.setText("You have selected: " + myShape.getName());
        shapeSelectedTitle2.setText("Enter the parameters for the " + myShape.getName());
        
        //Set parameter 1 label
        param1.setText(myShape.getName_p1());
        param1Entry.setText("");
        
        //Set the parameter 2 values depending on the number of params
        if(numOfParameters > 1)
            {
            param2.setText(myShape.getName_p2());
            param2.setVisible(true);
            param2Entry.setText("");
            param2Entry.setVisible(true);
            }
        else
            {
            //Hide the second parameter inputs
            param2.setVisible(false);
            param2Entry.setVisible(false);
            }
        }
    

    /////////////////////////////////////////////////////////////////
    ///
    /// Will compute the shape area and circumference and add them to the UI
    ///
    /////////////////////////////////////////////////////////////////
    private void computeShapeParameters()
        {
        if(myShape != null)
            {
            //Collect the input values to set for the shape
            double param1Result;
            double param2Result;
            try
                {
                param1Result = Double.parseDouble(param1Entry.getText());
    
                if(numOfParameters > 1)
                    {
                    param2Result = Double.parseDouble(param2Entry.getText());
                    myShape.setParam2(param2Result);
                    }
    
                //Set shape parameters
                myShape.setParam1(param1Result);
                }
            catch (NumberFormatException e)
                {
                myShape.setParam1(0.0);
                myShape.setParam2(0.0);
                }


            //Set the circumference and area values
            DecimalFormat resultFormat = new DecimalFormat("#.00");
            double circumference = Double.parseDouble(resultFormat.format(myShape.calculateCircumference()));
            double area = Double.parseDouble(resultFormat.format(myShape.calculateArea()));

            if(circumference == 0.0 || area == 0.0)
                {
                if(numOfParameters > 1)
                    {
                    circumferenceField.setText("Parameters Invalid");
                    areaField.setText("Parameters Invalid");
                    }
                else
                    {
                    circumferenceField.setText("Parameter Invalid");
                    areaField.setText("Parameter Invalid");
                    }
                }
            else
                {
                circumferenceField.setText(String.valueOf(circumference));
                areaField.setText(String.valueOf(area));
                }
            }
        //else -- do no calculations
        }
    
    
    /////////////////////////////////////////////////////////////////
    ///
    /// Will add the generic shapes section of the GUI
    ///
    /////////////////////////////////////////////////////////////////
    private void addGenericShapes(Graphics2D graphic, int width, int height)
        {
        //StartingY will be 0 for this function -- don't need to accommodate for it
        int buffer = 10;
        int labelHeight = (int) Math.floor(1.5*height / 10);
        int yValOfRadioButtons = (int) Math.floor(6.5*height / 10) + 1;
        int radioButtonSize = 30;
        int yValOfShapes = (int) Math.floor((1.5)*height / 10) + buffer;
        int heightOfShapes = (5*height / 10) - buffer;
        int widthOfShapes = (int) ((width / 5) - 1.2*buffer);
        int symmetricalShapeDiff;
        
        int selectShapeButtonWidth = width/5;
        int selectShapeButtonHeight = (int) Math.floor(1.5*height / 10);
        int yValOfSelectButton = 8*height/10;
        int xValOfSelectButton = (width/2) - (selectShapeButtonWidth/2);
        
        
        
        if(heightOfShapes > widthOfShapes)
            symmetricalShapeDiff = (heightOfShapes - widthOfShapes) / 2;
        else
            symmetricalShapeDiff = (widthOfShapes - heightOfShapes) / 2;
        
        //Create shape prompt label -- will take up 1/5 of top section
        JLabel genericShapePrompt = new JLabel("Please Select a Shape");
        genericShapePrompt.setBounds(buffer, buffer, width, labelHeight);
        this.add(genericShapePrompt);

        //Draw ellipse
        graphic.setPaint(Color.BLUE);
        //graphic.draw(new Ellipse2D.Double(buffer, yValOfShapes, widthOfShapes, heightOfShapes));
        graphic.fill(new Ellipse2D.Double(buffer, yValOfShapes, widthOfShapes, heightOfShapes));

        //Draw Circle
        graphic.setPaint(Color.GREEN);
        //graphic.draw(new Ellipse2D.Double(buffer, yValOfShapes, widthOfShapes, widthOfShapes));
        graphic.fill(new Ellipse2D.Double(2*buffer + widthOfShapes, yValOfShapes + symmetricalShapeDiff, widthOfShapes, widthOfShapes));


        //Draw Rectangle
        graphic.setPaint(Color.GRAY);
        graphic.drawRect(3*buffer + 2*widthOfShapes, yValOfShapes, widthOfShapes, heightOfShapes);
        graphic.setPaint(Color.ORANGE);
        graphic.fillRect(3*buffer + 2*widthOfShapes, yValOfShapes, widthOfShapes, heightOfShapes);

        //Draw Square
        graphic.setPaint(Color.CYAN);
        graphic.fillRect(4*buffer + 3*widthOfShapes, yValOfShapes + symmetricalShapeDiff, widthOfShapes, widthOfShapes);

        //Draw Triangle
        graphic.setPaint(Color.BLACK);
        int[] x = {5*buffer + (4*widthOfShapes), (int) ((5*buffer) + (4.5*widthOfShapes)), 5*(buffer + widthOfShapes)};
        int[] y = {yValOfShapes + symmetricalShapeDiff + widthOfShapes, yValOfShapes + symmetricalShapeDiff, yValOfShapes + symmetricalShapeDiff + widthOfShapes};
        graphic.fillPolygon(x, y,3);

        //Select Shape Button
        selectShape = new JButton("Select Shape");
        selectShape.setActionCommand(SHAPE_SELECTED);
        selectShape.setBounds(xValOfSelectButton, yValOfSelectButton, selectShapeButtonWidth, selectShapeButtonHeight);
        this.add(selectShape);
        selectShape.addActionListener(this);
        
        
        //
        //Add buttons
        //
        JRadioButton ellipseButton;
        JRadioButton circleButton;
        JRadioButton rectangleButton;
        JRadioButton squareButton;
        JRadioButton triangleButton;

        ButtonGroup radioBtnGroup;
        
        //Ellipse Button
        ellipseButton = new JRadioButton();
        ellipseButton.setActionCommand(ELLIPSE_S);
        ellipseButton.setBounds(widthOfShapes/ 2, yValOfRadioButtons, radioButtonSize, radioButtonSize);
        this.add(ellipseButton);
        ellipseButton.addActionListener(this);

        //Circle Button
        circleButton = new JRadioButton();
        circleButton.setActionCommand(CIRCLE_S);
        circleButton.setBounds(3*widthOfShapes/ 2, yValOfRadioButtons, radioButtonSize, radioButtonSize);
        this.add(circleButton);
        circleButton.addActionListener(this);

        //Rectangle Button
        rectangleButton = new JRadioButton();
        rectangleButton.setActionCommand(RECTANGLE_S);
        rectangleButton.setBounds((5*widthOfShapes/ 2) + buffer, yValOfRadioButtons, radioButtonSize, radioButtonSize);
        this.add(rectangleButton);
        rectangleButton.addActionListener(this);

        //Square Button
        squareButton = new JRadioButton();
        squareButton.setActionCommand(SQUARE_S);
        squareButton.setBounds((7*widthOfShapes/ 2) + 2*buffer, yValOfRadioButtons, radioButtonSize, radioButtonSize);
        this.add(squareButton);
        squareButton.addActionListener(this);

        //Triangle Button
        triangleButton = new JRadioButton();
        triangleButton.setActionCommand(TRIANGLE_S);
        triangleButton.setBounds((9*widthOfShapes/ 2) + 3*buffer, yValOfRadioButtons, radioButtonSize, radioButtonSize);
        this.add(triangleButton);
        triangleButton.addActionListener(this);
        
        //Add all the buttons to a button group
        radioBtnGroup = new ButtonGroup();
        radioBtnGroup.add(ellipseButton);
        radioBtnGroup.add(circleButton);
        radioBtnGroup.add(rectangleButton);
        radioBtnGroup.add(squareButton);
        radioBtnGroup.add(triangleButton);
        }

    /////////////////////////////////////////////////////////////////
    ///
    /// Will add the generic shapes section of the GUI
    ///
    /////////////////////////////////////////////////////////////////
    private void modifyParameterEntry(Graphics2D graphic, int width, int height, int startingY)
        {
        int buffer = 10;
        int xPosOfParameterBox = width/8;
        int yPosOfParameterBox = startingY + buffer;
        int widthOfParameterBox = 6*width/8;
        int heightOfParameterBox = height - 2*buffer;
        
        int heightOfTitleLabels = heightOfParameterBox/10;
        
        int yValOfParameterLine = yPosOfParameterBox + 2*heightOfTitleLabels;
        int heightOfParameterEntry = (heightOfParameterBox/5) - buffer;
        int widthOfParameterLabel_Entry = (widthOfParameterBox / 2) - buffer;
        
        int yValOfCalculateButton = yValOfParameterLine + 2*heightOfParameterEntry + buffer;
        int xValOfCalculateButton = (widthOfParameterBox / 4) + xPosOfParameterBox;
        int widthOfButton = (widthOfParameterBox / 2);
        int heightOfButton = (heightOfParameterBox/5) - buffer;
        
        int xValOfCircumference = xPosOfParameterBox + buffer;
        int yValOfCalculate = yValOfCalculateButton + heightOfButton + buffer;
        int widthOfCalculated = (widthOfParameterBox / 2) - 2*buffer;
        int heightOfCalculatedLabel = (heightOfParameterBox / 20);
        int heightOfCalculatedValue = (3*widthOfParameterBox / 20) - 2*buffer;
        
        int xValOfArea = xValOfCircumference + widthOfCalculated + buffer;
        
        
        //Add instruction labels to the parameter box
        shapeSelectedTitle = new JLabel("You have selected: ");
        shapeSelectedTitle.setBounds(xPosOfParameterBox + buffer, yPosOfParameterBox + buffer, widthOfParameterBox - 2*buffer, heightOfTitleLabels);
        this.add(shapeSelectedTitle);

        shapeSelectedTitle2 = new JLabel("Enter the parameters for the ");
        shapeSelectedTitle2.setBounds(xPosOfParameterBox + buffer, yPosOfParameterBox + heightOfTitleLabels + buffer, widthOfParameterBox - buffer, heightOfTitleLabels);
        this.add(shapeSelectedTitle2);
        
        //Add parameter entry lines
        param1 = new JLabel("Parameter 1");
        param1.setBounds(xPosOfParameterBox + buffer, yValOfParameterLine + buffer, widthOfParameterLabel_Entry, heightOfParameterEntry);
        this.add(param1);
        
        param1Entry = new JTextField();
        param1Entry.setBounds(xPosOfParameterBox + widthOfParameterLabel_Entry + 2*buffer, yValOfParameterLine + 2*buffer, widthOfParameterLabel_Entry - buffer, heightOfParameterEntry - buffer);
        this.add(param1Entry);
        
        param2 = new JLabel("Parameter 2");
        param2.setBounds(xPosOfParameterBox + buffer, yValOfParameterLine + heightOfParameterEntry + buffer, widthOfParameterLabel_Entry, heightOfParameterEntry);
        this.add(param2);

        param2Entry = new JTextField();
        param2Entry.setBounds(xPosOfParameterBox + widthOfParameterLabel_Entry + 2*buffer, yValOfParameterLine + heightOfParameterEntry + buffer, widthOfParameterLabel_Entry - buffer, heightOfParameterEntry - buffer);
        this.add(param2Entry);
        
        //Add Calculate Button
        calculateButton = new JButton("Calculate");
        calculateButton.setActionCommand(CALCULATE);
        calculateButton.setBounds(xValOfCalculateButton, yValOfCalculateButton, widthOfButton, heightOfButton);
        this.add(calculateButton);
        calculateButton.addActionListener(this);
        
        
        //Add the area and circumference entry boxes
        JLabel circumferenceLabel = new JLabel("Circumference (cm)");
        circumferenceLabel.setBounds(xValOfCircumference, yValOfCalculate, widthOfCalculated, heightOfCalculatedLabel);
        this.add(circumferenceLabel);

        circumferenceField = new JTextField();
        circumferenceField.setDisabledTextColor(Color.BLUE);
        circumferenceField.setEditable(false);
        circumferenceField.setBounds(xValOfCircumference, yValOfCalculate + heightOfCalculatedLabel, widthOfCalculated, heightOfCalculatedValue);
        this.add(circumferenceField);

        
        JLabel areaLabel = new JLabel("Area (cm^2)");
        areaLabel.setBounds(xValOfArea, yValOfCalculate, widthOfCalculated, heightOfCalculatedLabel);
        this.add(areaLabel);

        areaField = new JTextField();
        areaField.setDisabledTextColor(Color.BLUE);
        areaField.setEditable(false);
        areaField.setBounds(xValOfArea, yValOfCalculate + heightOfCalculatedLabel, widthOfCalculated, heightOfCalculatedValue);
        this.add(areaField);
        }
    
    }

