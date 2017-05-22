package com.mreturn.zhihudaily.utils;

import android.util.Log;

public class MyLog {
	private static final boolean DEBUG = true;
	private static final String MyLog_sht = "shtLog-->";

	public static void d(String tag, String msg) {
		if (DEBUG) Log.d(MyLog_sht + tag, MyLog_sht + msg);
	}

	public static void e(String tag, String msg) {
		if (DEBUG) Log.e(MyLog_sht + tag, MyLog_sht + msg);
	}
	
	public static void e(String tag, String msg, Throwable e) {
		if (DEBUG) Log.e(MyLog_sht + tag, MyLog_sht + msg, e);
	}
	
	public static void i(String tag, String msg) {
		if (DEBUG) Log.i(MyLog_sht + tag, MyLog_sht + msg);
	}
	
	public static void w(String tag, String msg) {
		if (DEBUG) Log.i(MyLog_sht + tag, MyLog_sht + msg);
	}
}
