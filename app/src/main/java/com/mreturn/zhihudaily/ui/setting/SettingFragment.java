package com.mreturn.zhihudaily.ui.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;

import com.mreturn.zhihudaily.R;
import com.mreturn.zhihudaily.app.Constant;
import com.mreturn.zhihudaily.utils.CacheUtils;
import com.mreturn.zhihudaily.utils.SpUtils;
import com.mreturn.zhihudaily.utils.ToastShow;

/**
 * Created by mReturn
 * on 2017/6/1.
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        CheckBoxPreference noImg = (CheckBoxPreference) findPreference(getResources().getString(R.string.key_no_img));
        CheckBoxPreference bigFont = (CheckBoxPreference) findPreference(getResources().getString(R.string.key_big_font));
        Preference clearCache = findPreference(getResources().getString(R.string.key_clear_cache));
        Preference notice =findPreference(getResources().getString(R.string.key_notice));

        noImg.setOnPreferenceChangeListener(this);
        bigFont.setOnPreferenceChangeListener(this);

        clearCache.setOnPreferenceClickListener(this);
        notice.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case "clear_cache":
                AlertDialog.Builder cacheBuilder = new AlertDialog.Builder(getActivity());
                cacheBuilder.setTitle("清除缓存")
                        .setMessage("当前缓存: "+ CacheUtils.getTotalCacheSize())
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CacheUtils.cleanAllCache();
                                ToastShow.show("缓存已清除");
                            }
                        }).show();
                break;
            case "notice":
                AlertDialog.Builder noticeBuilder = new AlertDialog.Builder(getActivity());
                noticeBuilder.setTitle("声明")
                        .setMessage("本软件仅作学习之用，如被告知侵权将立即停止使用")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        boolean checked = (boolean) o;
        switch (preference.getKey()){
            case "no_img":
                SpUtils.put(getActivity(), Constant.KEY_NO_LOAD_IMAGE,checked);
                break;
            case "big_font":
                SpUtils.put(getActivity(), Constant.KEY_BIG_FONT,checked);
                break;
            default:
                break;
        }
        return true;
    }
}
