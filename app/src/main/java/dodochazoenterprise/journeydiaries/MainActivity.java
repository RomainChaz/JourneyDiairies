package dodochazoenterprise.journeydiaries;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dodochazoenterprise.journeydiaries.databinding.MainActivityBinding;
import dodochazoenterprise.journeydiaries.model.Journey;
import dodochazoenterprise.journeydiaries.view.JourneyManageFragment;
import dodochazoenterprise.journeydiaries.view.JourneysFragment;

/**
 * Created by Romain on 09/10/2017.
 */

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.main_activity);
        this.showStartup();
    }
    public void showStartup() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        JourneysFragment fragment = new JourneysFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
    public void showManage(Journey journey) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        JourneyManageFragment fragment;
        fragment = new JourneyManageFragment(journey);

        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
    public void returnStartup() {
        FragmentManager manager = getFragmentManager();
        manager.popBackStackImmediate();
        JourneysFragment fragment = (JourneysFragment) manager.findFragmentById(R.id.fragment_container);
        fragment.update();
    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistenState) {
        super.onSaveInstanceState(outState, outPersistenState);
    }
}