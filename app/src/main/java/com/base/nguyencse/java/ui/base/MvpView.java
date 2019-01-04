package com.base.nguyencse.java.ui.base;

import android.content.Context;

public interface MvpView {
    void showToast(String msg);

    Context getContext();
}
