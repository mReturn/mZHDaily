package com.mreturn.zhihudaily.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;
    protected BasePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
        getData();
        return view;
    }

    protected void changeReadStatus(View view,int textViewId){
        TextView tv = (TextView) view.findViewById(textViewId);
        tv.setTextColor(getResources().getColor(R.color.textReaded));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.dispose();
    }

    protected abstract BasePresenter createPresenter();

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void getData();


}
