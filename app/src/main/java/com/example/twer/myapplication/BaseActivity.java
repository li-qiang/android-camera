package com.example.twer.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by twer on 9/12/15.
 */
public class BaseActivity extends Activity {

    public static final String pName = "main";

    private SharedPreferences setting = null;


    public String getMyString(String key, String defaultString) {
        return getSetting().getString(key, defaultString);
    }

    public void setMyString(String key, String value) {
        SharedPreferences.Editor editor = getSetting().edit();
        editor.putString(key, value);
        editor.apply();
    }

    private SharedPreferences getSetting() {
        if (setting == null) {
            setting = getApplicationContext().getSharedPreferences(pName, 0);
        }
        return setting;
    }

}
