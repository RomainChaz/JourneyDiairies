package dodochazoenterprise.journeydiaries;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import dodochazoenterprise.journeydiaries.database.DatabaseImplementor;
import dodochazoenterprise.journeydiaries.databinding.MainActivityBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.view.JourneyManageFragment;
import dodochazoenterprise.journeydiaries.view.JourneysFragment;

/**
 * Created by Romain on 09/10/2017.
 */

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        this.showStartup();
    }

    public void showStartup() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneysFragment fragment = new JourneysFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
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

    public void returnStartup(boolean changed) {
        manager.removeUpdates(myLocationListener);

        FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
        JourneysFragment fragment = (JourneysFragment) manager.findFragmentById(R.id.fragment_container);
        fragment.update(changed);
    }

    public void showMap(boolean changed) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
        FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
        JourneysFragment fragment = (JourneysFragment) manager.findFragmentById(R.id.fragment_container);
        fragment.update(changed);
    }

    LocationListener myLocationListener = new
            LocationListener() {
                @Override
                public void onLocationChanged(Location newLocation) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120, 100, myLocationListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120, 100, myLocationListener);

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistenState) {
        super.onSaveInstanceState(outState, outPersistenState);
    }
}