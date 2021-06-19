package com.is.sunnahapp.di.module;


import com.is.sunnahapp.ui.bookList.BooksListActivity;
import com.is.sunnahapp.ui.bookList.BooksListFragment;
import com.is.sunnahapp.ui.chapterList.ChaptersListActivity;
import com.is.sunnahapp.ui.chapterList.ChaptersListFragment;
import com.is.sunnahapp.ui.settings.SettingsActivity;
import com.is.sunnahapp.ui.settings.SettingsFragment;
import com.is.sunnahapp.ui.hadithsList.HadithsListActivity;
import com.is.sunnahapp.ui.hadithsList.HadithsListFragment;
import com.is.sunnahapp.ui.collectionList.CollectionsListActivity;
import com.is.sunnahapp.ui.collectionList.CollectionsListFragment;
import com.is.sunnahapp.ui.main.MainActivity;
import com.is.sunnahapp.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Since we are using the dagger-android support library,
 * we can make use of Android Injection.
 * The ActivityModule generates AndroidInjector(this is the new dagger-android class which exist in dagger-android framework)
 * for Activities defined in this class.
 * This allows us to inject things into Activities using AndroidInjection.
 * inject(this) in the onCreate() method.
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector()
    abstract CollectionsListFragment contributeBankListFragment();

    @ContributesAndroidInjector()
    abstract CollectionsListActivity contributeWorldListActivity();

    @ContributesAndroidInjector()
    abstract BooksListFragment contributeBooksListFragment();

    @ContributesAndroidInjector()
    abstract BooksListActivity contributeBooksListActivity();

    @ContributesAndroidInjector()
    abstract ChaptersListFragment contributeChaptersListFragment();

    @ContributesAndroidInjector()
    abstract ChaptersListActivity contributeChaptersListActivity();

    @ContributesAndroidInjector()
    abstract HadithsListFragment contributeHadithsListFragment();

    @ContributesAndroidInjector()
    abstract HadithsListActivity contributehadithsListActivity();

    @ContributesAndroidInjector()
    abstract SettingsFragment contributePersonalInfoFragment();

    @ContributesAndroidInjector()
    abstract SettingsActivity contributeDetailsActivity();

    @ContributesAndroidInjector()
    abstract SplashActivity contributeSplashActivity();


}