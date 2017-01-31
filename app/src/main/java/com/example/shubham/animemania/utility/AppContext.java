package com.example.shubham.animemania.utility;

import android.app.Application;
import android.content.Context;

/**
 * Utility Class for getting context of Application
 *
 * Created by shubham on 30/11/16.
 */
public class AppContext extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    private static void setContext(Context mContext) {
        AppContext.mContext = mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);

    }

}
