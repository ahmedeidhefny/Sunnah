package com.is.sunnahapp.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.is.sunnahapp.R;
import com.is.sunnahapp.databinding.ActivityDetailsBinding;
import com.is.sunnahapp.ui.base.BaseActivity;

/**
 * @author Ahmed Eid Hefny
 * @date 15/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class SettingsActivity extends BaseActivity {

    //region Variables
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        // Init all UI
        initUI(binding);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    //endregion

    //region Private Methods

    private void initUI(ActivityDetailsBinding binding) {

        // Toolbar
        initToolbar(binding.toolbar,"Settings");

        // setup Fragment
        setupFragment(new SettingsFragment());
        // Or you can call like this
        //setupFragment(new NewsListFragment(), R.id.content_frame);

    }

    //endregion



}

