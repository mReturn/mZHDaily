package com.mreturn.zhihudaily.ui.theme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.Presenter.ThemePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.adapter.BaseStoryRecycleAdapter;
import com.mreturn.zhihudaily.adapter.ThemeAdapter;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.listener.LoadMoreScrollListener;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.model.ThemeBean;
import com.mreturn.zhihudaily.ui.BaseFragment;
import com.mreturn.zhihudaily.ui.ediotr.EditorActivity;
import com.mreturn.zhihudaily.utils.ImageLoader;
import com.mreturn.zhihudaily.utils.ToastShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mReturn
 * on 2017/5/23.
 * 其他类别内容
 */

public class ThemesFragment extends BaseFragment implements ThemeView {

    @BindView(R.id.rv_home)
    RecyclerView mRecycleView;
    @BindView(R.id.srl_home)
    SwipeRefreshLayout mSwipRefreshayout;

    ThemePresenter mPresenter = new ThemePresenter(this);
    private static final String THEME_ID = "theme_id";
    private int themeId;
    private int lastItemId; //最后一条内容的id
    ThemeAdapter mAdapter;

    public ThemesFragment() {
    }

    public static ThemesFragment newInstance(int themeId) {
        ThemesFragment fragment = new ThemesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(THEME_ID, themeId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            themeId = getArguments().getInt(THEME_ID);
    }

    @Override
    protected BasePresenter createPresenter() {
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setHasFixedSize(true);
        mAdapter = new ThemeAdapter(new ArrayList<StoriesBean>(), getContext());
        mRecycleView.setAdapter(mAdapter);
        mSwipRefreshayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    protected void setListener() {
        mRecycleView.addOnScrollListener(new LoadMoreScrollListener() {
            @Override
            protected void onLoadMore() {
                if (mAdapter.getFooterState() == BaseStoryRecycleAdapter.STATE_LOADING)
                    return;
                mPresenter.getMoreThemeStories(themeId, lastItemId);
            }
        });

        mSwipRefreshayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getTheme(themeId);
            }
        });
    }

    @Override
    protected void getData() {
        mPresenter.getTheme(themeId);
    }

    @Override
    public void setRefreshing(final boolean b) {
        mSwipRefreshayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipRefreshayout.setRefreshing(b);
            }
        });
    }

    @Override
    public void showTheme(ThemeBean themeBean) {
        mAdapter.clearData();
        setHeadView(themeBean);
        setEditorView(themeBean);
        mAdapter.addDatas(themeBean.getStories());
    }

    private void setHeadView(ThemeBean themeBean) {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_item, mRecycleView, false);
        ImageView ivTop = (ImageView) headView.findViewById(R.id.iv_top);
        TextView tvTitle = (TextView) headView.findViewById(R.id.tv_top_title);
        TextView tvSource = (TextView) headView.findViewById(R.id.tv_imgSource);
        ImageLoader.display(getContext(), ivTop, themeBean.getBackground());
        tvTitle.setTextSize(20);
        tvTitle.setText(themeBean.getDescription());
        tvSource.setText(themeBean.getImage_source());
        mAdapter.setHeadView(headView);
    }

    private void setEditorView(final ThemeBean themeBean) {
        List<ThemeBean.EditorsBean> editorList = themeBean.getEditors();
        if (editorList != null && editorList.size() > 0) {
            View editorView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_theme_title, mRecycleView, false);
            LinearLayout llImgs = (LinearLayout) editorView.findViewById(R.id.ll_imgs);
            for (int i = 0; i < editorList.size(); i++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_editor_img,null);
                ImageView iv = (ImageView) view.findViewById(R.id.iv_editor);
                ImageLoader.displayCircleImg(iv,editorList.get(i).getAvatar(),R.drawable.comment_avatar);
                llImgs.addView(view);
            }
            //点击事件
            editorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editorIntent = new Intent(getContext(),EditorActivity.class);
                    editorIntent.putExtra(Constant.THEME,themeBean);
                    startActivity(editorIntent);
                }
            });
            mAdapter.addTitle(editorView);
        }
    }

    @Override
    public void showMoreThemeStory(List<StoriesBean> stories) {
        mAdapter.addDatas(stories);
    }

    @Override
    public void showGetThemeError() {
        ToastShow.show(R.string.load_fail);
    }

    @Override
    public void showLoadMoreError() {
        ToastShow.show(R.string.load_fail);
    }

    @Override
    public void setLoadMoreViewShow(boolean show) {
        if (show) {
            mAdapter.addFooter();
            mRecycleView.smoothScrollToPosition(mAdapter.getItemCount());
        } else {
            mAdapter.removeFooter();
        }
    }

    @Override
    public void showNoMoreData() {
        ToastShow.show("已加载完毕");
    }

    public void setLastItemId(int id) {
        lastItemId = id;
    }
}
