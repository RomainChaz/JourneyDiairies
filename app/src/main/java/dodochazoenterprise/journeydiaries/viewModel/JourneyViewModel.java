package dodochazoenterprise.journeydiaries.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dodochazoenterprise.journeydiaries.MainActivity;
import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.database.DatabaseImplementor;
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
        this.state = ( this.journey == null || this.journey.getName() == "") ? "Create" : "Update";
    }

    @Bindable
    public String getState() {
        return this.state;
    }

    @Bindable
    public int getId() {
        return journey.getId();
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
            this.state = "Create";
            ((MainActivity) context).showManage(new Journey());
        }else {
            this.state = "Update";
            ((MainActivity) context).showManage(journey);
        }
    }
    public void onCancelClick(){
        ((MainActivity) context).returnStartup();
    }
    public void save(int id, String name, String from, String to) {
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(new Date(from));
        calEnd.setTime(new Date(to));

        if (state == "Update"){
            this.journey.setId(id);
            this.journey.setName(name);
            this.journey.setFrom(calBegin);
            this.journey.setTo(calEnd);
        }else{
        }
        ((MainActivity) context).returnStartup();
    }
}
