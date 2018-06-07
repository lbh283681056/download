package com.base.download.kernel;

/**
 * TODO linqiang form AppMarketUtil.CommonCallBack
 * 描述:
 * 作者 linqiang(866116)
 * @Since 2013-1-28
 * 属性 <E>
 */
public interface ConnectionCallBack<E> {
	public void invoke(final E... arg);
}
