package com.base.nguyencse.java.network;

public interface ApiCall<T> {
    void enqueue(ApiCallback<T> callback);
}

