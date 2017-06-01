package com.mreturn.zhihudaily.ui.setting;

import android.os.Bundle;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;

/**
 * Created by mReturn
 * on 2017/6/1.
 */

public class SettingActivity extends BaseToolBarAtivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initBackToolBar("设置选项");
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_setting,new SettingFragment())
                .commit();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
