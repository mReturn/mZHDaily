package com.mreturn.zhihudaily.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.mreturn.zhihudaily.ui.theme.ThemesFragment;
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
    private HomeFragment mHomeFragment;
    private Fragment currentFragment;


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
                int themeId = item.getOrder();
                switch (item.getItemId()) {
                    case R.id.menu_main_page:
                        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN);
                        if (mHomeFragment == null) {
                            mHomeFragment = new HomeFragment();
                        }
                        switchFragment(mHomeFragment, Constant.TAG_MAIN, getResources().getString(R.string.index));
                        break;
                    default:
                        ThemesFragment fragment = (ThemesFragment) getSupportFragmentManager().findFragmentByTag(themeId + "");
                        if (fragment == null) {
                            fragment = ThemesFragment.newInstance(themeId);
                        }
                        switchFragment(fragment, themeId + "", item.getTitle().toString());
                        break;
                }
                //关闭菜单
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String title = savedInstanceState.getString(Constant.KEY_TITLE, "首页");
            setToolBarTitle(title);
            currentTag = savedInstanceState.getString(Constant.KEY_TAG, Constant.TAG_MAIN);
            initFragment(currentTag, title);
        } else {
            initFragment(Constant.TAG_MAIN, getResources().getString(R.string.index));
        }
    }

    private void initFragment(String tag, String title) {
        currentTag = tag;
        if (tag.equals(Constant.TAG_MAIN)) {
            mHomeFragment = (HomeFragment) getSupportFragmentManager()
                    .findFragmentByTag(Constant.TAG_MAIN);
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
                currentFragment = mHomeFragment;
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_main, currentFragment, Constant.TAG_MAIN)
                        .show(currentFragment)
                        .commit();
            }
        } else {
            ThemesFragment themesFragment = ThemesFragment.newInstance(Integer.parseInt(tag));
            currentFragment = themesFragment;
            switchFragment(themesFragment, tag, title);
        }
    }

    private void switchFragment(Fragment fragment, String tag, String title) {
        currentTag = tag;
        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .hide(currentFragment)
                    .add(R.id.fl_main, fragment, tag)
                    .show(fragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .hide(currentFragment)
                    .show(fragment)
                    .commit();
        }
        currentFragment = fragment;
        setToolBarTitle(title);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.KEY_TAG, currentTag);
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
                getSupportFragmentManager().getFragments().clear();
                recreate(); //移除重新创建 避免多个同时存在
                return true;
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
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastShow.show("再次点击退出");
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void onClick(View view) {
        ToastShow.show("click");
    }
}
