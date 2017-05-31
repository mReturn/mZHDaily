package com.mreturn.zhihudaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mreturn.zhihudaily.Presenter.CommentPresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.model.CommentBean;
import com.mreturn.zhihudaily.utils.ImageLoader;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/31.
 */

public class CommentAdapter extends BaseStoryRecycleAdapter<CommentBean.Comments> {

    CommentPresenter commentPresenter;
    int storyId;
    boolean isShorExpand ;
    int shortCountItemPos ;

    public CommentAdapter(List<CommentBean.Comments> datas, Context context, CommentPresenter presenter, int storyId) {
        super(datas, context);
        this.commentPresenter = presenter;
        this.storyId = storyId;
    }

    public boolean isShorExpand() {
        return isShorExpand;
    }

    //    public CommentAdapter(List<CommentBean.Comments> datas, Context context) {
//        super(datas, context);
//    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_comment;
    }

    @Override
    protected View getTitleView(ViewGroup parent) {
        return  LayoutInflater.from(mContext).inflate(R.layout.item_comment_title, parent, false);
    }


    @Override
    protected int getNoImgItemLayout() {
        return 0;
    }

    @Override
    protected int getDefaultItemLayout() {
        return R.layout.item_comment_empty;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, CommentBean.Comments comments) {
        if (comments != null){
            switch (getItemViewType(position)){
                case TYPE_TITLE:
                    holder.setText(R.id.tv_comment_count,comments.getContent());
                    final ImageView ivShort = holder.getImageView(R.id.iv_short);
                    if (comments.getContent().contains("短评")){
                        shortCountItemPos = position;
                        ivShort.setVisibility(View.VISIBLE);
                        holder.setClickListener(R.id.rootview, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isShorExpand){
                                    ivShort.setImageResource(R.drawable.comment_icon_fold);
                                }else{
                                    commentPresenter.getShortComment(storyId);
                                    ivShort.setImageResource(R.drawable.comment_icon_fold1);
                                }
                                isShorExpand = !isShorExpand;
                            }
                        });
                    }else{
                        ivShort.setVisibility(View.GONE);
                    }
                    break;
                case TYPE_ITEM:
                    ImageLoader.displayCircleImg(holder.getImageView(R.id.iv_avatar),comments.getAvatar());
                    holder.setText(R.id.tv_name,comments.getAuthor());
                    holder.setText(R.id.tv_content,comments.getContent());
                    holder.setText(R.id.tv_date,comments.getTime());
                    holder.setText(R.id.tv_praise_count,comments.getLikes()+"");
                    break;
                default:
                    break;
            }
        }
    }

    public void addTitle (String title){
        mDatas.add(new CommentBean.Comments(title,TYPE_TITLE));
        notifyDataSetChanged();
    }

    public void addEmptyView(){
        mDatas.add(new CommentBean.Comments(TYPE_EMPTY_VIEW));
        notifyDataSetChanged();
    }

    public void addLongComments(List<CommentBean.Comments> commentsList){
        if (shortCountItemPos>0){
            mDatas.addAll(shortCountItemPos,commentsList);
            notifyDataSetChanged();
        }

    }

}
