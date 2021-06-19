package com.is.sunnahapp.di.module;

import android.app.Application;
import android.content.Context;

import com.is.sunnahapp.Constants;
import com.is.sunnahapp.data.local.shardPref.AppPreferencesHelper;
import com.is.sunnahapp.data.local.shardPref.PreferenceInfo;
import com.is.sunnahapp.data.local.shardPref.PreferencesHelper;
import com.is.sunnahapp.util.Connectivity;
import com.is.sunnahapp.util.NavigationController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Module
public class AppModule {

    @Singleton
    @Provides
    Connectivity provideConnectivity(Application app) {
        return new Connectivity(app);
    }

    @Singleton
    @Provides
    NavigationController provideNavigationController() {
        return new NavigationController();
    }

//    @Singleton
//    @Provides
//    SharedPreferences provideSharedPreferences(Application app) {
//        return PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
//    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

}
