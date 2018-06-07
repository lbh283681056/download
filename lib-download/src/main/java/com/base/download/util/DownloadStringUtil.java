package com.base.download.util;

/**
 * 下载字符串工具类
 * 
 * 作者 LinXin
 * @Date 2014年7月31日 上午11:19:11
 */
public class DownloadStringUtil {

	/**
	 * 从路径中获取 文件名
	 * 
	 * 属性 path
	 * 属性 hasSuffix
	 *        是否包括后缀
	 * @return
	 */
	public static String getFileName(String path, boolean hasSuffix) {
		if (null == path || -1 == path.lastIndexOf("/") || -1 == path.lastIndexOf("."))
			return null;
		if (!hasSuffix)
			return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		else
			return path.substring(path.lastIndexOf("/") + 1);
	}

}
