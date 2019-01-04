package com.base.nguyencse.java.ui.base;

interface MvpPresenter<T extends MvpView> {
    void attachView(T mvpView);

    void detachView();
}
