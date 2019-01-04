package com.base.nguyencse.java.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.SystemClock;

public class BitmapUtils {

    private static final String TAG = "BitmapUtils";

    /**
     * Default value of retry limit
     */
    private static final int RETRY_LIMIT_DEFAULT = 3;

    /**
     * Sleep time at failure (ms)
     */
    private static final long SLEEP_TIME_OF_FAILURE = 100L;


    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param width  width
     * @param height height
     * @param config configuration
     * @return Bitmap
     */
    public static Bitmap createBitmap(int width, int height, Bitmap.Config config) {
        return createBitmap(width, height, config, RETRY_LIMIT_DEFAULT);
    }

    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param width      width
     * @param height     height
     * @param config     configuration
     * @param retryLimit retry limit
     * @return Bitmap
     */
    public static Bitmap createBitmap(int width, int height, Bitmap.Config config, int retryLimit) {
        return createBitmap(width, height, config, 1, retryLimit);
    }

    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param width      width
     * @param height     height
     * @param config     configuration
     * @param retryCount number of retries
     * @param retryLimit retry limit
     * @return Bitmap
     */
    private static Bitmap createBitmap(int width, int height, Bitmap.Config config, int retryCount, int retryLimit) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            if (retryCount > retryLimit) {
                throw e;
            }
            System.gc();
            SystemClock.sleep(SLEEP_TIME_OF_FAILURE * retryCount);

            return createBitmap(width, height, config, ++retryCount, retryLimit);
        }
    }

    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param source source
     * @param x      X coordinate
     * @param y      Y coordinate
     * @param width  width
     * @param height height
     * @param matrix matrix
     * @param filter filter
     * @return Bitmap
     */
    public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix matrix, boolean filter) {
        return createBitmap(source, x, y, width, height, matrix, filter, RETRY_LIMIT_DEFAULT);
    }

    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param source     source
     * @param x          X coordinate
     * @param y          Y coordinate
     * @param width      width
     * @param height     height
     * @param matrix     matrix
     * @param filter     filter
     * @param retryLimit retry limit
     * @return Bitmap
     */
    public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix matrix, boolean filter, int retryLimit) {
        return createBitmap(source, x, y, width, height, matrix, filter, 1, retryLimit);
    }

    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param source     source
     * @param x          X coordinate
     * @param y          Y coordinate
     * @param width      width
     * @param height     height
     * @param matrix     matrix
     * @param filter     filter
     * @param retryCount number of retries
     * @param retryLimit retry limit
     * @return Bitmap
     */
    public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix matrix, boolean filter, int retryCount, int retryLimit) {
        try {
            if (source != null) {
                return Bitmap.createBitmap(source, x, y, width, height, matrix, filter);
            } else {
                return null;
            }
        } catch (OutOfMemoryError e) {
            if (retryCount > retryLimit) {
                throw e;
            }
            System.gc();
            SystemClock.sleep(SLEEP_TIME_OF_FAILURE * retryCount);

            return createBitmap(source, x, y, width, height, matrix, filter, ++retryCount, retryLimit);
        }
    }

    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param src       source
     * @param dstWidth  width
     * @param dstHeight height
     * @param filter    filter
     * @return Bitmap
     */
    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter) {
        return createScaledBitmap(src, dstWidth, dstHeight, filter, RETRY_LIMIT_DEFAULT);
    }

    /**
     * Generate Bitmap.
     * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param src        source
     * @param dstWidth   width
     * @param dstHeight  height
     * @param filter     filter
     * @param retryLimit retry limit
     * @return Bitmap
     */
    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter, int retryLimit) {
        return createScaledBitmap(src, dstWidth, dstHeight, filter, 1, retryLimit);
    }

    /**
     *      * Generate Bitmap.
     *      * When OutOfMemoryError occurs at generation, sleeps for the specified time and retries.
     *
     * @param src        source
     * @param dstWidth   width
     * @param dstHeight  height
     * @param filter     filter
     * @param retryCount number of retries
     * @param retryLimit retry limit
     * @return Bitmap
     */
    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter, int retryCount, int retryLimit) {
        try {
            return Bitmap.createScaledBitmap(src, dstWidth, dstHeight, filter);
        } catch (OutOfMemoryError e) {
            if (retryCount > retryLimit) {
                throw e;
            }
            System.gc();
            SystemClock.sleep(SLEEP_TIME_OF_FAILURE * retryCount);

            return createScaledBitmap(src, dstWidth, dstHeight, filter, ++retryCount, retryLimit);
        }
    }
}
