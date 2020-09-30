package cmpt275.stolys.model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class graphResults
    {
    //
    // Class Variables
    //
    private JFreeChart chart;

    
    /////////////////////////////////////////////////////////////////
    //
    // Constructor
    //
    /////////////////////////////////////////////////////////////////
    public graphResults()
        {
        //Do Nothing
        }


    /////////////////////////////////////////////////////////////////
    //
    // Create line chart to display data
    //
    /////////////////////////////////////////////////////////////////
    public JFreeChart createChart(double[] dataValues, double timeSteps, double endTime)
        {
        chart = ChartFactory.createXYLineChart(
            "Charge of Capacitor" ,
            "Time (sec)",
            "Charge (C)",
            createDataset(dataValues, timeSteps, endTime),
            PlotOrientation.VERTICAL,
            false , true , false);
        
        
        //Return chart to be displayed on screen
        return chart;
        }

    
    /////////////////////////////////////////////////////////////////
    //
    // Create dataset to plot
    //
    /////////////////////////////////////////////////////////////////
    private XYDataset createDataset(double[] values, double timeStep, double endTime)
        {
        final XYSeries chargeData = new XYSeries( "Charge of Capacitor" );
        
        
        for(int index = 0; index < values.length; index++)
            {
            chargeData.add( timeStep * index , values[index]);
            }

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(chargeData);
        
        return dataset;
        }
    
    
    }
