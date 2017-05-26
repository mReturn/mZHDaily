package com.mreturn.zhihudaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.ui.detail.ThemeStoryDetailActivity;
import com.mreturn.zhihudaily.utils.ImageLoader;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/23.
 */

public class ThemeAdapter extends BaseStoryRecycleAdapter<StoriesBean> {
    private View mTitleView;

    public ThemeAdapter(List<StoriesBean> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_story;
    }

    @Override
    protected View getTitleView() {
        return mTitleView;
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
                    setRootListener(holder,story);
                    break;
                case TYPE_NO_IMG_ITEM:
                    setRootListener(holder,story);
                    setTitle(holder, story);
                    break;
                default:
                    break;
            }
        }
    }


    public void addTitle(View titleView){
        mTitleView = titleView;
        mDatas.add(new StoriesBean(StoriesBean.TYPE_TITLE));
        notifyDataSetChanged();
    }

    private void setRootListener(final BaseViewHolder holder, final StoriesBean story) {
        holder.setClickListener(R.id.rootview, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ThemeStoryDetailActivity.class);
                intent.putExtra(Constant.STORY,story);
                mContext.startActivity(intent);
                markRead(holder,story);
            }
        });
    }
}
