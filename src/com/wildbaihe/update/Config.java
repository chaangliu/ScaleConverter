package com.wildbaihe.update;

import com.wildbaihe.R;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class Config {
	private static final String TAG = "Config";
	
	public static final String UPDATE_SERVER = "http://larry.us1.2lai.org/";
	public static final String UPDATE_APKNAME = "ScaleConverter.apk";
	public static final String UPDATE_VERJSON = "ver.json";
	public static final String UPDATE_SAVENAME = "ScaleConverter.apk";
	
	
	public static int getVerCode(Context context) {//得到当前版本号
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					"com.wildbaihe", 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}
	
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.wildbaihe", 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;	

	}
	
	public static String getAppName(Context context) {
		String verName = context.getResources()
		.getText(R.string.app_name).toString();
		return verName;
	}
}
