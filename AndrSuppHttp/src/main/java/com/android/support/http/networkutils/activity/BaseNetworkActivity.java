package com.android.support.http.networkutils.activity;

import com.android.support.http.inetworklistener.IBaseErrorListener;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.inetworklistener.IBaseStateListener;
import com.android.support.http.networkutils.activity.networkhelper.INetworkActivityHelper;
import com.android.support.http.networkutils.activity.networkhelper.NetworkActivityHelper;
import com.loopj.android.http.RequestHandle;

import android.app.Activity;
import android.os.Bundle;

/**
 * 这个基类只是封装了一些常用的网络方法
 * 
 * @author jiahongfei
 * 
 */
public class BaseNetworkActivity extends Activity implements
		INetworkActivityHelper {

	private INetworkActivityHelper mAppViewAsyncNetworkHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAppViewAsyncNetworkHelper = new NetworkActivityHelper(this);
	}

	/********************** GET REQUEST START **********************/
	/**
	 * GET方式请求，状态监听和错误监听都自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @param iBaseErrorListener
	 * @return
	 */
	public synchronized RequestHandle GetRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.GetRequest(
				iBaseRequest, iBaseResponse, iBaseStateListener,
				iBaseErrorListener);

		return requestHandle;

	}

	/**
	 * GET方式请求，状态监听自定义，错误监听默认
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @return
	 */
	public synchronized RequestHandle GetRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.GetRequest(
				iBaseRequest, iBaseResponse, iBaseStateListener);
		return requestHandle;
	}

	/**
	 * GET方式请求，错误监听默认，状态监听自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseErrorListener
	 * @return
	 */
	public synchronized RequestHandle GetRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse,
			final IBaseErrorListener iBaseErrorListener) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.GetRequest(
				iBaseRequest, iBaseResponse, iBaseErrorListener);
		return requestHandle;
	}

	/**
	 * GET方式请求，状态监听和错误监听都默认（状态监听弹出等待框，错误监听弹出toast）
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public synchronized RequestHandle GetRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.GetRequest(
				iBaseRequest, iBaseResponse);
		return requestHandle;
	}

	/**
	 * GET方式请求，忽略状态监听和错误监听
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public synchronized RequestHandle GetRequestIgnoreListener(
			IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse) {
		RequestHandle requestHandle = mAppViewAsyncNetworkHelper
				.GetRequestIgnoreListener(iBaseRequest, iBaseResponse);
		return requestHandle;
	}

	/********************** GET REQUEST END **********************/

	/********************** POST REQUEST START **********************/

	/**
	 * POST方式请求，状态监听和错误监听都自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @param iBaseErrorListener
	 * @return
	 */
	public synchronized RequestHandle PostRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.PostRequest(
				iBaseRequest, iBaseResponse, iBaseStateListener,
				iBaseErrorListener);

		return requestHandle;

	}

	/**
	 * POST方式请求，状态监听自定义，错误监听默认
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @return
	 */
	public synchronized RequestHandle PostRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.PostRequest(
				iBaseRequest, iBaseResponse, iBaseStateListener);
		return requestHandle;
	}

	/**
	 * POST方式请求，错误监听默认，状态监听自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseErrorListener
	 * @return
	 */
	public synchronized RequestHandle PostRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse,
			final IBaseErrorListener iBaseErrorListener) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.PostRequest(
				iBaseRequest, iBaseResponse, iBaseErrorListener);
		return requestHandle;
	}

	/**
	 * POST方式请求，状态监听和错误监听都默认（状态监听弹出等待框，错误监听弹出toast）
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public synchronized RequestHandle PostRequest(IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse) {

		RequestHandle requestHandle = mAppViewAsyncNetworkHelper.PostRequest(
				iBaseRequest, iBaseResponse);
		return requestHandle;
	}

	/**
	 * POST方式请求，忽略状态监听和错误监听
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public synchronized RequestHandle PostRequestIgnoreListener(
			IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse) {
		RequestHandle requestHandle = mAppViewAsyncNetworkHelper
				.PostRequestIgnoreListener(iBaseRequest, iBaseResponse);
		return requestHandle;
	}

	/**
	 * POST方式请求，忽略状态监听
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseErrorListener
	 * @return
	 */
	public synchronized RequestHandle PostRequestIgnoreStateListener(
			IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse,
			IBaseErrorListener iBaseErrorListener) {
		RequestHandle requestHandle = mAppViewAsyncNetworkHelper
				.PostRequestIgnoreStateListener(iBaseRequest, iBaseResponse,
						iBaseErrorListener);
		return requestHandle;
	}

	/********************** POST REQUEST END **********************/

	/**
	 * 取消当前activity所有的请求
	 */
	public void cancelCurrentActivityAllRequest() {
		mAppViewAsyncNetworkHelper.cancelCurrentActivityAllRequest();
	}

}
