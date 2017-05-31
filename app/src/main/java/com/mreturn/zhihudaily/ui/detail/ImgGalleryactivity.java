package com.mreturn.zhihudaily.ui.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.adapter.ImgGalleryPageAdapter;
import com.mreturn.zhihudaily.api.ZhihuClient;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.FileUtils;
import com.mreturn.zhihudaily.utils.MyLog;
import com.mreturn.zhihudaily.utils.ToastShow;
import com.mreturn.zhihudaily.utils.TransformUtils;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by mReturn
 * on 2017/5/27.
 */

public class ImgGalleryactivity extends BaseToolBarAtivity {
    @BindView(R.id.vp_img)
    ViewPager vpImg;
    @BindView(R.id.tv_page)
    TextView tvPage;

    String imgUrl;
    List<String> imgList;
    String currentImgUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_img_gallery;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initBackToolBar("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_gallery,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save_img:
                downloadImg(currentImgUrl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        imgUrl = getIntent().getStringExtra(Constant.IMG_URL);
        imgList = (List<String>) getIntent().getSerializableExtra(Constant.IMG_URL_LIST);
        vpImg.setAdapter(new ImgGalleryPageAdapter(imgList));
        vpImg.setCurrentItem(imgList.indexOf(imgUrl));
    }

    @Override
    protected void setListener() {
        super.setListener();
        vpImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvPage.setText(String.format("%s/"+imgList.size(),position+1));
                currentImgUrl = imgList.get(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void downloadImg(final String url) {
//        url = "http://img.78dian.com/user/forum/201703/1_1488435170_86662.gif";
        MyLog.e("download ： ",url);
        ZhihuClient.getZhihuApi().downloadFile(url)
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return FileUtils.writeResponseBodyToDisk(responseBody,url);
                    }
                })
                .compose(TransformUtils.<String>defaultSchedulers())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
                        ToastShow.show("下载成功");
                        MyLog.e("download success： ",value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastShow.show("下载失败");
                        MyLog.e("download fail： ",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
