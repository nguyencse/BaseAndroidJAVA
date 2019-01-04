package com.base.nguyencse.java.network.model;

import com.google.gson.annotations.SerializedName;

public class ErrorModel {
    @SerializedName("status")
    private int status;

    @SerializedName(value = "err_no", alternate = {"error_no"})
    private int errorNo = -1;

    @SerializedName(value = "err_detail", alternate = {"error_detail"})
    private int errorDetail = -1;

    @SerializedName(value = "err_message", alternate = {"error_message"})
    private String errorMessage;

    public ErrorModel(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public int getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(int errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
