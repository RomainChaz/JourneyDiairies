package dodochazoenterprise.journeydiaries;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import dodochazoenterprise.journeydiaries.databinding.MainActivityBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.view.JourneyManageFragment;
import dodochazoenterprise.journeydiaries.view.JourneysFragment;
import dodochazoenterprise.journeydiaries.view.MapsFragment;

/**
 * Created by Romain on 09/10/2017.
 */

public class MainActivity extends AppCompatActivity implements MapsFragment.LocationInterface {
    private MainActivityBinding binding;
    private MapsFragment mapsFragment;
    private JourneysFragment journeyFragment;

    private LocationListener myLocationListener = new
            LocationListener() {
                @Override
                public void onLocationChanged(Location newLocation) {
                    if (mapsFragment != null && mapsFragment.onLocationChangeListener != null)
                        mapsFragment.onLocationChangeListener.onLocationChanged(newLocation);
                }

                @Override
                public void onStatusChanged(String provider, int
                        status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

    private static final int MY_LOCATION_REQUEST_CODE = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        this.showStartup();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void showStartup() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        journeyFragment = new JourneysFragment();
        transaction.replace(R.id.fragment_container, journeyFragment);
        transaction.commit();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    public void showManage(Journey journey) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        JourneyManageFragment fragment;
        fragment = new JourneyManageFragment(journey);

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void showMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        } else {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10L, 100.0f, myLocationListener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120L, 100.0f, myLocationListener);
        }
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        List<Journey> journeys = journeyFragment.getJourneys();
        mapsFragment = new MapsFragment(this, journeys);

        transaction.replace(R.id.fragment_container, mapsFragment);
        transaction.commit();
    }

    public void returnStartup(boolean changed) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(myLocationListener);
        }

        FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            JourneysFragment fragment = (JourneysFragment) manager.findFragmentById(R.id.fragment_container);
            fragment.update(changed);

        }
        if (changed) {
            journeyFragment.retrieveJourneys();
            if (mapsFragment != null) {
                mapsFragment.setJourneys(journeyFragment.getJourneys());
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (permissions.length == 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120L, 100.0f, myLocationListener);
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120L, 100.0f, myLocationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            Toast toast = Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment instanceof MapsFragment)
            mapsFragment = null;
        returnStartup(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistenState) {
        super.onSaveInstanceState(outState, outPersistenState);
    }

    @Override
    public void setLocation(Location location) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}