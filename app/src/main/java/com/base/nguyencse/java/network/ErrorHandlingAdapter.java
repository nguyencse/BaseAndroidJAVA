package com.base.nguyencse.java.network;

import com.base.nguyencse.java.App;
import com.base.nguyencse.java.Constants;
import com.base.nguyencse.java.network.model.ErrorModel;
import com.base.nguyencse.java.network.response.BaseResponse;
import com.base.nguyencse.java.utils.LogUtils;
import retrofit2.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class ErrorHandlingAdapter {
    public static final String TAG = "Network";
    public static final String E_SERVER = "server";
    public static final String E_CLIENT = "client";
    public static final String E_NETWORK = "network";
    public static final String E_UNKNOWN = "unknown";

    public static class ErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
        @Override
        public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations,
                                     Retrofit retrofit) {
            if (getRawType(returnType) != ApiCall.class) {
                return null;
            }
            if (!(returnType instanceof ParameterizedType)) {
                throw new IllegalStateException(
                        "ApiCall must have generic type (e.g., ApiCall<ResponseBody>)");
            }
            Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
            Executor callbackExecutor = retrofit.callbackExecutor();
            return new ErrorHandlingCallAdapter<>(responseType, callbackExecutor);
        }

        private static final class ErrorHandlingCallAdapter<R> implements CallAdapter<R, ApiCall> {
            private final Type responseType;
            private final Executor callbackExecutor;

            ErrorHandlingCallAdapter(Type responseType, Executor callbackExecutor) {
                this.responseType = responseType;
                this.callbackExecutor = callbackExecutor;
            }

            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public ApiCall adapt(Call<R> call) {
                return new ApiCallAdapter<>(call, callbackExecutor);
            }

        }
    }

    /**
     * Adapts a {@link Call} to {@link ApiCall}.
     */
    static class ApiCallAdapter<T> implements ApiCall<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;

        ApiCallAdapter(Call call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public void enqueue(final ApiCallback<T> callback) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    int code = response.code();
                    String url = response.raw().request().url().toString();
                    if (code >= 200 && code < 300) {
                        if (response.body() instanceof BaseResponse) {
                            BaseResponse baseResponse = (BaseResponse) response.body();
                            if (baseResponse.getStatus() == 0 && baseResponse.getResultCode() == 1) {
                                LogUtils.i(TAG, "onSuccess ---> " + url);
                                callback.success(response.body());
                            } else {
                                logError(E_SERVER, Constants.ErrorCode.INVALID_RESULT, response.message(), url);
                                callback.unexpectedError(new ErrorModel(Constants.ErrorCode.INVALID_RESULT, response.message()));
                            }
                        } else {
                            logError(E_CLIENT, Constants.ErrorCode.PARSE_RESULT_FAIL, response.message(), url);
                            callback.unexpectedError(new ErrorModel(Constants.ErrorCode.PARSE_RESULT_FAIL, response.message()));
                        }
                    } else if (code >= 400 && code < 500) {
                        if (code == 400) {
                            BaseResponse errorResponse = null;
                            if (response.errorBody() != null) {
                                try {
                                    errorResponse = App.getGSon().fromJson(response.errorBody().string(), BaseResponse.class);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (errorResponse != null && errorResponse.getErrorList() != null) {
                                for (ErrorModel error : errorResponse.getErrorList()) {
                                    logError(E_CLIENT, error, url);
                                }
                                if (errorResponse.getErrorList().size() == 0) {
                                    callback.unexpectedError(new ErrorModel(Constants.ErrorCode.PARSE_ERROR_FAIL, response.message()));
                                } else {
                                    callback.unexpectedError(errorResponse.getErrorList().get(0));
                                }
                            } else {
                                logError(E_CLIENT, Constants.ErrorCode.PARSE_ERROR_FAIL, response.message(), url);
                                callback.unexpectedError(new ErrorModel(Constants.ErrorCode.PARSE_ERROR_FAIL, response.message()));
                            }
                        } else {
                            logError(E_SERVER, code, response.message(), url);
                            callback.unexpectedError(new ErrorModel(code, response.message()));
                        }
                    } else if (code >= 500 && code < 600) {
                        logError(E_SERVER, code, response.message(), url);
                        callback.unexpectedError(new ErrorModel(response.code(), response.message()));
                    } else {
                        logError(E_UNKNOWN, code, response.message(), url);
                        callback.unexpectedError(new ErrorModel(response.code(), response.message()));
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    String url = call.request().url().toString();
                    List<ErrorModel> errors = new ArrayList<>();
                    if (t instanceof SocketTimeoutException) {
                        logError(E_NETWORK, Constants.ErrorCode.NETWORK_TIME_OUT, t.getMessage(), url);
                        errors.add(new ErrorModel(Constants.ErrorCode.NETWORK_TIME_OUT, t.getMessage()));
                    } else if (t instanceof UnknownHostException) {
                        logError(E_NETWORK, Constants.ErrorCode.NETWORK_NO_CONNECTION, t.getMessage(), url);
                        errors.add(new ErrorModel(Constants.ErrorCode.NETWORK_NO_CONNECTION, t.getMessage()));
                    } else {
                        logError(E_CLIENT, Constants.ErrorCode.UNKNOWN, t.getMessage(), url);
                        errors.add(new ErrorModel(Constants.ErrorCode.UNKNOWN, t.getMessage()));
                    }
                    callback.unexpectedError(errors.get(0));
                }
            });
        }
    }

    private static void logError(String reason, int code, String message, String url) {
        LogUtils.e(TAG, String.format("onFailure %s ---> (%s):%s:%s", reason, code, message, url));
    }

    private static void logError(String reason, ErrorModel error, String url) {
        LogUtils.e(TAG, String.format("onFailure %s ---> no=%s:detail=%s:message=%s:%s", reason, error.getErrorNo(), error.getErrorDetail(), error.getErrorMessage(), url));
    }
}