package dodochazoenterprise.journeydiaries.model;

import android.databinding.Bindable;

import java.util.Calendar;

/**
 * Created by Romain on 09/10/2017.
 */

public class Journey {

    private int id;
    private String name;
    private Calendar from;
    private Calendar to;
    private Double latitude;
    private Double longitude;

    public Journey() {
        name ="";
        from = Calendar.getInstance();
        to = Calendar.getInstance();
    }

    public Journey(Double latitude, Double longitude) {
        name ="";
        from = Calendar.getInstance();
        to = Calendar.getInstance();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Journey(int id, String name, Calendar from, Calendar to, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Calendar getFrom() {
        return from;
    }
    public void setFrom(Calendar from) {
        this.from = from;
    }
    public Calendar getTo() {
        return to;
    }
    public void setTo(Calendar to) {
        this.to = to;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
