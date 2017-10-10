package dodochazoenterprise.journeydiaries.view;

import android.app.Fragment;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.JourneyManageBinding;
import dodochazoenterprise.journeydiaries.databinding.JourneysFragmentBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneyManageFragment extends Fragment {
    private Journey journey = null;
    private String state = null;

    public JourneyManageFragment(Journey journey, String state) {
        super();
        this.journey = journey;
        this.state = state;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        JourneyManageBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.journey_manage, container, false);
        binding.setJvm(new JourneyViewModel(binding.getRoot().getContext(), journey, state));
        return binding.getRoot();
    }
}
