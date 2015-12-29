package com.guc.bureau2.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 
 * @author Administrator
 * 
 */

public class ScreenUtil {

	/**
	 * 
	 * @param reality_scrren_width手机屏幕宽px
	 * @param density手机屏幕像素密度
	 * @param table_width表格的总宽度dp
	 */

	public static int DpToPx(int x, float density) {

		return (int) (x * density);

	}

	/**
	 * 
	 * @param default_view_width
	 *            单位dp
	 * @param result返回值的单位为px
	 * @return
	 */
	public static int getViewWidth(int view_width, int table_width,
			int reality_scrren_width, float density) {// 返回实际view的宽度值，返回值的单位为px
		int result;
		// 将default_view_width转化为像素
		int width = (int) (view_width * density);

		float a = (table_width * density) / reality_scrren_width;

		float k = reality_scrren_width / (table_width * density);

		if (a > 1 || a == 1) {
			result = width;
			// Log.i(FirstActivity.TAG, "result" + result);
		} else {
			result = (int) (k * width);
			// Log.i(FirstActivity.TAG, "result" + result);
		}
		return result;
	}

	public static float getDensity(Activity activity) {
		// 测量屏幕尺寸
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// int screen_width = metrics.widthPixels;
		// int screen_height = metrics.heightPixels;
		float density = metrics.density;// 屏幕密度（0.75 / 1.0 / 1.5）
		// int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		return density;
	}

	public static int getScreenWidth(Activity activity) {
		// 测量屏幕尺寸
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screen_width = metrics.widthPixels;
		// int screen_height = metrics.heightPixels;
		// float density = metrics.density;// 屏幕密度（0.75 / 1.0 / 1.5）
		// int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		return screen_width;
	}

	public static int getScreenHeight(Activity activity) {
		// 测量屏幕尺寸
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// int screen_width = metrics.widthPixels;
		int screen_height = metrics.heightPixels;
		// float density = metrics.density;// 屏幕密度（0.75 / 1.0 / 1.5）
		// int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		return screen_height;
	}

	public static int getScreenDensityDpi(Activity activity) {
		// 测量屏幕尺寸
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// int screen_width = metrics.widthPixels;
		// int screen_height = metrics.heightPixels;
		// float density = metrics.density;// 屏幕密度（0.75 / 1.0 / 1.5）
		int densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
		return densityDpi;
	}
}
