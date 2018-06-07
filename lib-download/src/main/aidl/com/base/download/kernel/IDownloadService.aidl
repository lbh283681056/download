package com.base.download.kernel;

interface IDownloadService {

	/**
	 * 添加下载任务
	 * 
	 * 属性 info
	 */
	boolean addNewDownloadTask(in Map info);

	/**
	 * 暂停下载任务
	 * 
	 * 属性 info
	 */
	boolean pauseDownloadTask(in Map info);

	/**
	 * 取消下载任务
	 * 
	 * 属性 info
	 */
	boolean cancelDownloadTask(in Map info);
	
	/**
	* 判断服务是否活动中
	*/
	boolean isServiceAlive();
	
	/**
	 *清除所有下载任务
	 */
	void clearAllDownloadTask();
	
	/**
	 * 获取下载总队列
	 * @return
	 */
	Map getDownloadInfos();
	
	/**
	 * 返回任务数，包括所有状态的
	 * @return
	 */
	int getTaskCount();
	
	/**
     * 获取单个下载任务
     * 属性 identification 下载唯一标识
     */
    Map getDownloadInfo(String url);
    
	/**
	 * 继续下载
	 */
	boolean continueDownload(in Map info);
	

}
