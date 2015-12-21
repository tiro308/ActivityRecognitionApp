package com.example.atrt01admin.meinexample03;


import java.io.Serializable;
import java.util.Date;

public class RecordItem implements Serializable {

    private static final long serialVersionUID=1L;
    String activity;
    double latitude;
    double longitude;
    Date time;

    public RecordItem(){
        this.activity = "still";
        this.latitude = 16.0;
        this.longitude=48.0;
        this.time = new Date();
    }

    public RecordItem(String activity, double latitude, double longitude, Date time) {

        this.activity=activity;
        this.latitude = latitude;
        this.longitude=longitude;
        this.time = time;
    }

    public String getActivity() {

        return activity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Date getTime() {
        return time;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String recordItemToString(){
        return "activity: " + activity +
        " latitude: " + latitude +
        " longitude: " + longitude +
        " time: " + time +
        "\n";
    }
}

/*
    public void gsonBuilder() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        RecordItem recordItem = new RecordItem();
        String json = gson.toJson(recordItem);
        System.out.println(json);

        json = gson.toJson(null);
        System.out.println(json);
    }
*/
