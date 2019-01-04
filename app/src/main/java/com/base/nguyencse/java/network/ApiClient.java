package com.base.nguyencse.java.network;

import android.os.Build;
import com.base.nguyencse.java.BuildConfig;
import com.base.nguyencse.java.Settings;
import com.base.nguyencse.java.utils.LogUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static final String TAG = "ApiClient";

    private static final String BASE_URL = Settings.API_URL;
    private ApiService apiService;
    private static ApiClient instance = null;

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    ApiClient() {
        if (instance == null) {
            apiService = buildRetrofit();
        }
    }

    private ApiService buildRetrofit() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            try {
                SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
                sslcontext.init(null, null, new java.security.SecureRandom());
                SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
                httpClient.sslSocketFactory(NoSSLv3Factory);

            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        httpClient.connectTimeout(15, TimeUnit.SECONDS);
        httpClient.readTimeout(15, TimeUnit.SECONDS);
        httpClient.writeTimeout(15, TimeUnit.SECONDS);

        httpClient.addInterceptor(chain -> {
            Request request;
            Request original = chain.request();

            request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("X-API-key", Settings.API_KEY)
                    .method(original.method(), original.body())
                    .build();

            if (BuildConfig.DEBUG) {
                LogUtils.i(TAG, "header:" + request.headers().toString() + " " + request.url());
            }

            return chain.proceed(request);
        });
        Gson gson = new GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(new ErrorHandlingAdapter.ErrorHandlingCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return this.apiService;
    }
}
