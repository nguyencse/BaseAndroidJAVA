package com.base.nguyencse.java.utils;

import android.util.Log;
import com.base.nguyencse.java.BuildConfig;

public class LogUtils {
    /**
     * Send a DEBUG custom　log with message.
     *
     * @param tag the name of class
     * @param msg the message　you would like logged
     * @return void
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.LOG) {
            Log.d(tag, String.format("[line:%d]%s", getLineNumber(), msg));
        }
    }

    /**
     * Send a INFO custom　log with message.
     *
     * @param tag the name of class
     * @param msg the message　you would like logged
     * @return void
     */
    public static void i(String tag, String msg) {
        if (BuildConfig.LOG) {
            Log.i(tag, String.format("[line:%d]%s", getLineNumber(), msg));
        }
    }

    /**
     * Send a ERROR custom　log with message.
     *
     * @param tag the name of class
     * @param msg the message　you would like logged
     * @return void
     */
    public static void e(String tag, String msg, Exception e) {
        if (BuildConfig.LOG) {
            Log.e(tag, String.format("[line:%d]%s", getLineNumber(), msg), e);
        }
    }

    /**
     * Send a ERROR custom　log with message.
     *
     * @param tag the name of class
     * @param msg the message　you would like logged
     * @return void
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.LOG) {
            Log.e(tag, String.format("[line:%d]%s", getLineNumber(), msg));
        }
    }

    /**
     * Send a ERROR custom　log with message.
     *
     * @param tag the name of class
     * @param msg the message　you would like logged
     * @return void
     */
    public static void e(String tag, Exception msg) {
        if (BuildConfig.LOG) {
            Log.e(tag, String.format("[line:%d]%s", getLineNumber(), msg.getMessage()), msg);
        }
    }

    /**
     * Send a VERBOSE custom　log with message.
     *
     * @param tag the name of class
     * @param msg the message　you would like logged
     * @return void
     */
    public static void v(String tag, String msg) {
        if (BuildConfig.LOG) {
            Log.v(tag, String.format("[line:%d]%s", getLineNumber(), msg));
        }
    }

    /**
     * Send a WARN custom　log with message.
     *
     * @param tag the name of class
     * @param msg the message　you would like logged
     * @return void
     */
    public static void w(String tag, String msg) {
        if (BuildConfig.LOG) {
            Log.w(tag, String.format("[line:%d]%s", getLineNumber(), msg));
        }
    }

    /**
     * Get the current line number.
     *
     * @return int - Current line number.
     */
    public static int getLineNumber() {
        try {
            return Thread.currentThread().getStackTrace()[4].getLineNumber();
        } catch (Exception e) {
            return 0;
        }
    }
}
