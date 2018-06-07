package com.base.download.intf;

import com.base.download.kernel.BaseDownloadInfo;

/**
 * 显示帮助类
 * 
 * 作者 LinXin
 * @Date 2014年8月4日 上午9:29:25
 */
public interface IDownloadDisplayHelper<T extends BaseDownloadInfo> {

	/**
	 * 设置下载对象
	 * 
	 * 属性 downloadInfo
	 */
	public void setDownloadInfo(T downloadInfo);

	/**
	 * 获取下载对象
	 * 
	 * @return
	 */
	public T getDownloadInfo();

	/**
	 * 注册广播监听
	 * 
	 * 属性 receiver
	 */
	public void registerDownloadReceiver();

	/**
	 * 反注册广播监听
	 * 
	 * 属性 receiver
	 */
	public void unregisterDownloadReceiver();

	/**
	 * 点击下载前的状态
	 * 
	 * 属性 info
	 */
	public void onDownloadNewDisplay();

	/**
	 * 等待状态
	 * 
	 * 属性 info
	 */
	public void onDownloadWaitDisplay();

	/**
	 * 下载中状态
	 * 
	 * 属性 info
	 */
	public void onDownloadingDisplay();

	/**
	 * 已下载状态
	 * 
	 * 属性 info
	 */
	public void onDownloadedDisplay();

	/**
	 * 暂停中状态
	 * 
	 * 属性 info
	 */
	public void onDownloadPausingDisplay();

	/**
	 * 已暂停状态
	 * 
	 * 属性 info
	 */
	public void onDownloadPausedDisplay();

	/**
	 * 取消中状态
	 * 
	 * 属性 info
	 */
	public void onDownloadCancelingDisplay();

	/**
	 * 下载失败状态
	 * 
	 * 属性 info
	 */
	public void onDownloadFailedDisplay();

	/**
	 * 未知状态
	 * 
	 * 属性 info
	 */
	public void onDownloadNoneDisplay();

	/**
	 * 连接中状态
	 * 
	 * 属性 info
	 */
	public void onDownloadConnectDisplay();

}
