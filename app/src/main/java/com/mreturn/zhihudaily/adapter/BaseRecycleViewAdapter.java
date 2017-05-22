package com.mreturn.zhihudaily.adapter;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mreturn.zhihudaily.R;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public abstract class BaseRecycleViewAdapter<T extends BaseAdapterBean> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> datas;
    protected View mHeadView;

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_TITLE = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_FOOTER = 3;
    protected static final int TYPE_NO_IMG_TITLE = 4;
    protected static final int TYPE_EMPTY_VIEW = 5;

    private int mFooterState;
    public static final int STATE_LOADING = -1;
    public static final int STATE_NO_FOOTER = -2;

    public BaseRecycleViewAdapter(List<T> datas) {
        this.datas = datas;
    }

    public void setHeadView(View mHeadView) {
        T headerBean = creatBean(TYPE_HEADER);
        datas.add(0, headerBean);
        this.mHeadView = mHeadView;
        notifyItemInserted(0); //插入到最上面
    }

    public int getFooterState() {
        return mFooterState;
    }

    public void setFooterState(int mFooterState) {
        this.mFooterState = mFooterState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case TYPE_ITEM:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
                return getItemViewHolder(itemView);
            case TYPE_TITLE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(getTitlLayout(), parent, false);
                return getTitleViewHolder(itemView);
            case TYPE_HEADER:
                return new HeadViewHolder(mHeadView);
            case TYPE_FOOTER:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item, parent, false);
                return new FooterViewHolder(itemView);
            default:
                break;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }

    public T getItem(int pos) {
        return datas.get(pos);
    }

    //添加footer
    public void addFooter() {
        T footBean = creatBean(TYPE_FOOTER);
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
    private T creatBean(int type) {
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


    protected abstract int getItemType(int pos);

    protected abstract int getItemLayout();

    protected abstract RecyclerView.ViewHolder getItemViewHolder(View itemView);

    protected abstract int getTitlLayout();

    protected abstract RecyclerView.ViewHolder getTitleViewHolder(View itemView);


    public class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_loading)
        TextView tvLoading;
        @BindView(R.id.progressBar)
        ContentLoadingProgressBar pb;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
