package com.base.download.util;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.base.download.abst.ADownloadWorker;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 下载线程工具
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午4:41:22
 */
public class DownloadThreadUtil {

	/**
	 * 默认下载线程数
	 */
	private final static int DEFAULT_DOWNLOAD_SIZE = 5;

	/**
	 * 下载线程池
	 */
	private static ThreadPoolExecutor mDownloadThreadPoolExecutor = new ThreadPoolExecutor(DEFAULT_DOWNLOAD_SIZE, DEFAULT_DOWNLOAD_SIZE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());;

	/**
	 * 处理下载操作
	 * 
	 * 属性 command
	 */
	public static void excuteDownload(Runnable command) {
		mDownloadThreadPoolExecutor.execute(command);
	}

	/**
	 * 清除所有下载任务
	 * 
	 * @return
	 */
	public static List<Runnable> clearAllDownloadTask() {
		return mDownloadThreadPoolExecutor.shutdownNow();
	}

	/**
	 * 移除一个等待中的任务
	 * 
	 * 属性 worker
	 * @return
	 */
	public static boolean remove(ADownloadWorker<? extends BaseDownloadInfo> worker) {
		return mDownloadThreadPoolExecutor.remove(worker);
	}
}
