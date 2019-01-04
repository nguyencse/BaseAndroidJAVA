package com.base.nguyencse.java.models;

import com.base.nguyencse.java.network.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class DummyModel extends BaseResponse {

    @SerializedName("title")
    private String title;

    public DummyModel() {
    }

    public DummyModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
