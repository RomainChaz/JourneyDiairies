package dodochazoenterprise.journeydiaries.view;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dodochazoenterprise.journeydiaries.MainActivity;
import dodochazoenterprise.journeydiaries.R;
import dodochazoenterprise.journeydiaries.databinding.MapsFragmentBinding;
import dodochazoenterprise.journeydiaries.model.Journey;

/**
 * Created by Donatien on 11/10/2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationSource {

    public GoogleMap map;
    private Context context;
    public OnLocationChangedListener onLocationChangeListener;
    private Journey target = new Journey();
    private LatLng tmpLocation;
    private List<Journey> journeys;
    private MapsFragmentBinding binding;

    public MapsFragment(Context context, List<Journey> journeys) {
        this.context = context;
        this.journeys = journeys;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    String address = getAddress(new LatLng(point.latitude, point.longitude));
                    if (address.equals(" - ")) {
                        address = "Unknown destination";
                    }
                    tmpLocation = point;
                    builder.setTitle(address);

                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()

                            {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }
                    );

                    builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener()

                            {
                                public void onClick(DialogInterface dialog, int id) {
                                    addMarker(tmpLocation);
                                }
                            }
                    );

                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    String address = "";
                    for (Journey j : journeys) {
                        if (j.getLatitude() == marker.getPosition().latitude && j.getLongitude() == marker.getPosition().longitude) {
                            target = j;
                            address = getAddress(new LatLng(j.getLatitude(), j.getLongitude()));
                            if (address.equals(" - ")) {
                                address = j.getName();
                            } else {
                                address += "\n" + j.getName();
                            }
                            builder.setTitle(address);
                        }
                    }
                    FragmentManager fm = getFragmentManager();
                    ShowJourneyDialog dialogFragment = new ShowJourneyDialog (address, target.getNote());
                    dialogFragment.show(fm, "Manage journey");

                    return false;
                }
            });
            showMarkers();
            return;
        }

    }



    private void showMarkers() {
        for (Journey j : journeys) {
            LatLng position = new LatLng(j.getLatitude(), j.getLongitude());
            map.addMarker(new MarkerOptions().position(position));
        }
    }

    private String getAddress(LatLng latLng) {
        Geocoder geocoder =
                new Geocoder(context, Locale.getDefault());
        // Create a list to contain the result address
        List<Address> addresses = null;
        try {
            /*
             * Return 1 address.
             */
            addresses = geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude, 1);
        } catch (IOException e1) {
            Log.e("LocationSampleActivity",
                    "IO Exception in getFromLocation()");
            e1.printStackTrace();
            return ("IO Exception trying to get address");
        } catch (IllegalArgumentException e2) {
            // Error message to post in the log
            String errorString = "Illegal arguments " +
                    Double.toString(latLng.latitude) +
                    " , " +
                    Double.toString(latLng.longitude) +
                    " passed to address service";
            Log.e("LocationSampleActivity", errorString);
            e2.printStackTrace();
            return errorString;
        }
        // If the reverse geocode returned an address
        if (addresses != null && addresses.size() > 0) {
            // Get the first address
            String resultat = " - ";
            Address address = addresses.get(0);
            if (null == address.getCountryName() || null == address.getLocality()) {
                resultat = " - ";
            } else {
                resultat = address.getCountryName() + resultat + address.getLocality();
            }

            return resultat;
        } else {
            return " - ";
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

    public interface LocationInterface {
        public void setLocation(Location location);
    }

    public List<Journey> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
    }
}
