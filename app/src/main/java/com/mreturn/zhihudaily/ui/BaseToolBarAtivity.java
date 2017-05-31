package com.mreturn.zhihudaily.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
    BasePresenter mPresenter;

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
        prepareData(getIntent());
        initView();
        setListener();
        initData(savedInstanceState);
    }

    protected void beforeContentView() {
    }

    protected void prepareData(Intent intent) {
    }

    protected void setListener() {
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
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    protected void initToolbar(){
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    protected void initBackToolBar(Object title){
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (title != null){
            if (title instanceof Integer)
                setToolBarTitle((Integer) title);

            if (title instanceof String)
                setToolBarTitle((String)title);
        }else{
            setToolBarTitle("");
        }
    }

    protected void initToolbar(String title){
        initToolbar();
        setToolBarTitle(title);
    }

    protected void initToolbar(int resId){
        initToolbar();
        setToolBarTitle(resId);
    }

    public void setToolBarTitle(String title){
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);

    }

    public void setToolBarTitle(int resId){
        if (getSupportActionBar() != null){
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

    protected abstract void initData(Bundle savedInstanceState);
}
