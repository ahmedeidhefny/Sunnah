package com.is.sunnahapp.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.is.sunnahapp.App;
import com.is.sunnahapp.Config;
import com.is.sunnahapp.di.component.DaggerAppComponent;
import com.is.sunnahapp.di.module.ApiModule;
import com.is.sunnahapp.di.module.DbModule;

import dagger.android.AndroidInjection;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;


/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public class AppInjector {

    private AppInjector() {
        new Config();
    }

    public static void init(App app) {

        DaggerAppComponent
                .builder()
                .application(app)
                .apiModule(new ApiModule())
                .dbModule(new DbModule())
                .build()
                .inject(app);

                 app
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }

                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasAndroidInjector
                ) {
            AndroidInjection.inject(activity);
        }

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                                              Bundle savedInstanceState) {
                                    if (f instanceof Injectable) {
                                        AndroidSupportInjection.inject(f);
                                    }
                                }
                            }, true);
        }

//        if (activity instanceof FragmentActivity) {
//            ((FragmentActivity) activity).getSupportFragmentManager()
//                    .registerFragmentLifecycleCallbacks(
//                            new FragmentManager.FragmentLifecycleCallbacks() {
//                                @Override
//                                public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
//                                                              Bundle savedInstanceState) {
//                                    if (f instanceof Injectable) {
//                                        AndroidSupportInjection.inject(f);
//                                        //AndroidInjection.inject(f);
//                                    }
//                                }
//                            }, true);
//        }else {
//            AndroidInjection.inject(activity);
//        }
    }
}
