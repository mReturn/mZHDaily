package com.mreturn.zhihudaily.Presenter;

import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.model.CommentBean;
import com.mreturn.zhihudaily.ui.comment.CommentView;
import com.mreturn.zhihudaily.utils.TransformUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by mReturn
 * on 2017/5/31.
 */

public class CommentPresenter extends BasePresenter {
    CommentView commentView;

    public CommentPresenter(CommentView commentView) {
        this.commentView = commentView;
    }

    public void getLongComment(int storyId){
        commentView.setRefreshing(true);
        ZhihuClient.getZhihuApi().getLongComments(storyId)
                .compose(TransformUtils.<CommentBean>defaultSchedulers())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentBean value) {
                        commentView.setRefreshing(false);
                        commentView.showLongComment(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        commentView.setRefreshing(false);
                        commentView.showgetCommentFail();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMoreLongComment(int storyId,int lastCommentId){
        commentView.setLoadMoreViewShow(true);
        ZhihuClient.getZhihuApi().getMoreLongComment(storyId,lastCommentId)
                .compose(TransformUtils.<CommentBean>defaultSchedulers())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentBean value) {
                        commentView.setLoadMoreViewShow(false);
                        commentView.showMoreLongComment(value.getComments());
                    }

                    @Override
                    public void onError(Throwable e) {
                        commentView.setLoadMoreViewShow(false);
                        commentView.showgetCommentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getShortComment(int storyId){
        commentView.setRefreshing(true);
        ZhihuClient.getZhihuApi().getShortComments(storyId)
                .compose(TransformUtils.<CommentBean>defaultSchedulers())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentBean value) {
                        commentView.setRefreshing(false);
                        commentView.showShortComment(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        commentView.setRefreshing(false);
                        commentView.showgetCommentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getMoreShortComment(int storyId,int lastCommentId){
        commentView.setLoadMoreViewShow(true);
        ZhihuClient.getZhihuApi().getMoreShortComment(storyId,lastCommentId)
                .compose(TransformUtils.<CommentBean>defaultSchedulers())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentBean value) {
                        commentView.setLoadMoreViewShow(false);
                        List<CommentBean.Comments> commentsList = value.getComments();
                        if (commentsList != null && commentsList.size() > 0){
                            commentView.setlastCommenttId(commentsList.get(commentsList.size()-1).getId());
                            commentView.showMoreShorComment(commentsList);
                        }else{
                            commentView.showNoMoreComment();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        commentView.setLoadMoreViewShow(false);
                        commentView.showgetCommentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
