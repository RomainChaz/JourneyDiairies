package dodochazoenterprise.journeydiaries.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dodochazoenterprise.journeydiaries.MainActivity;
import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.model.Journey;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneyViewModel extends BaseObservable {
    private Journey journey;
    private Context context;
    private String state;
    public JourneyViewModel(Context context, Journey journey) {
        this.journey = journey;
        this.context = context;
    }

    @Bindable
    public String getState() {
        return this.state;
    }

    @Bindable
    public String getName() {
        return journey.getName();
    }

    @Bindable
    public String getFrom() {
        Calendar cal = journey.getFrom();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(cal.getTime());
    }

    @Bindable
    public String getTo() {
        Calendar cal = journey.getTo();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(cal.getTime());
    }
    public void onJourneyClick() {
        if (journey == null){
            state="Create";
            ((MainActivity) context).showManage(new Journey(), "Create");
        }else{
            state="Update";
            ((MainActivity) context).showManage(journey, "Update");
        }

    }
    public void onCancelClick(){
        ((MainActivity) context).returnStartup();
    }
    public void save(String name, String from, String to) {
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(new Date(from));
        calEnd.setTime(new Date(to));
        this.journey.setName(name);
        this.journey.setFrom(calBegin);
        this.journey.setTo(calEnd);

        ((MainActivity) context).returnStartup();
    }
}
