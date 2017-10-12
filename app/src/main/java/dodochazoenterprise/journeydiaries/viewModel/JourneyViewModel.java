package dodochazoenterprise.journeydiaries.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private String stateReturn;

    public JourneyViewModel(Context context, Journey journey) {
        this.journey = journey;
        this.context = context;
        this.state = (this.journey == null || this.journey.getName() == "") ? "Create" : "Update";
        this.stateReturn = (this.journey == null || this.journey.getName() == "") ? "Cancel" : "Delete";
    }

    @Bindable
    public String getStateReturn() {
        return this.stateReturn;
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
    public String getLatitude() {
        String latitude = "";
        if(journey.getLongitude() != null)
            latitude =Double.toString(journey.getLatitude());
        return latitude;
    }

    @Bindable
    public String getLongitude() {
        String longitude = "";
        if(journey.getLongitude() != null)
            longitude =Double.toString(journey.getLongitude());
        return longitude;
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

        if (journey == null) {
            this.state = "Create";
            ((MainActivity) context).showManage(new Journey());
        } else {
            this.state = "Update";
            ((MainActivity) context).showManage(journey);
        }
    }

    public void onDeleteClick() {
        DatabaseImplementor db = new DatabaseImplementor((MainActivity) context);
        if (stateReturn.equals("Delete")) {
            db.delete(this.journey.getId());

            Toast toast = Toast.makeText(context, ((MainActivity) context).getResources().getString(R.string.journey_deleted), Toast.LENGTH_SHORT);
            toast.show();
        }
        ((MainActivity) context).returnStartup(Boolean.FALSE);
    }

    public void onMapClick() {
        ((MainActivity) context).showMap();
    }

    public void save(int id, String name, String from, String to, String latitude, String longitude) {
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(new Date(from));
        calEnd.setTime(new Date(to));


        DatabaseImplementor db = new DatabaseImplementor((MainActivity) context);

        this.journey.setName(name);
        this.journey.setFrom(calBegin);
        this.journey.setTo(calEnd);
        this.journey.setLatitude(Double.parseDouble(latitude));
        this.journey.setLongitude(Double.parseDouble(longitude));
        String result = "";
        if (state == "Update") {
            this.journey.setId(id);
            db.update(id, this.journey);
            result = ((MainActivity) context).getResources().getString(R.string.journey_updated);
        } else {
            db.create(journey);
            result = ((MainActivity) context).getResources().getString(R.string.journey_created);
        }

        Toast toast = Toast.makeText(context, result, Toast.LENGTH_SHORT);
        toast.show();
        ((MainActivity) context).returnStartup(Boolean.TRUE);
    }
}
