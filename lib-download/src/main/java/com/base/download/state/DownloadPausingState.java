package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 暂停中状态类
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午4:02:07
 */
public class DownloadPausingState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_PAUSEING;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausingClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadPausingDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadPausingState() {

	}

	public DownloadPausingState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadPausingState> CREATOR = new Creator<DownloadPausingState>() {

		@Override
		public DownloadPausingState[] newArray(int size) {
			return new DownloadPausingState[size];
		}

		@Override
		public DownloadPausingState createFromParcel(Parcel source) {
			return new DownloadPausingState(source);
		}
	};

}
