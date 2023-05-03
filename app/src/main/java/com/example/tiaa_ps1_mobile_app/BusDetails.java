package com.example.tiaa_ps1_mobile_app;

public class BusDetails {
    String time;
    int cost;
    int duration;
    int availability;
    String operator;
    String[] features;

    public BusDetails(String time, int cost, int duration, int availability, String operator, String[] features) {
        this.time = time;
        this.cost = cost;
        this.duration = duration;
        this.availability = availability;
        this.operator = operator;
        this.features = features;
    }

    public String getTime() {
        return time;
    }

    public String getCost() {
        return "INR "+cost;
    }

    public String getDuration() {
        //convert to hours and minutes
        return duration+" hours";
    }

    public String getAvailability() {
        return availability+ " seats left";
    }

    public String getOperator() {
        return operator;
    }

    public String getFeatures() {
        String f="";
        for(String s:features)
            f=f+" "+s;
        return f;
    }
}
