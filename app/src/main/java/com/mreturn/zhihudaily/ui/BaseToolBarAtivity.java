package com.mreturn.zhihudaily.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.utils.ToastShow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mReturn
 * on 2017/5/17.
 */

public abstract class BaseToolBarAtivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private ProgressDialog mProgressDialog;
    protected BasePresenter mPresenter;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeContentView();
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mPresenter = createPresenter();
        mProgressDialog = new ProgressDialog(this);
        initView();
        setListener();
        initData(savedInstanceState);
    }

    protected void beforeContentView() {
    }

    protected void showProgressDialog(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                ToastShow.show("cancle");
            }
        });
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    protected void initToolbar(){
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            ToastShow.show("not null");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    protected void initToolbar(String title){
        initToolbar();
        setToolBarTitle(title);
    }

    protected void initToolbar(int redId){
        initToolbar();
        setToolBarTitle(redId);
    }

    public void setToolBarTitle(String txt){
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(txt);

    }

    public void setToolBarTitle(int resId){
        if (getSupportActionBar() != null){
            ToastShow.show("set title");
            getSupportActionBar().setTitle(resId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.dispose();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    protected abstract int getLayoutId();

    protected abstract BasePresenter createPresenter();

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void initData(Bundle savedInstanceState);
}
