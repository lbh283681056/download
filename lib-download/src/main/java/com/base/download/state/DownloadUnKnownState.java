package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 未知状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午4:00:07
 */
public class DownloadUnKnownState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_UNKNOWN;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNoneClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNoneDisplay();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadUnKnownState() {

	}

	public DownloadUnKnownState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadUnKnownState> CREATOR = new Creator<DownloadUnKnownState>() {

		@Override
		public DownloadUnKnownState[] newArray(int size) {
			return new DownloadUnKnownState[size];
		}

		@Override
		public DownloadUnKnownState createFromParcel(Parcel source) {
			return new DownloadUnKnownState(source);
		}
	};

}
