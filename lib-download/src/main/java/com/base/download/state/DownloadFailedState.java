package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 下载失败状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午3:57:08
 */
public class DownloadFailedState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_FAILED;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadFailedClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadFailedDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadFailedState() {

	}

	public DownloadFailedState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadFailedState> CREATOR = new Creator<DownloadFailedState>() {

		@Override
		public DownloadFailedState[] newArray(int size) {
			return new DownloadFailedState[size];
		}

		@Override
		public DownloadFailedState createFromParcel(Parcel source) {
			return new DownloadFailedState(source);
		}
	};

}
