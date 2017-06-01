package com.mreturn.zhihudaily.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.Presenter.CommentPresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.adapter.BaseStoryRecycleAdapter;
import com.mreturn.zhihudaily.adapter.CommentAdapter;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.listener.LoadMoreScrollListener;
import com.mreturn.zhihudaily.model.CommentBean;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.ToastShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mReturn
 * on 2017/5/31.
 */

public class CommentActivity extends BaseToolBarAtivity implements CommentView{

    @BindView(R.id.rv_comment)
    RecyclerView rvComment;

    int storyId;
    int commentCount;
    int longCommentCount;
    int shortCommentCount;
    int lastCommentId;

    boolean noMoreComment;
    boolean loadedAllLongComment;
    CommentPresenter commentPresenter = new CommentPresenter(this);
    CommentAdapter commentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected BasePresenter createPresenter() {
        return commentPresenter;
    }

    @Override
    protected void prepareData(Intent intent) {
        storyId = getIntent().getIntExtra(Constant.STORY_ID,0);
        commentCount = getIntent().getIntExtra(Constant.COMMENT_COUNT,0);
        shortCommentCount = getIntent().getIntExtra(Constant.SHORT_COMMENT_COUNT,0);
        longCommentCount = getIntent().getIntExtra(Constant.LONG_COMMENT_COUNT,0);
    }

    @Override
    protected void initView() {
        initBackToolBar(commentCount+" 条点评");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvComment.setLayoutManager(layoutManager);
        rvComment.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvComment.setHasFixedSize(true);
        commentAdapter = new CommentAdapter(new ArrayList<CommentBean.Comments>(0),this,commentPresenter,storyId);
        commentAdapter.setLayoutManager(layoutManager);
        rvComment.setAdapter(commentAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        commentPresenter.getLongComment(storyId);
    }

    @Override
    protected void setListener() {
        rvComment.addOnScrollListener(new LoadMoreScrollListener() {
            @Override
            protected void onLoadMore() {
                if (loadedAllLongComment){
                    if (noMoreComment){
                        ToastShow.show("没有更多评论了");
                        return;
                    }
                    //没加载过短评 或正在加载时返回
                    if (!commentAdapter.isShorExpand() || commentAdapter.getFooterState()
                            == BaseStoryRecycleAdapter.STATE_LOADING)
                        return;
                    commentPresenter.getMoreShortComment(storyId,lastCommentId);
                }else{
                    commentPresenter.getMoreLongComment(storyId,lastCommentId);
                }
            }
        });
    }

    @Override
    public void setRefreshing(boolean b) {
        if (b){
            showProgressDialog("努力加载中");
        }else {
            dismissProgressDialog();
        }

    }

    @Override
    public void setlastCommenttId(int lastId) {
        lastCommentId = lastId;
    }

    @Override
    public void showLongComment(CommentBean comment) {
        commentAdapter.clearData();
        List<CommentBean.Comments> commentList = comment.getComments();
        commentAdapter.addTitle(longCommentCount+getString(R.string.long_comment_title)); //长评数
        if (commentList == null || commentList.size() == 0){
            commentAdapter.addEmptyView(); //没有长评
            loadedAllLongComment = true;
        }else{
            setlastCommenttId(commentList.get(commentList.size()-1).getId());
            commentAdapter.addDatas(comment.getComments());
            if (commentAdapter.getItemCount() >= longCommentCount+1){
                loadedAllLongComment = true;
            }else {
                loadedAllLongComment = false;
            }

        }
        commentAdapter.addTitle(shortCommentCount+ getString(R.string.short_comment_title)); //短评数
    }

    @Override
    public void showMoreLongComment(List<CommentBean.Comments> commentList) {
        if (commentList == null || commentList.size() == 0){
            loadedAllLongComment = true;
        }else{
            setlastCommenttId(commentList.get(commentList.size()-1).getId());
            commentAdapter.addLongComments(commentList);
            if (commentAdapter.getItemCount() >= longCommentCount+2){
                loadedAllLongComment = true;
            }else {
                loadedAllLongComment = false;
            }
        }

    }


    @Override
    public void showShortComment(CommentBean comment) {
        int itemCount = commentAdapter.getItemCount();
        rvComment.smoothScrollToPosition(itemCount);
        List<CommentBean.Comments> commentsList = comment.getComments();
        if (commentsList == null || commentsList.size() == 0){
            ToastShow.show("暂无短评");
        }else{
            commentAdapter.addDatas(comment.getComments());
            setlastCommenttId(commentsList.get(commentsList.size() -1).getId());
        }
    }

    @Override
    public void showMoreShorComment(List<CommentBean.Comments> commentList) {
        commentAdapter.addDatas(commentList);
    }

    @Override
    public void showgetCommentFail() {
        ToastShow.show("请求数据失败");
    }

    @Override
    public void setLoadMoreViewShow(boolean show) {
//        if (show){
//            commentAdapter.addFooter();
//            rvComment.smoothScrollToPosition(commentAdapter.getItemCount());
//        }else{
//            commentAdapter.removeFooter();
//        }
    }

    @Override
    public void showNoMoreComment() {
        ToastShow.show("没有更多评论了");
        noMoreComment = true;
    }
}
