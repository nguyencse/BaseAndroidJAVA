package com.base.nguyencse.java.database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Object LOCK = new Object();
    private static AppExecutor sInstance;
    private Executor mDiskIO;

    private AppExecutor(Executor diskIO) {
        mDiskIO = diskIO;
    }

    public static AppExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }
}
