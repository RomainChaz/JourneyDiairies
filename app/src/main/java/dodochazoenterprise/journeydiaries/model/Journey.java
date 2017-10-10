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
    public Journey() {
        name ="";
        from = Calendar.getInstance();
        to = Calendar.getInstance();
    }
    public Journey(int id, String name, Calendar from, Calendar to) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
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
}
