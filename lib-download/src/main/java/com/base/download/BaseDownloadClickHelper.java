package com.base.download;

import android.content.Context;

import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadView;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 基本帮助类
 * 
 * 作者 LinXin
 * @Date 2014年8月1日 上午10:36:44
 */
public abstract class BaseDownloadClickHelper<T extends BaseDownloadInfo> implements IDownloadClickHelper<T> {

	private final static String TAG = BaseDownloadClickHelper.class.getName();

	public T mDownloadInfo;
	/**
	 * 上下文
	 */
	public Context mContext;

	/**
	 * 下载视图
	 */
	private IDownloadView<T> mDownloadView;

	public BaseDownloadClickHelper(IDownloadView<T> downloadView) {
		mDownloadView = downloadView;
		mContext = mDownloadView.getContext();
	}

	@Override
	public void onDownloadConnectingClick() {
		BaseDownloadOperate.pauseDownloadTask(mContext, mDownloadInfo);
	}

	@Override
	public void onDownloadNewClick() {
		BaseDownloadOperate.addNewDownloadTask(mContext, mDownloadInfo);
	}

	@Override
	public void onDownloadWaitClick() {
		BaseDownloadOperate.pauseDownloadTask(mContext, mDownloadInfo);
	}

	@Override
	public void onDownloadingClick() {
		BaseDownloadOperate.pauseDownloadTask(mContext, mDownloadInfo);
	}

	@Override
	public void onDownloadPausingClick() {
		//暂停中不做事
	}

	@Override
	public void onDownloadPausedClick() {
		BaseDownloadOperate.addNewDownloadTask(mContext, mDownloadInfo);
	}

	@Override
	public void onDownloadCancelingClick() {
		//取消中不做事
	}

	@Override
	public void onDownloadFailedClick() {
		BaseDownloadOperate.addNewDownloadTask(mContext, mDownloadInfo);

	}

	@Override
	public void onDownloadNoneClick() {
		BaseDownloadOperate.addNewDownloadTask(mContext, mDownloadInfo);
	}

	public T getDownloadInfo() {
		return mDownloadInfo;
	}

	public void setDownloadInfo(T downloadInfo) {
		this.mDownloadInfo = downloadInfo;
	}

}
