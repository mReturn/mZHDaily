package com.mreturn.zhihudaily.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mreturn.zhihudaily.Presenter.BasePresenter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeContentView();
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mPresenter = createPresenter();
        mProgressDialog = new ProgressDialog(this);
        initView();
        initListener();
        initData(savedInstanceState);
    }

    private void beforeContentView() {
    }

    protected void showProgressDialog(String msg){
        mProgressDialog.setMessage(msg);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

            }
        });

    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract BasePresenter createPresenter();
}
