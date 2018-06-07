package com.base.download.util;

import com.base.download.BuildConfig;

/**
 * 调试类
 * 
 * 作者 linxin
 * @Date 2013年11月9日 上午11:26:23
 */
public class Log {

	/**
	 * @see {@link android.util.Log#v(String, String)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(tag, msg);
	}

	/**
	 * @see {@link android.util.Log#v(String, String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 * 属性 tr
	 */
	public static void v(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.v(tag, msg, tr);
	}

	/**
	 * @see {@link android.util.Log#d(String, String)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(tag, msg);
	}

	/**
	 * @see {@link android.util.Log#d(String, String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 * 属性 tr
	 */
	public static void d(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.d(tag, msg, tr);
	}

	/**
	 * @see {@link android.util.Log#e(String, String)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(tag, msg);
	}

	/**
	 * @see {@link android.util.Log#e(String, String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 * 属性 tr
	 */
	public static void e(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.e(tag, msg, tr);
	}

	/**
	 * @see {@link android.util.Log#i(String, String)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(tag, msg);
	}

	/**
	 * @see {@link android.util.Log#i(String, String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 * 属性 tr
	 */
	public static void i(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.i(tag, msg, tr);
	}

	/**
	 * @see {@link android.util.Log#w(String, String)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void w(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(tag, msg);
	}

	/**
	 * @see {@link android.util.Log#w(String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void w(String tag, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(tag, tr);
	}

	/**
	 * @see {@link android.util.Log#w(String, String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 * 属性 tr
	 */
	public static void w(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.w(tag, msg, tr);
	}

	/**
	 * @see {@link android.util.Log#wtf(String, String)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void wtf(String tag, String msg) {
		if (BuildConfig.DEBUG)
			android.util.Log.wtf(tag, msg);
	}

	/**
	 * @see {@link android.util.Log#wtf(String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 */
	public static void wtf(String tag, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.wtf(tag, tr);
	}

	/**
	 * @see {@link android.util.Log#wtf(String, String, Throwable)}
	 * 属性 tag
	 * 属性 msg
	 * 属性 tr
	 */
	public static void wtf(String tag, String msg, Throwable tr) {
		if (BuildConfig.DEBUG)
			android.util.Log.wtf(tag, msg, tr);
	}
}
