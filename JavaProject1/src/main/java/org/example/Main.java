package org.example;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jfree.ui.RefineryUtilities;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException, SQLException, ClassNotFoundException {
        var earthquakesBase = new ArrayList<earthquakeBase>();
        var earthquakes = new ArrayList<earthquake>();
        var stateItems = new HashSet<String>();
        var states = new ArrayList<state>();
        ReadingData("C:\\Users\\user\\Documents\\Java\\JavaProject1\\Test.csv", earthquakesBase,stateItems);
        CreateStatesArray(states, stateItems);
        MatchingData(earthquakesBase,new ArrayList<>(stateItems),earthquakes);
        DatabaseControl.Conn("Test1.s3db");
//        AddingValuesToDatabase(earthquakes, states);
        DatabaseControl.ReadDB("select state from states, earthquakes " +
                "where states.stateID = earthquakes.stateID " +
                "and earthquakes.depthInMeters " +
                "= (select max(earthquakes.depthInMeters) from earthquakes where earthquakes.date like '2013%')",0);
        DatabaseControl.ReadDB("select avg(earthquakes.magnitude) from earthquakes, states " +
                "where earthquakes.stateID = states.stateID and states.state = 'West Virginia'", 1);
        PrintTheGraphic();

    }
    public static void ReadingData(String pathOfFile, ArrayList<earthquakeBase> earthquakeBases, HashSet<String> states) throws IOException, CsvValidationException {
        FileReader filereader = new FileReader(pathOfFile);
        CSVReader csvReader = new CSVReader(filereader);
        String[] nextRecord;
        var head = csvReader.readNext();
        while ((nextRecord = csvReader.readNext()) != null ) {
            earthquakeBases.add(new earthquakeBase(nextRecord));
            states.add(nextRecord[4]);
        }
    }
    public static void CreateStatesArray(ArrayList<state> statesItems, HashSet<String> states)
    {
        var tempStates = new ArrayList<>(states);
        for(int i = 0; i<states.size(); i++)
        {
            statesItems.add(new state(i,tempStates.get(i)));
        }
    }
    public static void MatchingData(ArrayList<earthquakeBase> earthquakeBases, ArrayList<String> states, ArrayList<earthquake> earthquakes)
    {
        for (org.example.earthquakeBase earthquakeBase : earthquakeBases) {
            earthquakes.add(new earthquake(earthquakeBase, states.indexOf(earthquakeBase.state)));
        }
    }
    public static void AddingValuesToDatabase(ArrayList<earthquake> earthquakes, ArrayList<state> states)
    {
        for (org.example.earthquake earthquake : earthquakes) {
            DatabaseControl.addEarthquakeItem(earthquake);
        }
        for(state item: states)
        {
            DatabaseControl.addStateItem(item);
        }
    }
    public static void PrintTheGraphic() throws SQLException, ClassNotFoundException {
        GraphicClass chart = new GraphicClass(
                "Среднее кол-во землетрясений за год" ,
                "Среднее кол-во землетрясений за год");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}