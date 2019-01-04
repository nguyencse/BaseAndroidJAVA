package com.base.nguyencse.java.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.base.nguyencse.java.database.entity.DummyEntity;

import java.util.List;

@Dao
public abstract class DummyDao {

    @Query("SELECT * FROM Dummy ORDER BY create_at ASC")
    public abstract LiveData<List<DummyEntity>> loadDummies();

    @Query("SELECT * FROM Dummy WHERE dummy_id = :id")
    public abstract LiveData<DummyEntity> loadDummy(long id);

    @Insert
    public abstract void insertDummy(DummyEntity entity);

    @Insert
    public abstract void insertDummies(List<DummyEntity> entities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateDummy(DummyEntity entity);

    @Delete
    public abstract void deleteDummy(DummyEntity entity);

    @Query("DELETE FROM Dummy")
    public abstract void deleteDummies();
}