package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/***
 * 取消下载中状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午3:46:18
 */
public class DownloadCancelingState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_CANCELING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadCancelingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadCancelingDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadCancelingState() {

	}

	public DownloadCancelingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadCancelingState> CREATOR = new Creator<DownloadCancelingState>() {

		@Override
		public DownloadCancelingState[] newArray(int size) {
			return new DownloadCancelingState[size];
		}

		@Override
		public DownloadCancelingState createFromParcel(Parcel source) {
			return new DownloadCancelingState(source);
		}
	};

}
