package dodochazoenterprise.journeydiaries;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dodochazoenterprise.journeydiaries.databinding.MainActivityBinding;

/**
 * Created by Romain on 09/10/2017.
 */

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.main_activity);
    }
}