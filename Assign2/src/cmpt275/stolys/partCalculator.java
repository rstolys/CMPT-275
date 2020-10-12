package cmpt275.stolys;

import cmpt275.stolys.compute;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.text.DecimalFormat;

public class partCalculator
    {

    public static void main(String[] args)
        {
        SwingUtilities.invokeLater(new Runnable()
            {
            @Override
            public void run()
                {
                createPartCalculatorUI();
                }
            });
        }

    private static void createPartCalculatorUI()
        {
        JFrame window = new JFrame("Part Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600,600);
        window.add(new cylinder());
        window.pack();
        window.setVisible(true);
        }
    
    }

class cylinder extends JPanel implements ActionListener
    {
    private compute cylinder;                       //Compute class global class instance

    private double height;
    private double thickness;
    private double holeRadius;

    private JFrame window;                          //For gui

    private JButton calculateButton;                //For gui

    static private JLabel heightErrorLabel;         //For gui
    static private JLabel thicknessErrorLabel;      //For gui
    static private JLabel holeRadiusErrorLabel;     //For gui

    private JTextField heightInput;                 //For gui
    private JTextField thicknessInput;              //For gui
    private JTextField holeRadiusInput;             //For gui
    private JTextField needLiquidValue;             //For gui
    
    private boolean errorInInputs;

    /////////////////////////////////////////////////////////////////
    ///
    /// Constructor for the cylinder class
    ///
    /////////////////////////////////////////////////////////////////
    public cylinder()
        {
        //Create the compute class instance
        cylinder = new compute(420, 50,500, 160);
        
        //Set no layout in JPanel
        this.setLayout(null);
        
        // Create Section on left side
        addLeftSection();
        
        // Create middle section
        addMiddleSection();
        }

    
    /////////////////////////////////////////////////////////////////
    ///
    /// Will call function to handle calculate event
    ///
    /////////////////////////////////////////////////////////////////
    public void actionPerformed(ActionEvent e)
        {
        handleCalculateEvent();
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Will collect errors  and manage errors
    ///
    /////////////////////////////////////////////////////////////////
    public void handleCalculateEvent()
        {
        String[] errorMessages = new String [3];
        
        clearInputFields();


        //Get inputs from GUI and check they are valid
        String heightValue = heightInput.getText();
        String thicknessValue = thicknessInput.getText();
        String holeRadiusValue = holeRadiusInput.getText();


        //Reset error messages
        errorMessages[0] = checkHeight(heightValue);

        errorMessages[1] = checkThickness(thicknessValue);

        errorMessages[2] = checkHoleRadius(holeRadiusValue);


        if(errorInInputs)
            {
            //Show the user the error messages and ask them to try again
            addErrorMessages(errorMessages);
    
            //Show there are no errors and let the user try again
            errorInInputs = false;
            }
        else
            {
            //The inputs are valid and we can fill our total volume
            double volumeOfCylinder = compute.volume(height, thickness, holeRadius);

            //Set output with a maximum of 6 decimals
            DecimalFormat numberFormat = new DecimalFormat("0.000000");
            
            //Fill the volume in the volume label
            needLiquidValue.setText(numberFormat.format(volumeOfCylinder));

            //Show that we have changed some values
            this.revalidate();
            this.repaint();
            }
        

        return;
        }

    /////////////////////////////////////////////////////////////////
    //
    // Add error messages at bottom of screen for user
    //
    /////////////////////////////////////////////////////////////////
    private void addErrorMessages(String[] errorMessages)
        {
        //Fill in error messages
        heightErrorLabel.setText(errorMessages[0]);
        thicknessErrorLabel.setText(errorMessages[1]);
        holeRadiusErrorLabel.setText(errorMessages[2]);
        
        return;
        }
    
    private void clearInputFields()
        {
        //Fill in error messages
        heightErrorLabel.setText("");
        thicknessErrorLabel.setText("");
        holeRadiusErrorLabel.setText("");

        return;
        }

    /////////////////////////////////////////////////////////////////
    //
    // Check that the height is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkHeight(String valueIn)
        {
        String returnMessage = "";
    
        if(valueIn.isEmpty())
            {
            height = -1;
            errorInInputs = true;
            returnMessage = "Height input is empty";
            }
        else
            {
            double value = Double.parseDouble(valueIn);
        
            if(value > 0)
                {
                height = value;
                }
            else
                {
                height = -1;
                errorInInputs = true;
                returnMessage = "Height must be above 0m";
                }
            }
    
        return returnMessage;
        }

    /////////////////////////////////////////////////////////////////
    //
    // Check that the thickness is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkThickness(String valueIn)
        {
        String returnMessage = "";
    
        if(valueIn.isEmpty())
            {
            thickness = -1;
            errorInInputs = true;
            returnMessage = "Thickness input is empty";
            }
        else
            {
            double value = Double.parseDouble(valueIn);
        
            if(value >= 0)
                {
                thickness = value;
                }
            else
                {
                height = -1;
                errorInInputs = true;
                returnMessage = "Thickness must be greater than 0m";
                }
            }
    
        return returnMessage;
        }

    /////////////////////////////////////////////////////////////////
    //
    // Check that the hole radius is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkHoleRadius(String valueIn)
        {
        String returnMessage = "";
    
        if (valueIn.isEmpty())
            {
            holeRadius = -1;
            errorInInputs = true;
            returnMessage = "Hole Radius input is empty";
            }
        else
            {
            double value = Double.parseDouble(valueIn);
        
            if (value >= 0)
                {
                holeRadius = value;
                }
            else
                {
                height = -1;
                errorInInputs = true;
                returnMessage = "Hole Radius must be greater than 0m";
                }
            }
    
        return returnMessage;
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Gets the preferred size of the JFrame
    ///
    /////////////////////////////////////////////////////////////////
    public Dimension getPreferredSize()
        {
        return new Dimension(600,600);
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
        
        
        //Draw side of cylinder
        int[] rectValues = compute.sideOfCylinderDim();
        //     0: x value
        //     1: y value
        //     2: width
        //     3: height
        graphic.setPaint(Color.BLUE);
        graphic.fillRect(rectValues[0], rectValues[1], rectValues[2], rectValues[3]);
        
        //Draw border lines
        graphic.setPaint(Color.BLACK);
        graphic.drawLine(rectValues[0], rectValues[1], rectValues[0], rectValues[1] + rectValues[3]);
        graphic.drawLine(rectValues[0] + rectValues[2], rectValues[1], rectValues[0] + rectValues[2], rectValues[1] + rectValues[3]);
        
        
        //Draw larger circle
        int[] largeCircle = compute.largeCircleDim();
        //     0: x value
        //     1: y value
        //     2: width
        //     3: height
        graphic.setPaint(Color.BLUE);
        graphic.fill(new Ellipse2D.Double(largeCircle[0], largeCircle[1], largeCircle[2], largeCircle[3]));

        graphic.setPaint(Color.BLACK);
        graphic.draw(new Ellipse2D.Double(largeCircle[0], largeCircle[1], largeCircle[2], largeCircle[3]));
        
        //Draw smaller circle
        int[] smallCircle = compute.smallCircleDim();
        //     0: x value
        //     1: y value
        //     2: width
        //     3: height
        graphic.setPaint(Color.WHITE);
        graphic.fill(new Ellipse2D.Double(smallCircle[0], smallCircle[1], smallCircle[2], smallCircle[3]));
        //Draw Border
        graphic.setPaint(Color.BLACK);
        graphic.draw(new Ellipse2D.Double(smallCircle[0], smallCircle[1], smallCircle[2], smallCircle[3]));
        
        //Draw arc for bottom of circle
        int[] arcValues = compute.arcDim();
        //     0: x value
        //     1: y value
        //     2: width
        //     3: height
        //     4: starting degree
        //     5: ending degree
        graphic.setPaint(Color.BLUE);
        graphic.fillArc(arcValues[0], arcValues[1], arcValues[2], arcValues[3], arcValues[4], arcValues[5]);
        
        //Draw Border
        graphic.setPaint(Color.BLACK);
        graphic.drawArc(arcValues[0], arcValues[1], arcValues[2], arcValues[3], arcValues[4], arcValues[5]);
        
        
        return;
        }

    /////////////////////////////////////////////////////////////////
    ///
    /// Add labels and compute button to window
    ///
    /////////////////////////////////////////////////////////////////
    private void addLeftSection()
        {
        //Create height label and error label
        JLabel heightLabel = new JLabel("Enter Height (m):");
        heightLabel.setBounds(10, 100, 180, 50);
    
        heightErrorLabel = new JLabel("");
        heightErrorLabel.setBounds(10, 150, 180, 25);
        heightErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
        //Create thickness label and error label
        JLabel thicknessLabel = new JLabel("Enter Thickness (m):");
        thicknessLabel.setBounds(10, 200, 180, 50);
    
        thicknessErrorLabel = new JLabel("");
        thicknessErrorLabel.setBounds(10, 250, 180, 25);
        thicknessErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
        //Create hole radius label and error label
        JLabel holeRadiusLabel = new JLabel("Enter hole radius (m):");
        holeRadiusLabel.setBounds(10, 300, 180, 50);
    
        holeRadiusErrorLabel = new JLabel("");
        holeRadiusErrorLabel.setBounds(10, 350, 180, 25);
        holeRadiusErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
    
        //Add compute button
        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(20, 450, 160, 50);
        calculateButton.addActionListener(this);
    
    
        //Add elements to window
        this.add(heightLabel);
        this.add(heightErrorLabel);
    
        this.add(thicknessLabel);
        this.add(thicknessErrorLabel);
    
        this.add(holeRadiusLabel);
        this.add(holeRadiusErrorLabel);
    
        this.add(calculateButton);
        }


    /////////////////////////////////////////////////////////////////
    ///
    /// Add input fields and volume total to window
    ///
    /////////////////////////////////////////////////////////////////
    private void addMiddleSection()
        {
        //Add height Input
        heightInput = new JTextField();
        heightInput.setBounds(210, 100, 180, 50);
    
        //Add thickness Input
        thicknessInput = new JTextField();
        thicknessInput.setBounds(210, 200, 180, 50);
    
        //Add hole radius Input
        holeRadiusInput = new JTextField();
        holeRadiusInput.setBounds(210, 300, 180, 50);
    
    
        //Add needed liquid label
        JLabel neededLiquidLabel = new JLabel("Needed Liquid (m^3):");
        neededLiquidLabel.setBounds(220, 400, 160, 50);
    
        //Add needed liquid value Input
        needLiquidValue = new JTextField();
        needLiquidValue.setBounds(210, 450, 180, 50);
        needLiquidValue.setEnabled(false);
    
        //Add elements to window
        this.add(heightInput);
        this.add(thicknessInput);
        this.add(holeRadiusInput);
    
        this.add(neededLiquidLabel);
        this.add(needLiquidValue);
        }
    }
