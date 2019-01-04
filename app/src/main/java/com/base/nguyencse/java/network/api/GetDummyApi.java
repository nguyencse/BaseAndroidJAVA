package com.base.nguyencse.java.network.api;

import com.base.nguyencse.java.network.ApiCall;
import com.base.nguyencse.java.network.ApiCallback;
import com.base.nguyencse.java.network.ApiClient;
import com.base.nguyencse.java.network.BaseNetworkApi;
import com.base.nguyencse.java.network.response.DummyResponse;

public class GetDummyApi extends BaseNetworkApi<DummyResponse> {

    private int page;
    private int num;

    public GetDummyApi(int page, int num) {
        this.page = page;
        this.num = num;
    }

    @Override
    protected ApiCall<DummyResponse> getApiCall() {
        return ApiClient.getInstance().getApiService().getDummyList(page, num);
    }

    public void requestData(ApiCallback<DummyResponse> apiCallback) {
        request(apiCallback);
    }
}
