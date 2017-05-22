package com.mreturn.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.model.StoryListBean;
import com.mreturn.zhihudaily.utils.ImageLoader;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class StoriesAdapter extends BaseRecycleAdapter<StoryListBean.StoriesBean> {

    private Context context;

    public StoriesAdapter(List<StoryListBean.StoriesBean> datas, Context context) {
        super(datas);
        this.context = context;
    }

    public void addTitle(String title) {
        datas.add(new StoryListBean.StoriesBean(title, StoryListBean.StoriesBean.TYPE_TITLE));
        notifyDataSetChanged();

    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_story_item;
    }

    @Override
    protected int getTitleViewLayout() {
        return R.layout.item_story_title;
    }

    @Override
    protected int getOtherViewLayout() {
        return R.layout.item_story_no_img;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, StoryListBean.StoriesBean story) {
        if (story != null) {
            switch (getItemViewType(position)) {
                case TYPE_TITLE:
                    holder.setText(R.id.tv_title, story.getTitle());
                    break;
                case TYPE_ITEM:
                    TextView tvTitle = holder.getView(R.id.tv_title);
                    tvTitle.setTextColor(ContextCompat.getColor(context, story.isRead() ?
                            R.color.textReaded : android.R.color.black));
                    tvTitle.setText(story.getTitle());
                    ImageLoader.display(context, (ImageView) holder.getView(R.id.iv_thumb),
                            story.getImages().get(0));
                    holder.getView(R.id.iv_multipic).setVisibility(story.isMultipic() ?
                            View.VISIBLE : View.GONE);
                    break;
                case TYPE_NO_IMG_ITEM:
                    TextView tvTitle1 = holder.getView(R.id.tv_title);
                    tvTitle1.setTextColor(ContextCompat.getColor(context, story.isRead() ?
                            R.color.textReaded : android.R.color.black));
                    tvTitle1.setText(story.getTitle());
                    break;
                default:
                    break;
            }
        }
    }
}
