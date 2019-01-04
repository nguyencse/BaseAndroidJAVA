package com.base.nguyencse.java.network.response;

import com.base.nguyencse.java.models.DummyModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DummyResponse extends BaseResponse {

    @SerializedName("dummy_list")
    private List<DummyModel> dummyList;

    public List<DummyModel> getDummyList() {
        return dummyList;
    }

    public void setDummyList(List<DummyModel> dummyList) {
        this.dummyList = dummyList;
    }
}
