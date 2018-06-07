package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 已暂停状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午4:12:41
 */
public class DownloadPausedState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_PAUSED;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausedClick();

	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausedDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadPausedState() {

	}

	public DownloadPausedState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadPausedState> CREATOR = new Creator<DownloadPausedState>() {

		@Override
		public DownloadPausedState[] newArray(int size) {
			return new DownloadPausedState[size];
		}

		@Override
		public DownloadPausedState createFromParcel(Parcel source) {
			return new DownloadPausedState(source);
		}
	};

}
