package com.mreturn.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.database.ReadDao;
import com.mreturn.zhihudaily.model.BaseStoryBean;
import com.mreturn.zhihudaily.model.StoriesBean;
import com.mreturn.zhihudaily.utils.SpUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;


/**
 * Created by mReturn
 * on 2017/5/22.
 */

public abstract class BaseStoryRecycleAdapter<T extends BaseStoryBean> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mDatas;
    protected Context mContext;

    protected View mHeadView;
    private int mFooterState;

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_TITLE = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_FOOTER = 3;
    protected static final int TYPE_NO_IMG_ITEM = 4;
    protected static final int TYPE_EMPTY_VIEW = 5;

    public static final int STATE_LOADING = -1;
    public static final int STATE_NO_FOOTER = -2;

    public BaseStoryRecycleAdapter(List<T> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case TYPE_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getItemViewLayout(), parent, false);
                return new BaseViewHolder(itemView);
            case TYPE_TITLE:
                itemView = getTitleView(parent);
                return new BaseViewHolder(itemView);
            case TYPE_HEADER:
                return new BaseViewHolder(mHeadView);
            case TYPE_FOOTER:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item, parent, false);
                return new BaseViewHolder(itemView);
            case TYPE_NO_IMG_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getNoImgItemLayout(), parent, false);
                return new BaseViewHolder(itemView);
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getDefaultItemLayout(), parent, false);
                return new BaseViewHolder (itemView);
        }
    }

    protected int getDefaultItemLayout() {
        return -1;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, position, mDatas.get(position));
    }

    protected void setTitle(BaseViewHolder holder, StoriesBean story) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        boolean isNight = (Boolean) SpUtils.get(mContext, Constant.KEY_NIGHT,false);
        tvTitle.setTextColor(ContextCompat.getColor(mContext, story.isRead() ?
                R.color.textReaded : isNight? android.R.color.white:android.R.color.black));
        tvTitle.setText(story.getTitle());
    }

    protected void markRead(BaseViewHolder holder, StoriesBean story) {
        holder.getTextView(R.id.tv_title).setTextColor(ContextCompat.getColor(mContext,R.color.textReaded));
        ReadDao readDao = new ReadDao(mContext);
        readDao.save(story.getId());
    }

    public void setHeadView(View mHeadView) {
        T headerBean = createBean(TYPE_HEADER);
        mDatas.add(0, headerBean);
        this.mHeadView = mHeadView;
        notifyItemInserted(0); //插入到最上面
    }

    public int getFooterState() {
        return mFooterState;
    }

    public void setFooterState(int mFooterState) {
        this.mFooterState = mFooterState;
    }

    //添加footer
    public void addFooter() {
        T footBean = createBean(TYPE_FOOTER);
        mDatas.add(footBean);
        setFooterState(STATE_LOADING);
        notifyDataSetChanged();
    }

    //移除footer
    public void removeFooter() {
        int lastPos = mDatas.size() - 1;
        int itemViewType = getItemViewType(lastPos);
        if (itemViewType == TYPE_FOOTER) {
            mDatas.remove(lastPos);
            setFooterState(STATE_NO_FOOTER);
            notifyDataSetChanged();
        }
    }

    //添加数据
    public void addDatas(List<T> data) {
        if (data != null && data.size() > 0) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    //清除数据
    public void clearData() {
        mDatas.clear();
        mHeadView = null;
        notifyDataSetChanged();
    }

    /**
     * 通过反射创建 header/footer  bean
     *
     * @param type head 2  foot 3
     * @return
     */
    private T createBean(int type) {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
            T bean = clazz.newInstance();
            bean.setShowType(type);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getShowType();
    }

    protected abstract int getItemViewLayout();

    protected abstract View getTitleView(ViewGroup parent);

    protected abstract int getNoImgItemLayout();

    protected abstract void bindData(BaseViewHolder holder, int position, T t);

}
