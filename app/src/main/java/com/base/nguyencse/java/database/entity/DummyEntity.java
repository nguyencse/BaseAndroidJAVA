package com.base.nguyencse.java.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.base.nguyencse.java.models.DummyModel;

@Entity(tableName = "Dummy")
public class DummyEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dummy_id")
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "create_at")
    private long createAt;

    @Ignore
    public DummyEntity(String title, long createAt) {
    }

    public DummyEntity(long id, String title, long createAt) {
        this.id = id;
        this.title = title;
        this.createAt = createAt;
    }

    public DummyEntity(DummyModel model) {
        this.id = System.currentTimeMillis();
        this.title = model.getTitle();
        this.createAt = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
}
