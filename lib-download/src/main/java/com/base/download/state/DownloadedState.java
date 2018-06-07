package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 下载结束状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午3:48:59
 */
public class DownloadedState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOADED;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadedClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadedDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadedState() {

	}

	public DownloadedState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadedState> CREATOR = new Creator<DownloadedState>() {

		@Override
		public DownloadedState[] newArray(int size) {
			return new DownloadedState[size];
		}

		@Override
		public DownloadedState createFromParcel(Parcel source) {
			return new DownloadedState(source);
		}
	};

}
