package org.example;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.SQLException;

public class GraphicClass extends ApplicationFrame {

    public GraphicClass( String applicationTitle , String chartTitle ) throws SQLException, ClassNotFoundException {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Годы","Среднее кол-во землетрясений",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1280 , 720 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( ) throws SQLException, ClassNotFoundException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (int i = 1950; i < 2020; i++)
        {
            double value = DatabaseControl.ReturnDBValue(String.format("" +
                    "select avg(earthquakes.magnitude) from earthquakes, states where earthquakes.date like '%d",i)+"%'");
            dataset.addValue(value, "Землетрясения",Integer.toString(i));
        }
        return dataset;
    }

}