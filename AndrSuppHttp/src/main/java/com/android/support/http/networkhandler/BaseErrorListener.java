package com.android.support.http.networkhandler;

import org.apache.http.Header;

import com.android.support.http.inetworklistener.IBaseErrorListener;
/**
* @Description: 错误监听的基类
* @version V1.0.0
 */
public abstract class BaseErrorListener implements IBaseErrorListener {
	
	private boolean isShowNetworkResponseError = false;
	private boolean isShowNetworkConnectError = false;
	private boolean isNetworkConnectErrorTipsMode = true;

	public BaseErrorListener() {
		this(false, false,false);
	}
	
	/**
	 * @param isShowNetworkResponseError 显示默认网络返回错误提示
	 * @param isShowNetworkConnectError 显示默认网络连接错误提示
	 */
	public BaseErrorListener(boolean isShowNetworkResponseError, boolean isShowNetworkConnectError){
		this(isShowNetworkResponseError, isShowNetworkConnectError, true);
	}
	
	/**
	 * @param isShowNetworkResponseError 显示默认网络返回错误提示
	 * @param isShowNetworkConnectError 显示默认网络连接错误提示
	 * @param isNetworkConnectErrorTipsMode true显示toast，false显示Dialog
	 */
	public BaseErrorListener(boolean isShowNetworkResponseError, boolean isShowNetworkConnectError, boolean isNetworkConnectErrorTipsMode){
		this.isShowNetworkResponseError = isShowNetworkResponseError;
		this.isShowNetworkConnectError= isShowNetworkConnectError;
		this.isNetworkConnectErrorTipsMode = isNetworkConnectErrorTipsMode;
	}

	@Override
	public boolean isNetworkConnectErrorTipsMode() {
		return isNetworkConnectErrorTipsMode;
	}
	
	@Override
	public boolean isShowNetworkResponseError() {
		return isShowNetworkResponseError;
	}

	@Override
	public boolean isShowNetworkConnectError() {
		return isShowNetworkConnectError;
	}
	
	@Override
	public void onFailure(int statusCode, int errCode, Header[] headers,
			Throwable error) {
		onFailure(statusCode, headers, error);
	}

	public abstract void onFailure(int statusCode, Header[] headers,
			Throwable error);
	
}
