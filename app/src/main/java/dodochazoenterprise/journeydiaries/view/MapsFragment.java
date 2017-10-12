package dodochazoenterprise.journeydiaries.view;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import dodochazoenterprise.journeydiaries.databinding.MapsFragmentBinding;
import dodochazoenterprise.journeydiaries.model.Journey;

/**
 * Created by Donatien on 11/10/2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationSource {

    public GoogleMap map;
    private Location currentLocation;
    private Context context;
    public OnLocationChangedListener onLocationChangeListener;
    //TODO: Use database
    private List<Journey> journeys;
    private MapsFragmentBinding binding;

    public MapsFragment(Context context, List<Journey> journeys) {
        this.context = context;
        this.journeys = journeys;
    }

    private void showMarkers() {
        for (Journey j : journeys){
            LatLng position = new LatLng(j.getLatitude(), j.getLongitude());
            map.addMarker(new MarkerOptions().position(position));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: When go from map fragment to create journey fragment, error xause by merge layout and attachToParent = false
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.maps_fragment, container, false);
        }

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
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {
                    addMarker(point);
                }
            });
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    for(Journey j: journeys){
                        if(j.getLatitude() == marker.getPosition().latitude && j.getLongitude() == marker.getPosition().longitude){
                            String tmp = "Marker: " + marker.getPosition();
                            Toast.makeText(context, tmp, Toast.LENGTH_SHORT).show();

                        }
                    }
                    return false;
                }
            });
            showMarkers();
            return;
        }

    }

    @Override
    public void onDestroyView() {

        FragmentManager fm = getFragmentManager();

        Fragment xmlFragment = fm.findFragmentById(R.id.maps_fragment);
        if (xmlFragment != null) {
            fm.beginTransaction().remove(xmlFragment).commit();
        }

        super.onDestroyView();
    }

    private void addMarker(LatLng point) {

        map.addMarker(new MarkerOptions().position(point));

        ((MainActivity) context).showManage(new Journey(point.latitude, point.longitude));
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangeListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        this.onLocationChangeListener = null;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public interface LocationInterface {
        public void setLocation(Location location);
    }
}
