package com.is.sunnahapp.data.local.shardPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.is.sunnahapp.Constants;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 10/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public class AppPreferencesHelper implements PreferencesHelper {


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public void saveLang(String lang) {
        mPrefs.edit().putString(Constants.SP_LANGUAGE, lang).apply();
    }

    @Override
    public String getLang() {
        return mPrefs.getString(Constants.SP_LANGUAGE, "");
    }

    @Override
    public void setFontSize(int fontSize) {
        mPrefs.edit().putInt(Constants.SP_FONT_SIZE, fontSize).apply();
    }

    @Override
    public int getFontSize() {
        return mPrefs.getInt(Constants.SP_FONT_SIZE, 16);
    }
}
