package dodochazoenterprise.journeydiaries;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dodochazoenterprise.journeydiaries.databinding.JourneysFragmentBinding;

/**
 * Created by Romain on 09/10/2017.
 */

public class JourneysFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        JourneysFragmentBinding binding =
                DataBindingUtil.inflate(inflater,R.layout.journeys_fragment,container,false);
        binding.journeysList.setLayoutManager(new
                LinearLayoutManager(binding.getRoot().getContext()));
        return binding.getRoot();
    }
}

