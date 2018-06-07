package com.base.download.kernel;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

import com.base.download.abst.ADownloadWorker;
import com.base.download.util.DownloadThreadUtil;

/**
 * 下载管理
 * 
 * 版本
 * 时间 2012-9-23 下午02:42:10
 */
public class DownloadWorkerSupervisor {

	private static final Hashtable<String, ADownloadWorker<? extends BaseDownloadInfo>> works = new Hashtable<String, ADownloadWorker<? extends BaseDownloadInfo>>();

	/**
	 * 添加一个下载任务
	 * 
	 * 属性 worker
	 * @return
	 */
	public static boolean add(String id, ADownloadWorker<? extends BaseDownloadInfo> worker) {
		if (!works.containsKey(id)) {
			works.put(id, worker);
			return true;
		} else {
			Log.e("http", "download mission has been in the queue -> " + id);
			return false;
		}
	}

	/**
	 * 移除指定下载对象
	 * 
	 * @return
	 */
	public static boolean remove(String id) {
		return works.remove(id) != null;
	}

	/**
	 * 判断指定下载对象是否在下载中
	 * 
	 * 属性 url
	 * @return
	 */
	public static boolean isDownloading(String id) {
		return works.containsKey(id);
	}

	/**
	 * 获取指定下载对象
	 */
	public static ADownloadWorker<? extends BaseDownloadInfo> getDownloadWorkerById(String id) {
		return works.get(id);
	}

	/**
	 * 获取指定下载对象信息
	 * 
	 * 属性 url
	 * @return
	 */
	public static BaseDownloadInfo getDownloadInfo(String id) {
		return getDownloadWorkerById(id).getBaseDownloadInfo();
	}

	/**
	 * 获取所有下载对象
	 * 
	 * @return
	 */
	public static Map<String, BaseDownloadInfo> getDownloadInfos() {
		Map<String, BaseDownloadInfo> map = new HashMap<String, BaseDownloadInfo>();
		for (Entry<String, ADownloadWorker<? extends BaseDownloadInfo>> entry : works.entrySet()) {
			map.put(entry.getKey(), entry.getValue().getBaseDownloadInfo());
		}
		return map;
	}

	/**
	 * 清除所有下载任务
	 */
	public static void clearAllDownloadTask() {
		DownloadThreadUtil.clearAllDownloadTask();
	}

	/**
	 * 获取下载任务总数
	 */
	public static int getTaskCount() {
		return works.size();
	}
}
