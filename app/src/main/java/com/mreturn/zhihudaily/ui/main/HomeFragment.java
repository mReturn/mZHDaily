package com.mreturn.zhihudaily.ui.main;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.Presenter.HomePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.adapter.StoriesAdapter;
import com.mreturn.zhihudaily.listener.LoadMoreScrollListener;
import com.mreturn.zhihudaily.model.StoryListBean;
import com.mreturn.zhihudaily.ui.BaseFragment;
import com.mreturn.zhihudaily.utils.CommonUtils;
import com.mreturn.zhihudaily.utils.ToastShow;
import com.mreturn.zhihudaily.widget.RollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class HomeFragment extends BaseFragment implements HomeView {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.srl_home)
    SwipeRefreshLayout srlHome;

    private HomePresenter homePresenter = new HomePresenter(this);
    private String currentData;
    private StoriesAdapter mAdapter;
    private List<Integer> readedList;

    @Override
    protected BasePresenter createPresenter() {
        return homePresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHome.setItemAnimator(new DefaultItemAnimator());
        rvHome.setHasFixedSize(true); //确保item宽高 不需再额外计算每个item大小
        mAdapter = new StoriesAdapter(new ArrayList<StoryListBean.StoriesBean>(0), getContext());
        rvHome.setAdapter(mAdapter);
        srlHome.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));

    }

    @Override
    protected void setListener() {
        rvHome.addOnScrollListener(new LoadMoreScrollListener() {
            @Override
            protected void onLoadMore() {
                homePresenter.loadMore(currentData);
            }
        });

        srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresenter.loadLatest(getContext());
            }
        });

    }

    @Override
    protected void getData() {
//        if (NetUtils.isNetAvailable(getContext())) {
            homePresenter.loadLatest(getContext());
//        } else {
//            homePresenter.getCache(getContext());
//        }
    }

    @Override
    public void setRefreshing(final boolean b) {
        srlHome.post(new Runnable() {
            @Override
            public void run() {
                srlHome.setRefreshing(b);
            }
        });
    }

    @Override
    public void setCurrentDate(String date) {
        currentData = date;
    }

    @Override
    public void showStories(StoryListBean storyList) {
        mAdapter.clearData();
        if (storyList.getTop_stories() != null && storyList.getTop_stories().size() > 0) {
            //轮播图
            View topView = intTopView(storyList.getTop_stories());
            mAdapter.setHeadView(topView);
        }
        mAdapter.addTitle(getResources().getString(R.string.today_news));
        mAdapter.addDatas(storyList.getStories());
    }

    // 初始化轮播图
    private View intTopView(List<StoryListBean.TopStoriesBean> topStories) {
        View topView = LayoutInflater.from(getContext()).inflate(R.layout.story_top_view, rvHome, false);
        FrameLayout flTop = (FrameLayout) topView.findViewById(R.id.fl_top);
        LinearLayout llDots = (LinearLayout) topView.findViewById(R.id.ll_dots);
        //初始化vp及dot
        RollViewPager rollViewPager = new RollViewPager(getContext(), initDots(llDots, topStories.size()),
                R.drawable.point_focured, R.drawable.point_nomal, new RollViewPager.OnPagerClickCallback() {
            @Override
            public void onPagerClick(int position) {
                //TODO to detail
                ToastShow.show("to detial");
            }
        });

        rollViewPager.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, CommonUtils.dp2px(getContext(), 240)));
        rollViewPager.setTopStories(topStories);
        rollViewPager.startRoll();
        //添加到view
        flTop.removeAllViews();
        flTop.addView(rollViewPager);

        return topView;
    }

    // 初始化轮播图 圆点
    private ArrayList<View> initDots(LinearLayout llDots, int size) {
        ArrayList<View> dotList = new ArrayList<>();
        llDots.removeAllViews();
        ;
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    CommonUtils.dp2px(getContext(), 6), CommonUtils.dp2px(getContext(), 6));
            params.setMargins(5, 0, 5, 0);
            View dotView = new View(getContext());
            if (i == 0) {
                dotView.setBackgroundResource(R.drawable.point_focured);
            } else {
                dotView.setBackgroundResource(R.drawable.point_nomal);
            }
            dotView.setLayoutParams(params);
            llDots.addView(dotView);
            dotList.add(dotView);
        }
        return dotList;
    }


    @Override
    public void showMoreStory(List<StoryListBean.StoriesBean> stories) {
        mAdapter.addTitle(CommonUtils.getCnDate(currentData));
        mAdapter.addDatas(stories);
    }

    @Override
    public void showLoadLatestError() {
        ToastShow.show(R.string.load_fail);
    }

    @Override
    public void showLoadMoreError() {
        ToastShow.show(R.string.load_fail);
    }

    @Override
    public void setLoadMoreViewShow(boolean show) {
        if (show){
            mAdapter.addFooter();
            rvHome.smoothScrollToPosition(mAdapter.getItemCount());
        }else{
            mAdapter.removeFooter();
        }
    }

    @Override
    public void showNoMoreData() {
        ToastShow.show("已加载完毕");
    }
}
