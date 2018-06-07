package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 下载中状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午3:57:56
 */
public class DownloadingState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOADING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadingDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadingState() {

	}

	public DownloadingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadingState> CREATOR = new Creator<DownloadingState>() {

		@Override
		public DownloadingState[] newArray(int size) {
			return new DownloadingState[size];
		}

		@Override
		public DownloadingState createFromParcel(Parcel source) {
			return new DownloadingState(source);
		}
	};

}
