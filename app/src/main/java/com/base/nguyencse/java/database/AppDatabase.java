package com.base.nguyencse.java.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.base.nguyencse.java.database.dao.DummyDao;
import com.base.nguyencse.java.database.entity.DummyEntity;

@Database(entities = {DummyEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "db_dummy";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract DummyDao dummyDao();
}
