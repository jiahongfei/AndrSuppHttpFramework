package com.android.support.http.inetworklistener;

import java.io.File;

import org.apache.http.Header;

import android.R.integer;

public interface IBaseErrorListener {

//	/**
//	 * 自定义错误监听
//	 * @param statusCode
//	 * @param headers
//	 * @param responseBody
//	 * @param error
//	 */
//	public void onFailure(int statusCode, Header[] headers, Throwable error);
	
	/**
	 * 自定义错误监听
	 * @param statusCode
	 * @param errCode
	 * @param headers
	 * @param responseBody
	 * @param error
	 */
	public void onFailure(int statusCode, int errCode, Header[] headers, Throwable error);
	
	/**
	 * 当自定义错误监听的时候是否显示网络返回返回错误
	 * @return true显示toast，false不显示toast
	 */
	public boolean isShowNetworkResponseError();
	
	/**
	 * 当自定义监听的时候是否显示网络连接错误，左边是设置网络右边是重试按钮
	 * @return true显示dialog，false不显示dialog
	 */
	public boolean isShowNetworkConnectError();
	
	/**
	 * 网络连接错误提示模式
	 * @return true显示toast，false显示Dialog
	 */
	public boolean isNetworkConnectErrorTipsMode();
}
