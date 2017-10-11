package dodochazoenterprise.journeydiaries.view;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.JourneyManageBinding;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

/**
 * Created by Donatien on 11/10/2017.
 */

public class MapsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        JourneyManageBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.maps_fragment, container, false);
        binding.mapView.getMapAsync(this);

        return binding.getRoot();
    }



}
