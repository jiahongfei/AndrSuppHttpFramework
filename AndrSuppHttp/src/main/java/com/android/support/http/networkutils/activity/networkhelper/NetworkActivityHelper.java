package com.android.support.http.networkutils.activity.networkhelper;

import org.apache.http.Header;

import android.content.Context;

import com.android.support.http.inetworklistener.IBaseErrorListener;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.inetworklistener.IBaseStateListener;
import com.android.support.http.networkhandler.BaseErrorListener;
import com.android.support.http.networkutils.AsyncNetworkUtils;
import com.loopj.android.http.RequestHandle;

/**
 * 
 * @Description: 
 *               基类异步网络请求的工具类，BaseActivity、BaseFragment、BaseFragmentActivity等积累中实现
 *               ，在这里面写暴露的代码，为了以后改动方便
 * @version V1.0.0
 */
public class NetworkActivityHelper implements INetworkActivityHelper{

	private AsyncNetworkUtils mAsyncNetworkUtils = AsyncNetworkUtils
			.getAsyncNetworkUtils();
	private Context mContext;

	/**
	 * @param context
	 *            当前Activity
	 */
	public NetworkActivityHelper(Context context) {
		mContext = context;
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

		RequestHandle requestHandle = mAsyncNetworkUtils.GetRequest(mContext,
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

		RequestHandle requestHandle = GetRequest(iBaseRequest, iBaseResponse,
				iBaseStateListener, null);
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

		RequestHandle requestHandle = GetRequest(iBaseRequest, iBaseResponse,
				null, iBaseErrorListener);
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

		RequestHandle requestHandle = GetRequest(iBaseRequest, iBaseResponse,
				null, null);
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
		RequestHandle requestHandle = GetRequest(iBaseRequest, iBaseResponse,
				new IBaseStateListener() {
					@Override
					public void onStart() {
					}

					@Override
					public void onProgress(int bytesWritten, int totalSize) {
					}

					@Override
					public void onFinish() {
					}

				}, new BaseErrorListener() {

					@Override
					public void onFailure(int statusCode, Header[] headers, Throwable error) {
						
					}
					
					
				});
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

		RequestHandle requestHandle = mAsyncNetworkUtils.PostRequest(mContext,
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

		RequestHandle requestHandle = PostRequest(iBaseRequest, iBaseResponse,
				iBaseStateListener, null);
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

		RequestHandle requestHandle = PostRequest(iBaseRequest, iBaseResponse,
				null, iBaseErrorListener);
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

		RequestHandle requestHandle = PostRequest(iBaseRequest, iBaseResponse,
				null, null);
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
		RequestHandle requestHandle = PostRequestIgnoreStateListener(
				iBaseRequest, iBaseResponse, new BaseErrorListener() {

					@Override
					public void onFailure(int statusCode, Header[] headers, Throwable error) {
						
					}
					
				});
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
			final IBaseErrorListener iBaseErrorListener) {
		RequestHandle requestHandle = PostRequest(iBaseRequest, iBaseResponse,
				new IBaseStateListener() {
					@Override
					public void onStart() {
					}

					@Override
					public void onFinish() {
					}

					@Override
					public void onProgress(int bytesWritten, int totalSize) {
					}

				}, iBaseErrorListener);
		return requestHandle;
	}

	/********************** POST REQUEST END **********************/

	/**
	 * 取消当前activity所有的请求
	 */
	public void cancelCurrentActivityAllRequest() {
		mAsyncNetworkUtils.cancelRequests(mContext, true);
	}

}
