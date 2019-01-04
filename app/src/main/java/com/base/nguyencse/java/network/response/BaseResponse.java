package com.base.nguyencse.java.network.response;

import android.support.annotation.Nullable;
import com.base.nguyencse.java.network.model.ErrorModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("result_code")
    private int resultCode;

    @SerializedName("num")
    private int num;

    @Nullable
    @SerializedName("error_list")
    List<ErrorModel> errorList;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Nullable
    public List<ErrorModel> getErrorList() {
        return errorList;
    }

    public void setErrorList(@Nullable List<ErrorModel> errorList) {
        this.errorList = errorList;
    }
}
