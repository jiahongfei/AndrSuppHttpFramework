package com.android.support.http.inetworklistener;

public interface IBaseStateListener {

	public void onStart();
	
	/**
	 * upload file and down file progress
	 * @param bytesWritten
	 * @param totalSize
	 */
	public void onProgress(int bytesWritten, int totalSize);
	
	public void onFinish();
}
