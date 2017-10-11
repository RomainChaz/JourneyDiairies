package dodochazoenterprise.journeydiaries.view;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import dodochazoenterprise.journeydiaries.MainActivity;
import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.JourneyManageBinding;
import dodochazoenterprise.journeydiaries.databinding.MapsFragmentBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.viewModel.JourneyViewModel;

/**
 * Created by Donatien on 11/10/2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationSource {

    public GoogleMap map;
    private Location currentLocation;
    private Context context;
    public OnLocationChangedListener onLocationChangeListener;
    //TODO: Use database
    private List<LatLng> positions;

    public MapsFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        positions = new ArrayList<>();
        MapsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.maps_fragment, container, false);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.setLocationSource(this);

            LatLng sydney = new LatLng(-33.852, 151.211);
            map.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Sydney"));

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {
                    addMarker(point);
                }
            });

            return;
        }

    }


    private void addMarker(LatLng point) {
        positions.add(point);
        map.addMarker(new MarkerOptions().position(point));

        //TODO: Add link between MapsFragment and jvm.JourneyClick() whitout use of xml
        ((MainActivity) context).showManage(new Journey());
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangeListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        this.onLocationChangeListener = null;
    }

    public Location getCurrentLocation(){
        return currentLocation;
    }

    public interface LocationInterface {
        public void setLocation(Location location);
    }
}
