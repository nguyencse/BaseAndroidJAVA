package com.base.nguyencse.java.ui.base;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    public String TAG = this.getClass().getSimpleName();
    protected V mMvpView;

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    protected boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public boolean checkViewAttached() {
        return isViewAttached();
    }

}
