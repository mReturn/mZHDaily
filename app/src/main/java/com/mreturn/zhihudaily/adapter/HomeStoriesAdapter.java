package com.mreturn.zhihudaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.database.ImgDao;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.ui.detail.HomeStoryDetailActivity;
import com.mreturn.zhihudaily.ui.detail.ThemeStoryDetailActivity;
import com.mreturn.zhihudaily.utils.ImageLoader;
import com.mreturn.zhihudaily.utils.NetUtils;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class HomeStoriesAdapter extends BaseStoryRecycleAdapter<StoriesBean> {


    public HomeStoriesAdapter(List<StoriesBean> datas, Context context) {
        super(datas, context);
    }

    public void addTitle(String title) {
        mDatas.add(new StoriesBean(title, StoriesBean.TYPE_TITLE));
        notifyDataSetChanged();

    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.item_story;
    }

    @Override
    protected View getTitleView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_story_title, parent, false);
    }

    @Override
    protected int getNoImgItemLayout() {
        return R.layout.item_story_no_img;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position, StoriesBean story) {
        if (story != null) {
            switch (getItemViewType(position)) {
                case TYPE_TITLE:
                    holder.setText(R.id.tv_title, story.getDate());
                    break;
                case TYPE_ITEM:
                    setRootListener(holder, story);
                    setTitle(holder, story);
                    if (!NetUtils.isNetAvailable(mContext)){
                        ImgDao imgDao = new ImgDao();
                        String imgUri = imgDao.getImgUri(story.getImages().get(0));
                        if (TextUtils.isEmpty(imgUri)){
                            ImageLoader.display(mContext, (ImageView) holder.getView(R.id.iv_thumb),
                                    story.getImages().get(0));
                        }else {
                            ImageLoader.display(mContext, (ImageView) holder.getView(R.id.iv_thumb),imgUri);
                        }
                    }else{
                        ImageLoader.display(mContext, (ImageView) holder.getView(R.id.iv_thumb),
                                story.getImages().get(0));
                    }
                    holder.getView(R.id.iv_multipic).setVisibility(story.isMultipic() ?
                            View.VISIBLE : View.GONE);
                    break;
                case TYPE_NO_IMG_ITEM:
                    setRootListener(holder, story);
                    setTitle(holder, story);
                    break;
                default:
                    break;
            }
        }
    }

    private void setRootListener(final BaseViewHolder holder, final StoriesBean story) {
        holder.setClickListener(R.id.rootview, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (story.getTitle().contains(Constant.NO_IMG)){
                    intent = new Intent(mContext, ThemeStoryDetailActivity.class);
                }else{
                    intent = new Intent(mContext, HomeStoryDetailActivity.class);
                }
                intent.putExtra(Constant.STORY,story);
                mContext.startActivity(intent);
                markRead(holder, story);

            }
        });
    }

}
