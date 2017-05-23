package com.mreturn.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.utils.ImageLoader;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/23.
 */

public class ThemeAdapter extends BaseStoryRecycleAdapter<StoriesBean> {

    public ThemeAdapter(List<StoriesBean> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_story;
    }

    @Override
    protected int getTitleViewLayout() {
        return R.layout.item_theme_title;
    }

    @Override
    protected int getNoImgItemLayout() {
        return R.layout.item_story_no_img;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, StoriesBean story) {
        if (story != null){
            switch (getItemViewType(position)){
                case TYPE_ITEM:
                    setTitle(holder, story);
                    ImageLoader.display(mContext, (ImageView) holder.getView(R.id.iv_thumb),
                            story.getImages().get(0));
                    holder.getView(R.id.iv_multipic).setVisibility(story.isMultipic() ?
                            View.VISIBLE : View.GONE);
                    break;
                case TYPE_NO_IMG_ITEM:
                    setTitle(holder, story);
                    break;
                default:
                    break;
            }
        }
    }

    private void setTitle(BaseViewHolder holder, StoriesBean story) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        tvTitle.setTextColor(ContextCompat.getColor(mContext, story.isRead() ?
                R.color.textReaded : android.R.color.black));
        tvTitle.setText(story.getTitle());
    }

    public void addTitle(){
        mDatas.add(new StoriesBean(StoriesBean.TYPE_TITLE));
        notifyDataSetChanged();
    }
}
