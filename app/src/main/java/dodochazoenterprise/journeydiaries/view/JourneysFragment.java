package dodochazoenterprise.journeydiaries.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.JourneysFragmentBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneysFragment extends Fragment {

    private JourneysFragmentBinding binding;
    private List<Journey> journeys;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState == null && journeys == null) {
            // Initialization of the list
            journeys = creation();

            binding =DataBindingUtil.inflate(inflater, R.layout.journeys_fragment, container, false);

            binding.journeysList.setLayoutManager(new
                    LinearLayoutManager(binding.getRoot().getContext()));
            binding.journeysList.setAdapter(new JourneyListAdapter(binding.getRoot().getContext(), journeys));
            binding.setJvm(new JourneyViewModel(binding.getRoot().getContext(), null));
        }
        return binding.getRoot();
    }

    private ArrayList<Journey> creation(){
        ArrayList<Journey> journeys = new ArrayList<>();
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(new Date("07/01/2017"));
        calEnd.setTime(new Date("09/01/2017"));
        journeys.add(new Journey("Los Angeles", calBegin, calEnd));

        calBegin = Calendar.getInstance();
        calEnd = Calendar.getInstance();
        calBegin.setTime(new Date("09/02/2017"));
        calEnd.setTime(new Date("09/11/2017"));
        journeys.add(new Journey("Dublin", calBegin, calEnd));

        calBegin = Calendar.getInstance();
        calEnd = Calendar.getInstance();
        calBegin.setTime(new Date("09/11/2017"));
        journeys.add(new Journey("Lyon", calBegin, calEnd));
        return journeys;
    }

    public void update(){
        binding.journeysList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

