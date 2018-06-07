package com.base.download.state;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.download.BaseDownloadStateFactory.State;
import com.base.download.intf.IDownloadClickHelper;
import com.base.download.intf.IDownloadDisplayHelper;
import com.base.download.intf.IDownloadState;
import com.base.download.kernel.BaseDownloadInfo;

/**
 * 新下载状态类,取消下载后，状态也变为这个
 * 
 * 作者 LinXin
 * @Date 2014年7月30日 下午3:33:01
 */
public class DownloadNewState implements IDownloadState {

	@Override
	public State getState() {
		return State.DOWNLOAD_NEW;
	}

	@Override
	public void onClick(IDownloadClickHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNewClick();
	}

	@Override
	public void display(IDownloadDisplayHelper<? extends BaseDownloadInfo> helper) {
		helper.onDownloadNewDisplay();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

	public DownloadNewState() {

	}

	public DownloadNewState(Parcel p) {

	}

	public final static Parcelable.Creator<DownloadNewState> CREATOR = new Creator<DownloadNewState>() {

		@Override
		public DownloadNewState[] newArray(int size) {
			return new DownloadNewState[size];
		}

		@Override
		public DownloadNewState createFromParcel(Parcel source) {
			return new DownloadNewState(source);
		}
	};

}
