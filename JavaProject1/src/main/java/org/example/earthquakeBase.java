package org.example;


import org.joda.time.DateTime;

public class earthquakeBase {
    public String ID;
    public Integer depthInMeters;
    public String typeOfMagnitude;
    public String magnitude;
    public DateTime date;
    public String state;
    public earthquakeBase(String[] args)
    {
        this.ID = args[0];
        this.depthInMeters = Integer.parseInt(args[1]);
        this.typeOfMagnitude = args[2];
        this.magnitude = args[3];
        this.date = DateTime.parse(args[5].substring(0,10));
        this.state = args[4];
    }

    @Override
    public String toString() {
        return String.format("%s %d %s %s %s", ID,depthInMeters,typeOfMagnitude,magnitude, date);
    }
}
