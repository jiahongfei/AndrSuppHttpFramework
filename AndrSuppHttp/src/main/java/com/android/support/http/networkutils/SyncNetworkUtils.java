package com.android.support.http.networkutils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;

/**
 * 
* @Description: 同步网络工具类，阻塞UI线程，用来调用网络库，弹出交互对话框等
* @version V1.0.0
 */
public class SyncNetworkUtils extends BaseNetwork {

	private static SyncNetworkUtils sNetworkUtils = null;

	/**
	 * 网络工具类,单例
	 * 
	 * @return
	 */
	public static SyncNetworkUtils getSyncNetworkUtils() {
		if (null == sNetworkUtils) {
			newInstance();
		}
		return sNetworkUtils;
	}
	
	private static synchronized void newInstance(){
		if (null == sNetworkUtils) {
			sNetworkUtils = new SyncNetworkUtils();
		}
	}

	/**
	 * 构造函数
	 * 
	 */
	private SyncNetworkUtils() {
		AsyncHttpClient asyncHttpClient = new SyncHttpClient();
		setAsyncHttpClient(asyncHttpClient);
	}

}
