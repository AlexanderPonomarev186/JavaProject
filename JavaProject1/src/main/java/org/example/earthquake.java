package org.example;

import org.joda.time.DateTime;

public class earthquake {
    public String ID;
    public Integer depthInMeters;
    public String typeOfMagnitude;
    public Double magnitude;
    public DateTime date;
    public Integer stateID;
    public earthquake(String[] args)
    {
        this.ID = args[0];
        this.depthInMeters = Integer.parseInt(args[1]);
        this.typeOfMagnitude = args[2];
        this.magnitude = Double.parseDouble(args[3]);
        this.date = DateTime.parse(args[5].substring(0,10));
        this.stateID = Integer.parseInt(args[4]);
    }
    public earthquake(earthquakeBase item, Integer stateID)
    {
        this.ID = item.ID;
        this.depthInMeters = item.depthInMeters;
        this.typeOfMagnitude = item.typeOfMagnitude;
        this.magnitude = Double.parseDouble(item.magnitude);
        this.date = item.date;
        this.stateID = stateID;
    }
    @Override
    public String toString() {
        return String.format("%s %d %s %s %s", ID,depthInMeters,typeOfMagnitude,magnitude, date);
    }
}