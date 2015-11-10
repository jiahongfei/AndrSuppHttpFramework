package com.android.support.http.networkhandler;

import com.android.support.http.inetworklistener.IBaseStateListener;
/**
 * 
* @Description: 为了去掉等待框
* @version V1.0.0
 */
public class BaseStateListener implements IBaseStateListener {

	public BaseStateListener() {
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onProgress(int bytesWritten, int totalSize) {

	}

	@Override
	public void onFinish() {

	}

}
