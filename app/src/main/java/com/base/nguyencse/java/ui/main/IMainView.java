package com.base.nguyencse.java.ui.main;

import com.base.nguyencse.java.database.entity.DummyEntity;
import com.base.nguyencse.java.ui.base.MvpView;

import java.util.List;

public interface IMainView extends MvpView {
    void getDummySuccess(List<DummyEntity> dummies);

    void getDummyFailed(String error);

    void showTotalDummies(int total);
}
