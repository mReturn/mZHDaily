package com.mreturn.zhihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mreturn.zhihudaily.R;

import java.lang.reflect.ParameterizedType;
import java.util.List;


/**
 * Created by mReturn
 * on 2017/5/22.
 */

public abstract class BaseRecycleAdapter<T extends BaseAdapterBean> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> datas;

    protected View mHeadView;
    private int mFooterState;

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_TITLE = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_FOOTER = 3;
    protected static final int TYPE_NO_IMG_ITEM = 4;

    public static final int STATE_LOADING = -1;
    public static final int STATE_NO_FOOTER = -2;

    public BaseRecycleAdapter(List<T> mDatas) {
        this.datas = mDatas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case TYPE_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getItemViewLayout(), parent, false);
                return new BaseViewHolder(itemView);
            case TYPE_TITLE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getTitleViewLayout(), parent, false);
                return new BaseViewHolder(itemView);
            case TYPE_HEADER:
                return new BaseViewHolder(mHeadView);
            case TYPE_FOOTER:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item, parent, false);
                return new BaseViewHolder(itemView);
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getOtherViewLayout(), parent, false);
                return new BaseViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, position, datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setHeadView(View mHeadView) {
        T headerBean = createBean(TYPE_HEADER);
        datas.add(0, headerBean);
        this.mHeadView = mHeadView;
        notifyItemInserted(0); //插入到最上面
    }


    public void setFooterState(int mFooterState) {
        this.mFooterState = mFooterState;
    }

    //添加footer
    public void addFooter() {
        T footBean = createBean(TYPE_FOOTER);
        datas.add(footBean);
        setFooterState(STATE_LOADING);
        notifyDataSetChanged();
    }

    //移除footer
    public void removeFooter() {
        int lastPos = datas.size() - 1;
        int itemViewType = getItemViewType(lastPos);
        if (itemViewType == TYPE_FOOTER) {
            datas.remove(lastPos);
            setFooterState(STATE_NO_FOOTER);
            notifyDataSetChanged();
        }
    }

    //添加数据
    public void addDatas(List<T> data) {
        datas.addAll(data);
        notifyDataSetChanged();
    }

    //清除数据
    public void clearData() {
        datas.clear();
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
        return datas.get(position).getShowType();
    }

    protected abstract int getItemViewLayout();

    protected abstract int getTitleViewLayout();

    protected abstract int getOtherViewLayout();

    protected abstract void bindData(BaseViewHolder holder, int position, T t);

}
