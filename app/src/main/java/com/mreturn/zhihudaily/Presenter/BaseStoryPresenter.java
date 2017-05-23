package com.mreturn.zhihudaily.Presenter;

import android.content.Context;

import com.mreturn.zhihudaily.database.ReadDao;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/23.
 */

public class BaseStoryPresenter extends BasePresenter{

    //保存已读文章id
    protected void saveRead(Context context,int id){
        ReadDao readDao = new ReadDao(context);
        readDao.save(id);
    }

    //获取读过的文章id list
    protected List<Integer> getReadList(Context context){
        ReadDao readDao = new ReadDao(context);
        return readDao.getReadList();
    }




//    private Observable getLatestObservable(final Context mContext) {
//        return ZhihuClient.getZhihuApi().getLatest().doOnNext(new Consumer<StoryListBean>() {
//            @Override
//            public void accept(StoryListBean storyListBean) throws Exception {
//                //保存第一页数据
//                StoryDao storyDao = new StoryDao(mContext);
//                storyDao.saveStoryList(storyListBean.getStories(), storyListBean.getTop_stories());
//                SpUtils.put(mContext, Constant.KEY_CURRENT_DATE, storyListBean.getDate());
//                SpUtils.put(mContext, Constant.KEY_HAS_CACHE, true);
//            }
//        }).doOnNext(mConsumer);
//    }

//    @SuppressWarnings("unchecked")
//    public Observable<StoryListBean> getCachedStory(Context mContext) {
//        return Observable.just(mContext).map(new Function<Context, StoryListBean>() {
//            @Override
//            public StoryListBean apply(Context mContext) throws Exception {
//                //获取缓存数据
//                StoryDao storyDao = new StoryDao(mContext);
//                StoryListBean storyList = new StoryListBean();
//                storyList.setStories(storyDao.getStoryList());
//                storyList.setTop_stories(storyDao.getTopStoryList());
//                String date = (String) SpUtils.get(mContext, Constant.KEY_CURRENT_DATE,"");
//                storyList.setDate(date);
//                return storyList;
//            }
//        }).doOnNext(mConsumer);
//    }
}
