package com.example.shubham.animemania.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for SharedPreferences
 * <p>
 * Created by shubham on 15/12/16.
 */

public class SharedPreferenceHelper {

    private final static String PREF_FILE = "PREF";

    /**
     * Set a string shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get a string shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static String getSharedPreferenceString(Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }

    /**
     * Clearing shared preference
     *
     */
    public static void clearSharedPreference(Context context)
    {
        SharedPreferences settings=context.getSharedPreferences(PREF_FILE,0);
        SharedPreferences.Editor editor=settings.edit();
        editor.clear();
        editor.apply();
    }

}