package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 连接中状态
 * 
 * 作者 LinXin
 * @Date 2014年8月4日 上午10:26:38
 */
public class DownloadConnectingState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_CONNECTING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadConnectingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadConnectDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadConnectingState() {

	}

	public DownloadConnectingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadConnectingState> CREATOR = new Creator<DownloadConnectingState>() {

		@Override
		public DownloadConnectingState[] newArray(int size) {
			return new DownloadConnectingState[size];
		}

		@Override
		public DownloadConnectingState createFromParcel(Parcel source) {
			return new DownloadConnectingState(source);
		}
	};

}
