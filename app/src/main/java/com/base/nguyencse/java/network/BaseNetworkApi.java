package com.base.nguyencse.java.network;

public abstract class BaseNetworkApi<T> extends ApiClient {
    protected abstract ApiCall<T> getApiCall();

    public void request(ApiCallback<T> apiCallback) {
        getApiCall().enqueue(apiCallback);
    }
}
