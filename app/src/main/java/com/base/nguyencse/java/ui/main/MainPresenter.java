package com.base.nguyencse.java.ui.main;

import android.arch.lifecycle.LiveData;
import com.base.nguyencse.java.App;
import com.base.nguyencse.java.database.AppDatabase;
import com.base.nguyencse.java.database.AppExecutor;
import com.base.nguyencse.java.database.entity.DummyEntity;
import com.base.nguyencse.java.models.DummyModel;
import com.base.nguyencse.java.network.ApiCallback;
import com.base.nguyencse.java.network.api.GetDummyApi;
import com.base.nguyencse.java.network.model.ErrorModel;
import com.base.nguyencse.java.network.response.DummyResponse;
import com.base.nguyencse.java.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends BasePresenter<IMainView> implements IMainPresenter {

    private AppDatabase mDb;

    public MainPresenter() {
        mDb = AppDatabase.getInstance(App.getInstance());
    }

    @Override
    public void addNewDummy(int currentPos) {
        AppExecutor.getInstance().getDiskIO().execute(() -> mDb.dummyDao().insertDummy(new DummyEntity(System.currentTimeMillis(), "Dummy title " + (currentPos + 1), System.currentTimeMillis())));
    }

    @Override
    public void getDummy(int page, int num) {
        AppExecutor.getInstance().getDiskIO().execute(() -> {
            LiveData<List<DummyEntity>> dummiesLiveData = mDb.dummyDao().loadDummies();
            dummiesLiveData.observe((MainActivity) getMvpView(),
                    dummyEntities -> {
                        if (dummyEntities != null && dummyEntities.size() > 0) {
                            if (isViewAttached()) {
                                getMvpView().getDummySuccess(dummyEntities);
                            }
                        }
                    });
        });


        GetDummyApi getDummyApi = new GetDummyApi(page, num);
        getDummyApi.requestData(new ApiCallback<DummyResponse>() {
            @Override
            public void success(DummyResponse response) {
                List<DummyModel> dummyModels = response.getDummyList();
                if (dummyModels != null) {
                    List<DummyEntity> dummyEntities = new ArrayList<>();

                    for (DummyModel model : dummyModels) {
                        dummyEntities.add(new DummyEntity(model));
                    }

                    mDb.dummyDao().insertDummies(dummyEntities);
                }
            }

            @Override
            public void unexpectedError(ErrorModel errors) {
                if (isViewAttached()) {
                    getMvpView().getDummyFailed(errors.getErrorMessage());
                }
            }
        });
    }

    @Override
    public void countDummies() {
        AppExecutor.getInstance().getDiskIO().execute(() -> {
            LiveData<List<DummyEntity>> dummiesLiveData = mDb.dummyDao().loadDummies();
            dummiesLiveData.observe((MainActivity) getMvpView(),
                    dummyEntities -> {
                        if (isViewAttached()) {
                            if (dummyEntities != null && dummyEntities.size() > 0) {
                                getMvpView().showTotalDummies(dummyEntities.size());
                            } else {
                                getMvpView().showTotalDummies(0);
                            }
                        }
                    });
        });
    }
}
