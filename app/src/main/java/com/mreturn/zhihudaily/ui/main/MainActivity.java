package com.mreturn.zhihudaily.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mreturn.zhihudaily.Presenter.BasePresenter;
import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.ui.BaseToolBarAtivity;
import com.mreturn.zhihudaily.utils.SpUtils;
import com.mreturn.zhihudaily.utils.ToastShow;

import butterknife.BindView;

public class MainActivity extends BaseToolBarAtivity implements View.OnClickListener {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    TextView tvCollect;
    TextView tvDownload;
    LinearLayout llUser;

    String currentTag;
    private long exitTime = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initToolbar(R.string.index);
        initDrawerLayout();
    }


    private void initDrawerLayout() {
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        View headView = navView.getHeaderView(0);
        tvCollect = (TextView) headView.findViewById(R.id.tv_collect);
        tvDownload = (TextView) headView.findViewById(R.id.tv_download);
        llUser = (LinearLayout) headView.findViewById(R.id.ll_user);
    }

    @Override
    protected void setListener() {
        llUser.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        tvDownload.setOnClickListener(this);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getOrder();
                ToastShow.show("click");
                switch (item.getItemId()) {
                    case R.id.menu_main_page:
                        ToastShow.show("main");
                        break;
                    default:
                        ToastShow.show("other");
                        break;
                }
                //关闭菜单
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String title = savedInstanceState.getString(Constant.KEY_TITLE, "首页");
            setToolBarTitle(title);
            currentTag = savedInstanceState.getString(Constant.KEY_TAG,Constant.TAG_MAIN);
            intFragment(currentTag,title);
        }
    }

    private void intFragment(String tag, String title) {
        currentTag = tag;
        if (tag.equals(Constant.TAG_MAIN)){

        }else{

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.KEY_TAG,currentTag);
        outState.putString(Constant.KEY_TITLE, getSupportActionBar().getTitle().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if ((Boolean) SpUtils.get(this, Constant.KEY_NIGHT, false)) {
            menu.getItem(1).setTitle(R.string.action_day_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_notification:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_night:
                int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (uiMode) {
                    case Configuration.UI_MODE_NIGHT_NO: //当前日间
                        SpUtils.put(this, Constant.KEY_NIGHT, true);
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES: //当前夜间
                        SpUtils.put(this, Constant.KEY_NIGHT, false);
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.action_setting:
                ToastShow.show("setting");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000){
                ToastShow.show("再次点击退出");
                exitTime = System.currentTimeMillis();
            }else{
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        ToastShow.show("click");
    }
}
