package com.android.support.http.networkutils;

import com.loopj.android.http.AsyncHttpClient;

/**
 * 
 * @Description: 异步网络工具类，用来调用网络库，弹出交互对话框等
 * @version V1.0.0
 */
public class AsyncNetworkUtils extends BaseNetwork {

	private static AsyncNetworkUtils sNetworkUtils = null;

	/**
	 * 网络工具类,单例
	 * 
	 * @param isAsync
	 *            true异步处理，false同步处理
	 * @return
	 */
	public static AsyncNetworkUtils getAsyncNetworkUtils() {
		if (null == sNetworkUtils) {
			newInstance();
		}
		return sNetworkUtils;
	}
	
	private static synchronized void newInstance(){
		if (null == sNetworkUtils) {
			sNetworkUtils = new AsyncNetworkUtils();
		}
	}

	/**
	 * 构造函数
	 * 
	 */
	private AsyncNetworkUtils() {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		setAsyncHttpClient(asyncHttpClient);
	}

}
