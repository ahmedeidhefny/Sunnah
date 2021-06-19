package com.is.sunnahapp.ui.splash;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.is.sunnahapp.R;
import com.is.sunnahapp.databinding.ActivitySplashBinding;
import com.is.sunnahapp.ui.base.BaseActivity;

/**
 * @author Ahmed Eid Hefny
 * @date 20/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public class SplashActivity extends BaseActivity {

    private Animation mTopAnimation, mBottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        mTopAnimation = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        mBottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        binding.imageLogoView.setAnimation(mTopAnimation);
        binding.imageLogoNameView.setAnimation(mBottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigationController.navigateToCollectionActivity(SplashActivity.this);
            }
        }, 2000);
    }
}