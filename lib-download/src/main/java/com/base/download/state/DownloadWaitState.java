package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 等待下载状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午4:12:25
 */
public class DownloadWaitState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_WAIT;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadWaitClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadWaitDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadWaitState() {

	}

	public DownloadWaitState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadWaitState> CREATOR = new Creator<DownloadWaitState>() {

		@Override
		public DownloadWaitState[] newArray(int size) {
			return new DownloadWaitState[size];
		}

		@Override
		public DownloadWaitState createFromParcel(Parcel source) {
			return new DownloadWaitState(source);
		}
	};

}
