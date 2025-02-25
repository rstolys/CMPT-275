package cmpt275.stolys.model;

import javax.swing.*;
import java.io.File;
import java.lang.Math;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class computeCharge implements ActionListener
    {
    //
    // Class Variables
    //
    private JFrame window;                      //For gui
    private JButton submitButton;               //For gui
    
    static private JLabel voltageErrorLabel;        //For gui
    static private JLabel capacitorErrorLabel;      //For gui
    static private JLabel inductorErrorLabel;       //For gui
    static private JLabel resistorErrorLabel;       //For gui
    static private JLabel endTimeErrorLabel;        //For gui
    static private JLabel timeStepErrorLabel;       //For gui
    static private JLabel fileNameErrorLabel;       //For gui
    static private JLabel fileDirErrorLabel;       //For gui
    
    private JTextField voltageInput;
    private JTextField capacitorInput;
    private JTextField inductorInput;
    private JTextField resistanceInput;
    private JTextField endTimeInput;
    private JTextField timeStepInput;
    private JTextField fileNameInput;
    private JTextField fileDirInput;

    static private double voltage;
    static private double capacitance;
    static private double inductance;
    static private double resistance;

    static private double endTime;
    static private double timeSteps;
    
    static private String fileName;
    static private String fileDirectory;

    public boolean errorInInputs;

    public double[] dataValues;


    /////////////////////////////////////////////////////////////////
    //
    // Class constructor
    //
    /////////////////////////////////////////////////////////////////
    public computeCharge()
        {
        //Create the gui
        buildGUI();
        
        //
        //Modify the file
        }

    
    /////////////////////////////////////////////////////////////////
    //
    // Will create the gui for the user to interact with
    //
    /////////////////////////////////////////////////////////////////
    private void buildGUI()
        {
        //
        //Set the input values to default error values
        //
        //Initialize class variables, set them all to -1
        voltage = -1;
        capacitance = -1;
        inductance = -1;
        resistance = -1;
        endTime = -1;
        timeSteps = -1;
    
    
        //
        //Build GUI here
        //
    
        //Create frame of window
        window = new JFrame("Enter Values");
        window.setSize(500, 550);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
    
    
        //Add labels to window
        addLabels();
    
        //Add input fields to window
        addInputFields();
    
    
        //Add Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 450, 100, 40);
        submitButton.addActionListener(this);
        window.add(submitButton);

        //Open window to begin process
        window.setVisible(true);
    
        return;
        }
    

    /////////////////////////////////////////////////////////////////
    //
    // Will save the values when the button is clicked
    //
    /////////////////////////////////////////////////////////////////
    public void actionPerformed(ActionEvent e)
        {
        handleComputations();
        }


    /////////////////////////////////////////////////////////////////
    //
    // Collect user input values and check they are valid
    //
    /////////////////////////////////////////////////////////////////
    private void handleComputations()
        {
        boolean inputsAreValid = false;
        String[] errorMessages = new String [8];
        
        
        //Get inputs from GUI and check they are valid
        String voltageValue = voltageInput.getText();
        String capacitorValue = capacitorInput.getText();
        String inductorValue = inductorInput.getText();
        String resistorValue = resistanceInput.getText();
        String endTimeValue = endTimeInput.getText();
        String timeStepValue = timeStepInput.getText();
        String fileNameValue = fileNameInput.getText();
        String fileDirValue = fileDirInput.getText();
        
    
        //Reset error messages
        errorMessages[0] = checkVoltage(voltageValue);
    
        errorMessages[1] = checkCapacitance(capacitorValue);
    
        errorMessages[2] = checkInductance(inductorValue);
    
        errorMessages[3] = checkResistance(resistorValue);
    
        errorMessages[4] = checkEndTime(endTimeValue);
    
        errorMessages[5] = checkTimeSteps(timeStepValue);

        errorMessages[6] = checkFileName(fileNameValue);

        errorMessages[7] = checkFileDirectory(fileDirValue);
    
        if(errorInInputs)
            {
            //Show the user the error messages and ask them to try again
            addErrorMessages(errorMessages);
        
            //Show there are no errors and let the user try again
            errorInInputs = false;
            }
        else
            {
            //The data values are valid and we can define the size of our data values array
            dataValues = new double[(int) Math.floor(endTime / timeSteps) + 1];
        
            //exit loop and return from function
            inputsAreValid = true;
            }
        
        if(inputsAreValid)
            {
            //hide the window
            window.setVisible(false);
            
            //Perform Calculations and print to output file
            computeChargeOfCapacitor();

            //Print results on a graph
            //**Here**
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
        voltageErrorLabel.setText(errorMessages[0]);
        capacitorErrorLabel.setText(errorMessages[1]);
        inductorErrorLabel.setText(errorMessages[2]);
        resistorErrorLabel.setText(errorMessages[3]);
        endTimeErrorLabel.setText(errorMessages[4]);
        timeStepErrorLabel.setText(errorMessages[5]);
        fileNameErrorLabel.setText(errorMessages[6]);
        fileDirErrorLabel.setText(errorMessages[7]);
        }

    
    /////////////////////////////////////////////////////////////////
    //
    // Compute the discrete function values
    //
    /////////////////////////////////////////////////////////////////
    private void computeChargeOfCapacitor()
        {
        double t = 0.0;
        int index = 0;
        for ( ; t <= endTime; t += timeSteps, index++)
            {
            double timeConstant = resistance / (2 * inductance);
            
            //Compute the coefficient and cosine argument
            double coefficient = voltage * capacitance * Math.exp(-t * timeConstant);
            double cosineArg = t * Math.sqrt((1 / (inductance * capacitance)) - Math.pow(timeConstant, 2));
        
            //Compute the data value and add it to the array
            dataValues[index] = coefficient * Math.cos(cosineArg);
            }
    
        //Output the values to a .txt file
        sendOutputToFile();
        
        //Create plot
        graphResults graph = new graphResults();
        JFreeChart chart = graph.createChart(dataValues, timeSteps, endTime);
        ChartPanel cp = new ChartPanel(chart);
        
        addChartToWindow(cp);
        
    
        return;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Will create a new window with the chart inside
    //
    /////////////////////////////////////////////////////////////////
    private void addChartToWindow(ChartPanel cp)
        {
        //Create new window
        window = new JFrame("Charts");
        window.setSize(500, 450);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        //Add the graph
        window.getContentPane().add(cp);
        }

    
    /////////////////////////////////////////////////////////////////
    //
    // Print output values to a file
    //
    /////////////////////////////////////////////////////////////////
    private void sendOutputToFile()
        {
        //Use Java swing to collect location and name of file
    
        //Try to open, write and close file
        try
            {
            FileWriter outputFile = new FileWriter(new File(fileDirectory, fileName));
            outputFile.write("t, q(t)\n");
        
            //Write all values to file
            for (int i = 0; i < dataValues.length; i++)
                {
                //                          t           ,        q(t)
                outputFile.write((timeSteps * i) + ", " + dataValues[i] + "\n");
                }
        
            //Close file
            outputFile.close();
            }
        catch (IOException error)
            {
            System.out.println("An error occurred.");
            error.printStackTrace();
            }
    
        return;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Check that the voltage is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkVoltage(String voltageIn)
        {
        String returnMessage = "";
        
        if(voltageIn.isEmpty())
            {
            voltage = -1;
            errorInInputs = true;
            returnMessage = "Voltage input is empty";
            }
        else
            {
            double value = Double.parseDouble(voltageIn);
            
            if(value >= 4 && value <= 15)
                {
                voltage = value;
                }
            else
                {
                voltage = -1;
                errorInInputs = true;
                returnMessage = "Voltage is outside of valid range";
                }
            }
    
        return returnMessage;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Check that the capacitance is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkCapacitance(String capacitanceIn)
        {
        String returnMessage = "";

        if(capacitanceIn.isEmpty())
            {
            voltage = -1;
            errorInInputs = true;
            returnMessage = "Capacitance input is empty";
            }
        else
            {
            double value = Double.parseDouble(capacitanceIn);
    
            if(value >= 1 && value <= 100)
                {
                capacitance = value * Math.pow(10, -9);
                }
            else
                {
                capacitance = -1;
                errorInInputs = true;
                returnMessage = "Capacitance is outside of valid range";
                }
            }
    
        return returnMessage;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Check that the inductance is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkInductance(String inductanceIn)
        {
        String returnMessage = "";

        if(inductanceIn.isEmpty())
            {
            voltage = -1;
            errorInInputs = true;
            returnMessage = "Inductance input is empty";
            }
        else
            {
            double value = Double.parseDouble(inductanceIn);
    
            if(value >= 1 && value <= 100)
                {
                inductance = value * Math.pow(10, -3);
                }
            else
                {
                inductance = -1;
                errorInInputs = true;
                returnMessage = "Inductance is outside of valid range";
                }
            }
        
        return returnMessage;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Check that the resistance is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkResistance(String resistanceIn)
        {
        String returnMessage = "";

        if(resistanceIn.isEmpty())
            {
            voltage = -1;
            errorInInputs = true;
            returnMessage = "Resistance input is empty";
            }
        else
            {
            double value = Double.parseDouble(resistanceIn);
    
            if(value >= 5 && value <= 10)
                {
                resistance = value;
                }
            else
                {
                resistance = -1;
                errorInInputs = true;
                returnMessage = "Resistance is outside of valid range";
                }
            }
    
        return returnMessage;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Check that the resistance is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkEndTime(String endTimeIn)
        {
        String returnMessage = "";

        if(endTimeIn.isEmpty())
            {
            voltage = -1;
            errorInInputs = true;
            returnMessage = "End Time input is empty";
            }
        else
            {
            double value = Double.parseDouble(endTimeIn);
    
            if(value > 0)
                {
                endTime = value;
                }
            else
                {
                endTime = -1;
                errorInInputs = true;
                returnMessage = "End time is outside of valid range";
                }
            }
    
        return returnMessage;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Check that the resistance is within valid range
    //
    /////////////////////////////////////////////////////////////////
    private String checkTimeSteps(String timeStepsIn)
        {
        String returnMessage = "";

        if(timeStepsIn.isEmpty())
            {
            voltage = -1;
            errorInInputs = true;
            returnMessage = "Time Step input is empty";
            }
        else
            {
            double value = Double.parseDouble(timeStepsIn);
    
            if(value > 0 && value <= endTime)
                {
                timeSteps = value;
                }
            else
                {
                timeSteps = -1;
                errorInInputs = true;
                returnMessage = "Time Step is outside of valid range";
                }
            }
    
        return returnMessage;
        }


    /////////////////////////////////////////////////////////////////
    //
    // Check that the file name is valid
    //
    /////////////////////////////////////////////////////////////////
    private String checkFileName(String fileNameIn)
        {
        String returnMessage = "";
    
        if(fileNameIn.isEmpty())
            {
            fileName = "Error";
            errorInInputs = true;
            returnMessage = "File Name input is empty";
            }
        else
            {
            if(fileNameIn.contains(".txt"))
                {
                fileName = fileNameIn;
                }
            else
                {
                fileName = fileNameIn + ".txt";
                }
            }
    
        return returnMessage;
        }

    
    /////////////////////////////////////////////////////////////////
    //
    // Check that the file name is valid
    //
    /////////////////////////////////////////////////////////////////
    private String checkFileDirectory(String fileDirIn)
        {
        String returnMessage = "";
        File file = new File(fileDirIn);
    
        
        if(fileDirIn.isEmpty())
            {
            fileDirectory = System.getProperty("user.dir");     //If we do not submit  a directory then use current
            }
        else if (file.exists())
            {
            fileDirectory = fileDirIn;
            }
        else
            {
            try
                {
                //Try to create a new file and if it fails we will show there is an error
                file.createNewFile();
                file.delete();
                fileDirectory = fileDirIn;
                }
            catch (Exception e)
                {
                fileDirectory = "Error";
                errorInInputs = true;
                returnMessage = "File Directory is Invalid";
                }
            }
    
        return returnMessage;
        }
    

    /////////////////////////////////////////////////////////////////
    //
    // Will add all the labels to the gui
    //
    /////////////////////////////////////////////////////////////////
    private void addLabels()
        {
        //Create title label
        JLabel titleLabel = new JLabel("Assignment 1: Input Desired Values");
        titleLabel.setBounds(75, 10, 350, 25);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
    
        //Add voltage label
        JLabel voltageLabel = new JLabel("Voltage: Min: 4V, Max: 15V");
        voltageLabel.setBounds(15, 50, 225, 25);

        voltageErrorLabel = new JLabel("");
        voltageErrorLabel.setBounds(15, 100, 225, 25);
        voltageErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
        //Add capacitor label
        JLabel capacitorLabel = new JLabel("Capacitance (nF): Min: 1, Max: 100");
        capacitorLabel.setBounds(265, 50, 225, 25);

        capacitorErrorLabel = new JLabel("");
        capacitorErrorLabel.setBounds(265, 100, 225, 25);
        capacitorErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
        //Add inductor label
        JLabel inductorLabel = new JLabel("Inductance (mH): Min: 1, Max: 100");
        inductorLabel.setBounds(15, 150, 225, 25);

        inductorErrorLabel = new JLabel("");
        inductorErrorLabel.setBounds(15, 200, 225, 25);
        inductorErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
        //Add resistance label
        JLabel resistanceLabel = new JLabel("Resistance: Min: 5Ω , Max: 10Ω");
        resistanceLabel.setBounds(265, 150, 225, 25);

        resistorErrorLabel = new JLabel("");
        resistorErrorLabel.setBounds(265, 200, 225, 25);
        resistorErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
        //Add End Time label
        JLabel endTimeLabel = new JLabel("End Time: Must be > 0s");
        endTimeLabel.setBounds(15, 250, 225, 25);

        endTimeErrorLabel = new JLabel("");
        endTimeErrorLabel.setBounds(15, 300, 225, 25);
        endTimeErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
        //Add Time Step label
        JLabel timeStepLabel = new JLabel("Time Steps: Must be > 0s");
        timeStepLabel.setBounds(265, 250, 225, 25);

        timeStepErrorLabel = new JLabel("");
        timeStepErrorLabel.setBounds(265, 300, 225, 25);
        timeStepErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));

        //Add File Name Step label
        JLabel fileNameLabel = new JLabel("Output File Name");
        fileNameLabel.setBounds(15, 350, 225, 25);

        fileNameErrorLabel = new JLabel("");
        fileNameErrorLabel.setBounds(15, 400, 225, 25);
        fileNameErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));

        //Add File Directory Step label
        JLabel fileDirLabel = new JLabel("Output File Directory");
        fileDirLabel.setBounds(265, 350, 225, 25);

        fileDirErrorLabel = new JLabel("");
        fileDirErrorLabel.setBounds(265, 400, 225, 25);
        fileDirErrorLabel.setFont(new Font("Serif", Font.BOLD, 12));
    
    
        //
        //Add all labels to window
        //
        window.add(titleLabel);
        window.add(voltageLabel);
        window.add(capacitorLabel);
        window.add(inductorLabel);
        window.add(resistanceLabel);
        window.add(voltageLabel);
        window.add(endTimeLabel);
        window.add(timeStepLabel);
        window.add(fileNameLabel);
        window.add(fileDirLabel);
    
        window.add(voltageErrorLabel);
        window.add(capacitorErrorLabel);
        window.add(inductorErrorLabel);
        window.add(resistorErrorLabel);
        window.add(endTimeErrorLabel);
        window.add(timeStepErrorLabel);
        window.add(fileNameErrorLabel);
        window.add(fileDirErrorLabel);
        }


    /////////////////////////////////////////////////////////////////
    //
    // Will add all the input fields to the gui
    //
    /////////////////////////////////////////////////////////////////
    private void addInputFields()
        {
        //Add voltage Input
        voltageInput = new JTextField();
        voltageInput.setBounds(10, 80, 230, 25);
    
        //Add capacitor Input
        capacitorInput = new JTextField();
        capacitorInput.setBounds(260, 80, 230, 25);
    
        //Add inductor Input
        inductorInput = new JTextField();
        inductorInput.setBounds(10, 180, 230, 25);
    
        //Add resistance Input
        resistanceInput = new JTextField();
        resistanceInput.setBounds(260, 180, 230, 25);
    
        //Add End Time Input
        endTimeInput = new JTextField();
        endTimeInput.setBounds(10, 280, 230, 25);
    
        //Add Time Step Input
        timeStepInput = new JTextField();
        timeStepInput.setBounds(260, 280, 230, 25);

        //Add End Time Input
        fileNameInput = new JTextField();
        fileNameInput.setBounds(10, 380, 230, 25);

        //Add Time Step Input
        fileDirInput = new JTextField();
        fileDirInput.setBounds(260, 380, 230, 25);
    
    
        //
        //Add all Inputs to window
        //
        window.add(voltageInput);
        window.add(capacitorInput);
        window.add(inductorInput);
        window.add(resistanceInput);
        window.add(endTimeInput);
        window.add(timeStepInput);
        window.add(fileNameInput);
        window.add(fileDirInput);
        }
    }
