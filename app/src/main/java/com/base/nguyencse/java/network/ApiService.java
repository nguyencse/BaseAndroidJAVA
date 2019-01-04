package com.base.nguyencse.java.network;

import com.base.nguyencse.java.network.request.Params;
import com.base.nguyencse.java.network.response.DummyResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET(ApiEndPoint.GET_DUMMYLIST_API)
    ApiCall<DummyResponse> getDummyList(@Path(Params.PAGE) int page, @Path(Params.NUM) int num);
}
