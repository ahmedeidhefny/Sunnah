package com.is.sunnahapp.data.local.shardPref;

/**
 * @author Ahmed Eid Hefny
 * @date 12/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public interface PreferencesHelper {

    void saveLang (String lang);

    String getLang ();

    void setFontSize (int fontSize);

    int getFontSize ();


}
