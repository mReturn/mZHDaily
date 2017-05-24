package com.mreturn.zhihudaily.ui.ediotr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.adapter.BaseRecycleViewAdapter;
import com.mreturn.zhihudaily.adapter.BaseViewHolder;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.model.ThemeBean;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public class EditorActivity extends BaseToolBarAtivity {

    @BindView(R.id.rv_editor)
    RecyclerView mRecyclerView;

    private List<ThemeBean.EditorsBean> editorList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initBackToolBar(R.string.editor_list_title);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ThemeBean themeBean =getIntent().getParcelableExtra(Constant.THEME);
        editorList = themeBean.getEditors();
        setAdapter();
    }

    @SuppressWarnings("unchecked")
    private void setAdapter() {
        mRecyclerView.setAdapter(new BaseRecycleViewAdapter(this,editorList) {
            @Override
            public int getItemLayoutId(int layoutID) {
                return R.layout.item_editor;
            }

            @Override
            public void bindData(BaseViewHolder holder, int position, Object item) {
                final ThemeBean.EditorsBean editor = (ThemeBean.EditorsBean) item;
                ImageLoader.displayCircleImg(holder.getImageView(R.id.iv_editor_avatar),editor.getAvatar(),R.drawable.comment_avatar);
                holder.setText(R.id.tv_name,editor.getName());
                holder.setText(R.id.tv_info,editor.getBio());
                holder.setClickListener(R.id.rootview, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EditorActivity.this,EditorDetailActivity.class);
                        intent.putExtra(Constant.EDITOR_ID,editor.getId()+"");
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
