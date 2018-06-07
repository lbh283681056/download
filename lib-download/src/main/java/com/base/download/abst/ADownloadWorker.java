package com.base.download.abst;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.base.download.kernel.BaseDownloadInfo;
import com.base.download.kernel.DownloadWorkerSupervisor;
import com.base.download.util.DownloadHttpUtil;
import com.base.download.util.DownloadThreadUtil;

/**
 * 通用下载抽象类，可支持断点续传，自动连接 <br>
 * 下载成功后的文件路径可通过回调函数{@link #onDownloadCompleted(String,String)}获得
 * 支持自定义下载对象，但自定义下载对象的构造函数必须为XXXX(Context ctx,BaseDownloadInfo info);
 *
 * 作者 pdw
 * 版本 1.0
 * 时间 2012-7-17 下午05:02:05
 */
public abstract class ADownloadWorker<T extends BaseDownloadInfo> implements Runnable {

	/**
	 * 下载对象
	 */
	protected final T mDownloadInfo;
	/**
	 * 下载唯一标识符
	 */
	private final String identification;
	/**
	 * 下载url
	 */
	private String url;
	/**
	 * 下载文件的保存路径
	 */
	private String saveDir;

	/**
	 * 指定的下载文件名
	 */
	private String saveName;

	/**
	 * 下载完成后的文件全路径，eg. /sdcard/pandahome2/XXXX.apt
	 */
	private String saveFile;

	/**
	 * 下载百分比
	 */
	private int progress;

	private long totalSize;

	/**
	 * 下载临时文件后缀
	 */
	public static final String POSTFIX_FILE_NAME = ".temp";

	/**
	 * @see {@link HttpConstants#HTTP_REQUEST_PAUSE},
	 *      {@link HttpConstants#HTTP_REQUEST_CANCLE}
	 */
	private int requestType = DownloadHttpUtil.HTTP_REQUEST_NONE;

	private volatile boolean running = false;

	/**
	 * 构造下载线程 由此构造的下载线程将统一由{@link DownloadWorkerSupervisor}管理
	 * 
	 * 属性 url
	 * 属性 saveDir
	 *        以"/"结束，如"/sdcard/PandaHome3/Theme/"
	 * 属性 saveName
	 *        指定下载文件名,不包含路径，不能为空，如：com.nd.ui.apk
	 * 属性 tipName
	 *        提示名称
	 */
	public ADownloadWorker(T info) {
		this.url = DownloadHttpUtil.utf8URLencode(info.getUrl());
		this.saveDir = info.getSaveDir();
		this.saveName = info.getSaveName();
		this.identification = info.getIdentification();
//		this.identification = info.getUrl();
		mDownloadInfo = info;
		maybeInitDir(saveDir);
	}

	protected void maybeInitDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		saveFile = new File(this.saveDir, saveName).getAbsolutePath();
	}

	@Override
	public void run() {
		download();
	}

	private void download() {
		running = true;
		int retryCount = 0;
		boolean shouldConn = true;
		byte[] buf = new byte[DownloadHttpUtil.BUFFER_SIZE];
		int size = 0; // read byte size per io
		long currentSize; // current size of file
		totalSize = 0; // total size of file
		File file = null;
		String tempFile;

		RandomAccessFile accessFile = null;

		InputStream in = null;
		HttpURLConnection httpConn = null;

		if (saveFile != null) {
			// 下载过程先生成临时文件
			tempFile = saveFile + POSTFIX_FILE_NAME;
		} else {
		
			onDownloadFailed(identification);
			return;
		}
		
		try {
			file = new File(saveFile);
			if (file.exists()) {
				onDownloadCompleted(identification, saveFile, file.length());
				return;
			}
			file = new File(tempFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			accessFile = new RandomAccessFile(tempFile, "rw");
			currentSize = file.length();
			onDownloadConnecting(identification, currentSize);
		} catch (Exception ex) {
			ex.printStackTrace();
			onDownloadFailed(identification);
			try {
				if (accessFile != null) {
					accessFile.close();
				}
			} catch (Exception x) {
				x.printStackTrace();
			}
			return;
		}
		
		/**
		 * 保存的文件名通过构造器传入，不需要处理302跳转 String url =
		 * HttpCommon.getRedirectionURL(this.url);
		 * 
		 * if (url == null) { Log.e(Global.TAG, "url is illegal ->"+this.url);
		 * onDownloadFailedWrap(identification,this.url); return; }
		 */
		while (shouldConn && retryCount < DownloadHttpUtil.MAX_REQUEST_RETRY_COUNTS) {
			if (!running || Thread.interrupted()) {
				if (requestType == DownloadHttpUtil.HTTP_REQUEST_PAUSE) {
					onDownloadPaused(identification);
				} else if (requestType == DownloadHttpUtil.HTTP_REQUEST_CANCLE) {
					if (file.exists())
						file.delete();
					onDownloadCanceled(identification);
				}

				return;
			}
			try {
				httpConn = getConnection(this.url);
				String sProperty = "bytes=" + currentSize + "-";
				// set break point
				httpConn.setRequestProperty("Range", sProperty);
				httpConn.connect();
				totalSize = httpConn.getContentLength();

				if (totalSize == -1) {
					onDownloadFailed(identification);
					return;
				}

				if (totalSize == currentSize) { // there is a trap here
					onDownloadCompleted(identification, saveFile, totalSize);
					break;
				} else {
					totalSize += currentSize;
				}

				// onBeginDownloadWrap(identification,this.url, currentSize,
				// (int) (currentSize * 100 / totalSize));
				onDownloadWorking(identification, totalSize, currentSize, (int) (currentSize * 100 / totalSize));
				accessFile.seek(currentSize);
				in = httpConn.getInputStream();
				// begin to download
				long lastTime = System.currentTimeMillis();
				while ((size = in.read(buf)) != -1) {
					if (!running || Thread.interrupted()) {
						if (requestType == DownloadHttpUtil.HTTP_REQUEST_PAUSE) {
							onDownloadPaused(identification);
						} else if (requestType == DownloadHttpUtil.HTTP_REQUEST_CANCLE) {
							if (file.exists())
								file.delete();
							onDownloadCanceled(identification);
						}
						return;
					}
					accessFile.write(buf, 0, size);
					currentSize += size;
					if (System.currentTimeMillis() - lastTime >= 1000 || currentSize == totalSize) {
						progress = (int) (currentSize * 100 / totalSize);
						onDownloadWorking(identification, totalSize, currentSize, progress);
						lastTime = System.currentTimeMillis();
					}
				}
				renameFile(file, saveFile);
				onDownloadCompleted(identification, saveFile, totalSize);
				shouldConn = false;
			} catch (Exception ex) {
				ex.printStackTrace();
				shouldConn = true;
				retryCount++;
				try {
					Thread.sleep(DownloadHttpUtil.RETRY_SLEEP_TIME); // sleep 2
					// seconds
				} catch (Exception e) {
					e.printStackTrace();
				}
//				if (retryCount == DownloadHttpUtil.MAX_REQUEST_RETRY_COUNTS) {
					onDownloadFailed(identification);
//				}
			} finally {
				try {
					if (in != null)
						in.close();

					if (httpConn != null)
						httpConn.disconnect();
					if (accessFile != null) {
						accessFile.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Thread.yield();
			running = false;
		}
	}

	private void renameFile(File from, String to) {
		File toFile = new File(to);
		if (!toFile.exists())
			from.renameTo(toFile);
		toFile = null;
	}

	/**
	 * </p>取消下载</p> </p>需实现者自己管理下载线程的托管，需调用
	 * {@link DownloadWorkerSupervisor#remove(String)}移除当前线程的托管</p>
	 * 
	 * 属性 identification
	 *        下载唯一标识
	 * 属性 url
	 * 属性 requestType
	 *        {@link HttpConstants#HTTP_REQUEST_PAUSE} or
	 *        {@link HttpConstants#HTTP_REQUEST_CANCLE}
	 */
	protected abstract void onDownloadCanceled(String identification);

	/**
	 * </p>暂停下载</p> </p>需实现者自己管理下载线程的托管，需调用
	 * {@link DownloadWorkerSupervisor#remove(String)}移除当前线程的托管</p>
	 * 
	 * 属性 identification
	 *        下载唯一标识
	 * 属性 url
	 * 属性 requestType
	 *        {@link HttpConstants#HTTP_REQUEST_PAUSE} or
	 *        {@link HttpConstants#HTTP_REQUEST_CANCLE}
	 */
	protected abstract void onDownloadPaused(String identification);

	/**
	 * <p>
	 * 通知下载进度
	 * </p>
	 * 
	 * 属性 identification
	 *        下载唯一标识
	 * 属性 url
	 * 属性 totalSize
	 *        总字节数
	 * 属性 downloadSize
	 *        已下载字节数
	 * 属性 progress
	 *        百分比数字，如 80,90,100
	 */
	protected abstract void onDownloadWorking(String identification, long totalSize, long downloadSize, int progress);

	/**
	 * <p>
	 * 下载完成
	 * </p>
	 * </p>需实现者自己管理下载线程的托管，需调用{@link DownloadWorkerSupervisor#remove(String)}
	 * 移除当前线程的托管</p> 创建于 2012-7-18 上午09:09:09
	 * 
	 * 属性 identification
	 *        下载唯一标识
	 * 属性 url
	 * 属性 file
	 *        下载成功后的文件路径，绝对路径
	 * 属性 totalSize
	 *        文件大小
	 */
	protected abstract void onDownloadCompleted(String identification, String file, long totalSize);

	/**
	 * <p>
	 * 连接中
	 * </p>
	 * 创建于 2012-7-18 上午09:09:35
	 * 
	 * 属性 identification
	 *        下载唯一标识
	 * 属性 url
	 * 属性 downloadSize
	 *        已下载大小
	 * 属性 progress
	 *        进度
	 */
	protected abstract void onDownloadConnecting(String identification, long downloadSize);

	/***
	 * 等待中
	 * 
	 * 属性 url
	 */
	protected abstract void onDownloadWait(String identification);

	/***
	 * 暂停中
	 * 
	 * 属性 url
	 */
	protected abstract void onDownloadPausing(String identification);

	/***
	 * 取消中
	 * 
	 * 属性 url
	 */
	protected abstract void onDownloadCanceling(String identification);

	/**
	 * <p>
	 * 下载失败
	 * </p>
	 * </p>需实现者自己管理下载线程的托管，需调用{@link DownloadWorkerSupervisor#remove(String)}
	 * 移除当前线程的托管</p> 创建于 2012-7-18 上午09:09:49
	 * 
	 * 属性 identification
	 *        下载唯一标识
	 * 属性 url
	 */
	protected abstract void onDownloadFailed(String identification);

	/**
	 * 删除临时文件，慎用，导致不能断点下载 ,或者下载异常<br>
	 * 启动线程之前调用
	 * <p>
	 * date: 2012-9-21 下午09:06:15
	 */
	public void deleteTempFile() {
		File file = new File(saveFile);
		if (file.exists())
			file.delete();
		if (!DownloadWorkerSupervisor.isDownloading(identification)) {
			try {
				file = new File(saveFile + POSTFIX_FILE_NAME);
				if (file.exists())
					file.delete();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * <p>
	 * 获取文件名
	 * </p>
	 * 
	 * <p>
	 * date: 2012-7-18 上午09:15:24
	 * 
	 * 属性 url
	 * @return
	 */
	public static String getFileName(String url) {
		int lastIndex = url.lastIndexOf('/');
		try {
			return url.substring(lastIndex + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取网络连接
	 * 
	 * 属性 urlString
	 * @return
	 * 异常 Exception
	 */
	private HttpURLConnection getConnection(String urlStr) throws Exception {
		URL url = null;
		HttpURLConnection conn = null;
		url = new URL(DownloadHttpUtil.utf8URLencode(urlStr));
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setConnectTimeout(15000);// break if it can't fetch connection
										// after 15 seconds
		conn.setReadTimeout(30000);// break connection if reading no data
									// after 30 seconds
		conn.setRequestProperty(
				"Accept",
				"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		conn.setRequestProperty("Charset", "UTF-8");
		// conn.setRequestProperty("User-Agent",
		// "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		conn.setRequestProperty("Connection", "Keep-Alive");
		return conn;
	}

	/**
	 * <p>
	 * 暂停下载线程，注意：暂停后会销毁掉线程，继续下载需重新构造下载对象。
	 * </p>
	 * <br>
	 * <p>
	 * 调用完后不要再调用{@link #cancle()}否则会引起异常或者不可预知的错误
	 * </p>
	 * <br>
	 * <p>
	 * 暂停下载会保留已下载的文件缓存
	 * </p>
	 * <p>
	 * date: 2012-8-21 下午05:23:02
	 */
	public final boolean pause() {
		onDownloadPausing(identification);
		requestType = DownloadHttpUtil.HTTP_REQUEST_PAUSE;
		if (running) {
			Thread.currentThread().interrupt();
			running = false;
		} else {
			DownloadThreadUtil.remove(this);
			onDownloadPaused(identification);
		}
		return true;
	}

	/**
	 * <p>
	 * 取消下载线程，注意：取消后会销毁掉线程，继续下载需重新构造下载对象。
	 * </p>
	 * <br>
	 * <p>
	 * 调用完后不要再调用{@link #pause()}否则会引起异常或者不可预知的错误
	 * </p>
	 * <br>
	 * <p>
	 * 取消下载会删除已下载的文件缓存
	 * </p>
	 * <p>
	 * date: 2012-8-21 下午05:23:02
	 */
	public final boolean cancle() {
		onDownloadCanceling(identification);
		requestType = DownloadHttpUtil.HTTP_REQUEST_CANCLE;
		if (running) {
			Thread.currentThread().interrupt();
			running = false;
		} else {
			DownloadThreadUtil.remove(this);
			deleteTempFile();
			onDownloadCanceled(identification);
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * 获取下载的进度，百分比
	 * </p>
	 * 
	 * <p>
	 * date: 2012-8-21 下午05:22:39
	 * 
	 * @return
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * 设置指定下载文件名
	 * 
	 * 属性 specifyFileName
	 *        the specifyFileName to set
	 */
	public void setSpecifyFileName(String specifyFileName) {
		this.saveName = specifyFileName;
	}

	public synchronized boolean start() {
		
		if (DownloadWorkerSupervisor.add(identification, this)) {
			onDownloadWait(identification);
			DownloadThreadUtil.excuteDownload(this);
			return true;
		}
		return false;
	}

	public long getTotalSize() {
		return totalSize;
	}

	/**
	 * 获取下载唯一值
	 * 
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 必须在download()之前去替换URL，在download()方法之后替换，出了问题哥不负责!!!!
	 * 
	 * 属性 url
	 */
	public void updateDownloadUrl(String url) {
		this.url = DownloadHttpUtil.utf8URLencode(url);
	}

	/**
	 * 获取下载对象
	 * 
	 * @return
	 */
	public T getBaseDownloadInfo() {
		return mDownloadInfo;
	}
}
