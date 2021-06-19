package com.is.sunnahapp.util;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.is.sunnahapp.Constants;
import com.is.sunnahapp.ui.bookList.BooksListActivity;
import com.is.sunnahapp.ui.collectionList.CollectionsListActivity;
import com.is.sunnahapp.ui.settings.SettingsActivity;
import com.is.sunnahapp.ui.hadithsList.HadithsListActivity;
import com.is.sunnahapp.ui.splash.SplashActivity;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 1/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class NavigationController {

    //region Variables

    //private final int containerId;
    public Uri photoURI;

    //endregion


    //region Constructor
    @Inject
    public NavigationController() {

        // This setup is for MainActivity
        //this.containerId = R.id.content_frame;
    }

    //endregion

    public void navigateToSettingsActivity(FragmentActivity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToCollectionActivity(Activity activity) {
        Intent intent = new Intent(activity, CollectionsListActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToSplashActivity(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToBooksActivity(FragmentActivity activity, String collectionName) {
        Intent intent = new Intent(activity, BooksListActivity.class);
        intent.putExtra(Constants.COLLECTION_NAME, collectionName);
        activity.startActivity(intent);
    }

    public void navigateToHadithsActivity(FragmentActivity activity, String collectionName , String bookNumber) {
        Intent intent = new Intent(activity, HadithsListActivity.class);
        intent.putExtra(Constants.COLLECTION_NAME, collectionName);
        intent.putExtra(Constants.BOOK_NUMBER, bookNumber);
        activity.startActivity(intent);
    }

}
