package com.mreturn.zhihudaily.ui.comment;

import com.mreturn.zhihudaily.model.CommentBean;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/31.
 */

public interface CommentView {

    void setRefreshing(boolean b);

    void setlastCommenttId(int lastId);

    void showLongComment(CommentBean comment);

    void showMoreLongComment(List<CommentBean.Comments> commentList);

    void showShortComment(CommentBean comment);

    void showMoreShorComment(List<CommentBean.Comments> commentList);

    void showgetCommentFail();

    void setLoadMoreViewShow(boolean show);

    void showNoMoreComment();
}
