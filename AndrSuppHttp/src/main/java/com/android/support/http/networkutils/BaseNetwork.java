package com.android.support.http.networkutils;

import java.io.File;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import android.content.Context;
import android.text.TextUtils;

import com.android.support.http.fileutils.FileCacheUtils;
import com.android.support.http.inetworklistener.IBaseErrorListener;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.inetworklistener.IBaseStateListener;
import com.android.support.http.networkhandler.BaseAsyncHttpResponseHandler;
import com.android.support.http.networkhandler.BaseHttpFileResponse;
import com.android.support.http.networkhandler.CustomFileAsyncHttpResponseHandler;
import com.android.support.http.networkhandler.CustomTextAsyncHttpResponseHandler;
import com.android.support.http.networkhandler.BaseAsyncHttpResponseHandler.RequestRetry;
import com.android.support.http.networkhandler.CustomTextAsyncHttpResponseHandler.INetworkCacheListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

/**
 * 
 * @Description: 基类，网络工具类，用来调用网络库，弹出交互对话框等
 * @version V1.0.0
 */
abstract class BaseNetwork {

	private AsyncHttpClient mAsyncHttpClient = null;

	protected void setAsyncHttpClient(AsyncHttpClient asyncHttpClient) {
		mAsyncHttpClient = asyncHttpClient;

	}

	public AsyncHttpClient getAsyncHttpClient() {
		return mAsyncHttpClient;
	}

	public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
		mAsyncHttpClient.cancelRequests(context, mayInterruptIfRunning);
	}

	/**
	 * 
	 * @Title: BaseNetworkUtils.java
	 * @Package hbyc.china.medical.view.base.baseactivity.network
	 * @ClassName: CustomNetworkCache
	 * @Description: 用于获取缓存
	 * @author jiahongfei jiahongfeinew@163.com
	 * @date 2014-12-3 下午1:40:48
	 * @version V1.0.0
	 */
	private class CustomNetworkCache implements INetworkCacheListener {
		private Context mContext;
		private IBaseRequest mIBaseRequest;
		private IBaseResponse mIBaseResponse;

		public CustomNetworkCache(Context context, IBaseRequest iBaseRequest,
				IBaseResponse iBaseResponse) {
			mContext = context;
			mIBaseRequest = iBaseRequest;
			mIBaseResponse = iBaseResponse;
		}

		@Override
		public boolean getNetworkCache() throws Exception {
			// false不连接网络，true连接网络
//			if (IBaseRequest.CACHE_NETWORK == mIBaseRequest.getCacheMode()) {
//				if (mIBaseRequest.getOfflineCache(mIBaseResponse)) {
//					// 获取离线成功,有离线缓存直接就不连接网络，也不更新缓存
//					return false;
//				} else if (FileCacheUtils.getNetworkCache(mContext,
//						mIBaseRequest, 0, null, mIBaseResponse)) {
//					// 读取缓存成功，判断规则是否更新缓存
//					String filePathString = mIBaseRequest
//							.getCacheFileAbsolutepath(mContext, mIBaseRequest);
//					if (mIBaseRequest.getCacheRule(new File(filePathString)
//							.lastModified())) {
//						// 更新缓存,连接网络
//						return true;
//					} else {
//						// 不更新缓存，不连接网络
//						return false;
//					}
//
//				}
//			}
			if (IBaseRequest.CACHE_NETWORK == mIBaseRequest.getCacheMode()) {
				if (mIBaseRequest.getOfflineCache(mIBaseResponse)) {
					// 获取离线成功,有离线缓存直接就不连接网络，也不更新缓存
					return false;
				} else if (FileCacheUtils.isNetworkCacheByRequest(mContext, mIBaseRequest)) {
					// 读取缓存成功，判断规则是否更新缓存
					String filePathString = mIBaseRequest
							.getCacheFileAbsolutepath(mContext);
					if(TextUtils.isEmpty(filePathString)){
						//没有缓存路径，链接网络获取
						return true;
					}
					if (mIBaseRequest.getCacheRule(new File(filePathString)
							.lastModified())) {
						if(mIBaseRequest.isCacheResponseInCacheNetworkStatus()){
							FileCacheUtils.getNetworkCache(mContext,
									mIBaseRequest, 0, null, mIBaseResponse);
						}
						// 更新缓存,连接网络
						return true;
					} else {
						//缓存没有的到期读取缓存刷新界面
						FileCacheUtils.getNetworkCache(mContext,
								mIBaseRequest, 0, null, mIBaseResponse);
						// 不更新缓存，不连接网络
						return false;
					}

				}
			}
			return true;
		}

	}

	/**
	 * GET方式请求
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @param iBaseErrorListener
	 * @return
	 */
	public synchronized RequestHandle GetRequest(final Context context,
			final IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener) {

		CustomNetworkCache customNetworkCache = new CustomNetworkCache(context,
				iBaseRequest, iBaseResponse);

		BaseAsyncHttpResponseHandler asyncHttpResponseHandler = getAsyncHttpResponseHandler(
				context, iBaseRequest, iBaseResponse, iBaseStateListener,
				iBaseErrorListener, customNetworkCache);
		asyncHttpResponseHandler.setRequestRetry(new RequestRetry() {

			@Override
			public void onRequestRetry() {
				if (mAsyncHttpClient instanceof SyncHttpClient) {
					// 如果要是同步阻塞的对象需要启动一个线程，忘了请求不允许在UI线程中发起否则会抛出异常，android.os.NetworkOnMainThreadException
					new Thread(new Runnable() {

						@Override
						public void run() {
							GetRequest(context, iBaseRequest, iBaseResponse,
									iBaseStateListener, iBaseErrorListener);
						}
					}).start();
				} else {
					GetRequest(context, iBaseRequest, iBaseResponse,
							iBaseStateListener, iBaseErrorListener);
				}
			}
		});
		asyncHttpResponseHandler.setUseSynchronousMode(true);

		if (iBaseRequest.getTimeOut() >= 1000) {// 大于等于1s
			mAsyncHttpClient.setTimeout(iBaseRequest.getTimeOut());
		}
		if (!TextUtils.isEmpty(iBaseRequest.getUserAgent())) {
			mAsyncHttpClient.setUserAgent(iBaseRequest.getUserAgent());
		}

		RequestHandle requestHandle = mAsyncHttpClient.get(context,
				iBaseRequest.getAbsoluteUrl(), iBaseRequest.getHeaders(),
				iBaseRequest.getRequestParams(), asyncHttpResponseHandler);
		asyncHttpResponseHandler.setRequestHandle(requestHandle);

		return requestHandle;

	}

	/**
	 * POST方式请求
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @param iBaseErrorListener
	 * @return 当iBaseRequest.getCacheMode()=CACHE_NETWORK返回null
	 */
	public synchronized RequestHandle PostRequest(final Context context,
			final IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener) {

		CustomNetworkCache customNetworkCache = new CustomNetworkCache(context,
				iBaseRequest, iBaseResponse);

		BaseAsyncHttpResponseHandler asyncHttpResponseHandler = getAsyncHttpResponseHandler(
				context, iBaseRequest, iBaseResponse, iBaseStateListener,
				iBaseErrorListener, customNetworkCache);
		asyncHttpResponseHandler.setRequestRetry(new RequestRetry() {

			@Override
			public void onRequestRetry() {
				if (mAsyncHttpClient instanceof SyncHttpClient) {
					// 如果要是同步阻塞的对象需要启动一个线程，忘了请求不允许在UI线程中发起否则会抛出异常，android.os.NetworkOnMainThreadException
					new Thread(new Runnable() {

						@Override
						public void run() {
							PostRequest(context, iBaseRequest, iBaseResponse,
									iBaseStateListener, iBaseErrorListener);
						}
					}).start();
				} else {
					PostRequest(context, iBaseRequest, iBaseResponse,
							iBaseStateListener, iBaseErrorListener);
				}

			}
		});
		asyncHttpResponseHandler.setUseSynchronousMode(true);

		if (iBaseRequest.getTimeOut() >= 1000) {// 大于等于1s
			mAsyncHttpClient.setTimeout(iBaseRequest.getTimeOut());
		}
		if (!TextUtils.isEmpty(iBaseRequest.getUserAgent())) {
			mAsyncHttpClient.setUserAgent(iBaseRequest.getUserAgent());
		}
		RequestHandle requestHandle = null;

		if (null != iBaseRequest.getPostRequestEntity()) {
			requestHandle = mAsyncHttpClient.post(context,
					iBaseRequest.getAbsoluteUrl(), iBaseRequest.getHeaders(),
					iBaseRequest.getPostRequestEntity(),
					iBaseRequest.getContentType(), asyncHttpResponseHandler);
		} else {
			requestHandle = mAsyncHttpClient.post(context,
					iBaseRequest.getAbsoluteUrl(), iBaseRequest.getHeaders(),
					iBaseRequest.getRequestParams(),
					iBaseRequest.getContentType(), asyncHttpResponseHandler);
		}
		asyncHttpResponseHandler.setRequestHandle(requestHandle);

		return requestHandle;
	}

	/**
	 * 公用的方法用来生成AsyncHttpResponseHandler
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @param iBaseErrorListener
	 * @return
	 */
	public BaseAsyncHttpResponseHandler getAsyncHttpResponseHandler(
			final Context context, final IBaseRequest iBaseRequest,
			final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener,
			final INetworkCacheListener iNetworkCacheListener) {

		BaseAsyncHttpResponseHandler asyncHttpResponseHandler = null;
		// 区分文件下载还是二进制数据下载
		if (iBaseResponse instanceof BaseHttpFileResponse) {
			asyncHttpResponseHandler = new CustomFileAsyncHttpResponseHandler(
					context, iBaseRequest, iBaseResponse, iBaseStateListener,
					iBaseErrorListener);
		} else {
			asyncHttpResponseHandler = new CustomTextAsyncHttpResponseHandler(
					context, iBaseRequest, iBaseResponse, iBaseStateListener,
					iBaseErrorListener, iNetworkCacheListener);
		}

		return asyncHttpResponseHandler;
	}

}
