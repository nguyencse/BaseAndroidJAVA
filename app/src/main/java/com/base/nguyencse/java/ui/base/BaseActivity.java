package com.base.nguyencse.java.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.base.nguyencse.java.R;
import com.base.nguyencse.java.utils.LogUtils;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements MvpView {

    private String TAG = this.getClass().getSimpleName();
    private boolean doubleBackToExitPressedOnce = false;
    protected Unbinder unbinder;
    protected P mPresenter;

    protected abstract int getLayout();

    protected abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");
        setContentView(getLayout());
        bindView();
        initView();
    }

    protected void bindView() {
        unbinder = ButterKnife.bind(this);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    public void initActionBarBack(String title, View.OnClickListener onBackPress) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab == null) {
            return;
        }
        ab.setCustomView(R.layout.layout_actionbar_back);
        ab.setDisplayShowCustomEnabled(true);

        Button back = findViewById(R.id.btn_back);
        if (onBackPress != null) {
            back.setOnClickListener(onBackPress);
        } else {
            back.setOnClickListener(view -> finish());
        }

        setTitle(title);
    }

    private void setTitle(String text) {
        if (!(findViewById(R.id.title_text) instanceof TextView)) {
            return;
        }
        TextView title = findViewById(R.id.title_text);
        title.setText(text);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        LogUtils.d(TAG, "onDestroy");
        if (unbinder != null) unbinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroy();
    }

    public void doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        showToast("Please click BACK again to exit");
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    public Context getContext() {
        return super.getBaseContext();
    }
}
