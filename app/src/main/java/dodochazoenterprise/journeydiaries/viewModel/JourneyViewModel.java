package dodochazoenterprise.journeydiaries.viewModel;

import android.databinding.BaseObservable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dodochazoenterprise.journeydiaries.model.Journey;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneyViewModel extends BaseObservable {
    private Journey journey;
    public JourneyViewModel(Journey journey) {
        this.journey = journey;
    }
    public String getName() {
        return journey.getName();
    }
    public String getFrom() {
        Calendar cal = journey.getFrom();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }
    public String getTo() {
        Calendar cal = journey.getTo();
        DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                Locale.getDefault());
        return sdf.format(cal.getTime());
    }
    public void onJourneyClick() {
        System.out.println("OnClick "+getName());

    }
}
