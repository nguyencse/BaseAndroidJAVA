package com.base.nguyencse.java.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import butterknife.BindView;
import com.base.nguyencse.java.R;
import com.base.nguyencse.java.adapter.DummyRecyclerViewAdapter;
import com.base.nguyencse.java.database.entity.DummyEntity;
import com.base.nguyencse.java.ui.base.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, DummyRecyclerViewAdapter.DummyListListener {

    @BindView(R.id.btn_add_dummy)
    Button btnAddDummy;

    @BindView(R.id.btn_count_dummies)
    Button btnCountDummies;

    @BindView(R.id.rcv_dummies)
    RecyclerView rcvDummies;

    private DummyRecyclerViewAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initActionBarBack("MainActivity", null);

        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        mPresenter.getDummy(1, 20);

        rcvDummies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvDummies.setNestedScrollingEnabled(false);

        adapter = new DummyRecyclerViewAdapter(rcvDummies, this);
        rcvDummies.setAdapter(adapter);

        btnAddDummy.setOnClickListener(v -> mPresenter.addNewDummy(adapter.getItemCount() - 1));
        btnCountDummies.setOnClickListener(view -> mPresenter.countDummies());
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void getDummySuccess(List<DummyEntity> dummies) {
        runOnUiThread(() -> adapter.setData(dummies));
    }

    @Override
    public void getDummyFailed(String error) {
        runOnUiThread(() -> showToast(error));
    }

    @Override
    public void showTotalDummies(int total) {
        runOnUiThread(() -> showToast(String.valueOf(total)));
    }

    @Override
    public void onBackPressed() {
        doubleBackToExit();
    }

    @Override
    public void onClickItem(DummyEntity entity) {
        showToast(entity.getTitle());
    }

    @Override
    public void onLoadingMore() {
    }
}
