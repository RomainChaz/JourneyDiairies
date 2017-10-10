package dodochazoenterprise.journeydiaries.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.JourneyManageBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneyManageFragment extends Fragment {
    private Journey journey = null;

    public JourneyManageFragment(Journey journey) {
        super();
        this.journey = journey;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        JourneyManageBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.journey_manage, container, false);
        binding.setJvm(new JourneyViewModel(binding.getRoot().getContext(), journey));
        return binding.getRoot();
    }
}
