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
import java.util.List;

import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.database.DatabaseImplementor;
import dodochazoenterprise.journeydiaries.databinding.JourneysFragmentBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneysFragment extends Fragment {

    private JourneysFragmentBinding binding;
    private List<Journey> journeys;
    private LayoutInflater inflater;
    ViewGroup container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState == null && journeys == null) {
            // Initialization of the list
            retrieveJourneys();

            this.inflater = inflater;
            this.container = container;
            bindJourneys();
        }
        return binding.getRoot();
    }

    private void bindJourneys() {
        binding = DataBindingUtil.inflate(inflater, R.layout.journeys_fragment, container, false);

        binding.journeysList.setLayoutManager(new
                LinearLayoutManager(binding.getRoot().getContext()));
        binding.journeysList.setAdapter(new JourneyListAdapter(binding.getRoot().getContext(), journeys));
        binding.setJvm(new JourneyViewModel(binding.getRoot().getContext(), null));
    }

    public void retrieveJourneys() {
        List<Journey> journeys = new ArrayList<>();

        DatabaseImplementor db = new DatabaseImplementor(this.getActivity());
        journeys = db.getJourneys();

        this.journeys =  journeys;
    }

    public List<Journey> getJourneys() {
        return journeys;
    }

    public static JourneysFragment newInstance() {
        return new JourneysFragment();
    }

    public void update(boolean changed) {
        if (changed) {
            retrieveJourneys();
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, JourneysFragment.newInstance()).commit();
        binding.journeysList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

