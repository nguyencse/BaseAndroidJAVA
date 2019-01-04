package com.base.nguyencse.java.network;

import com.base.nguyencse.java.network.model.ErrorModel;

public interface ApiCallback<T> {
    void success(T response);

    void unexpectedError(ErrorModel errors);
}
