package com.example.shubham.animemania.utility;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.shubham.animemania.BuildConfig;

/**
 * Logger Class for logging important information according to need
 * <p/>
 * Created by shubham on 16/12/16.
 */
public final class Logger {

    /**
     * Logger Class for showing logs when mode is DEBug
     *
     * @param tag     TAG under which INFO message will be shown.
     * @param message Message to be shown under INFO tag.
     */
    public static void debug(String tag, String message) {
        if (BuildConfig.DEBUG) {
            if (TextUtils.isEmpty(tag)) tag = AppContext.class.getSimpleName();
            if (message == null) message = "";
            Log.d(tag, message);
        }

    }

    /**
     * Logger Class for showing logs when mode is DEBug
     *
     * @param tag       TAG under which INFO message will be shown.
     * @param message   Message to be shown under INFO tag.
     * @param throwable Error which is thrown
     */
    public static void error(String tag, String message, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            if (TextUtils.isEmpty(tag)) tag = AppContext.class.getSimpleName();
            if (message == null) message = "";
            Log.e(tag, message, throwable);
        }

    }

    /**
     * Display a toast with supplied message for {@code SHORT} period of ic_time.
     *
     * @param msg The message to be shown. Can be formatted text.
     */
    public static void toastShort(CharSequence msg) {
        if (msg != null) {
            Toast.makeText(AppContext.getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Display a toast with supplied message for {@code LONG} period of ic_time.
     *
     * @param msg The message to be shown. Can be formatted text.
     */
    public static void toastLong(CharSequence msg) {
        if (msg != null) {
            Toast.makeText(AppContext.getContext(), msg, Toast.LENGTH_LONG).show();
        }
    }
}