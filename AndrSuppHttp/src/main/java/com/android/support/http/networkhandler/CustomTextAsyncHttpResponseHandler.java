package com.android.support.http.networkhandler;

import java.io.File;

import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.support.http.fileutils.FileCacheUtils;
import com.android.support.http.inetworklistener.IBaseErrorListener;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.inetworklistener.IBaseStateListener;

/**
 * 
 * @Description: 用于在网络中下载小文件或者文本，可以直接存储在内存中例如：json，xml，html
 * @version V1.0.0
 */
public class CustomTextAsyncHttpResponseHandler extends
		BaseAsyncHttpResponseHandler {

	/**
	 * 
	 * @Title: CustomTextAsyncHttpResponseHandler.java
	 * @Package hbyc.china.medical.view.base.baseactivity.network
	 * @ClassName: NetworkCacheListener
	 * @Description: 缓存监听
	 * @author jiahongfei jiahongfeinew@163.com
	 * @date 2014-12-3 下午1:30:08
	 * @version V1.0.0
	 */
	public interface INetworkCacheListener {
		/**
		 * 获取缓存
		 * 
		 * @return false不连接网络，true连接网络
		 * @throws Exception
		 */
		public boolean getNetworkCache() throws Exception;
	}

	private INetworkCacheListener mNetworkCacheListener;

	public CustomTextAsyncHttpResponseHandler(final Context context,
			final IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener,
			final INetworkCacheListener networkCacheListener) {
		super(context, iBaseRequest, iBaseResponse, iBaseStateListener,
				iBaseErrorListener);
		mNetworkCacheListener = networkCacheListener;

	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		// 返回结果
		File cacheFile = null;
		boolean cacheRule = false;
		try {
			try {
				// 保存缓存直接抛出异常，不提示错误信息，因为不影响展示
				if (IBaseRequest.NO_CACHE != mIBaseRequest.getCacheMode()) {
					String filePathString = mIBaseRequest
							.getCacheFileAbsolutepath(mWeakReference.get());
					if(!TextUtils.isEmpty(filePathString)){
						cacheFile = new File(filePathString);
						cacheRule = mIBaseRequest.getCacheRule(cacheFile
								.lastModified());
						if (cacheRule) {
							// 有缓存，更新规则返回true，保存缓存
							Log.e("", "save network cache");
							FileCacheUtils.saveNetworkCache(mWeakReference.get(),
									mIBaseRequest, responseBody);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 影响展示信息抛出异常
			if (null != mIBaseResponse) {
				mIBaseResponse.onResponse(statusCode, headers, responseBody);
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();

			try {
				// 删除缓存不影响展示信息，不提示错误
				if ((IBaseRequest.NO_CACHE != mIBaseRequest.getCacheMode())
						&& cacheRule && null != cacheFile && cacheFile.exists()) {
					cacheFile.delete();
				}
			} catch (Exception e2) {
				e.printStackTrace();
			}

			sendFailureMessage(statusCode, headers, responseBody, e);

		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, final Throwable error) {
		error.printStackTrace();
		int tempStatusCode = statusCode;
		// 错误处理
		try {
			try {
				// 获取缓存对用户是不可见的，不提示错误
				if (IBaseRequest.NETWORK_CACHE == mIBaseRequest.getCacheMode()) {
					// 当有缓存的时候读取缓存
					FileCacheUtils.getNetworkCache(mWeakReference.get(),
							mIBaseRequest, statusCode, headers, mIBaseResponse);
				}
				
				String filePathString = mIBaseRequest.getCacheFileAbsolutepath(mWeakReference.get());
				if(!TextUtils.isEmpty(filePathString)){
					//没有缓存路径
					File file = new File(filePathString);
					if (file.exists()) {
						tempStatusCode = ErrorExceptionHandler.ERROR_CODE_HAVE_CACHE;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (null != mIBaseErrorListener) {
				int errCode = 0;
				try {
					//error + "@errcode=" + errcode   @后面是参数
					String tempError = error.getMessage();
					String errCodeString = getCustomErrorParam(tempError, "errcode");
					errCode = Integer.parseInt(errCodeString);
				} catch (Exception e) {
					e.printStackTrace();
					errCode = 0;
				}
				mIBaseErrorListener.onFailure(tempStatusCode, errCode, headers, error);
			} else {
			}
			if (error instanceof HttpResponseException) {
				// 网络返回错误
				if (null != mIBaseErrorListener) {
					if (mIBaseErrorListener.isShowNetworkResponseError()) {
						showErrorToast(error);
					}
				} else {
					showErrorToast(error);
				}

			} else {
				// 网络连接错误
				if (null != mIBaseErrorListener) {
					if (mIBaseErrorListener.isShowNetworkConnectError()) {
						if (mIBaseErrorListener.isNetworkConnectErrorTipsMode()) {
							showNetworkConnectToast(error);
						} else {
							showErrorDialog(error);
						}
					}
				} else {
					showNetworkConnectToast(error);
				}
			}
		} catch (final Throwable e) {
			showErrorDialog(e);
			e.printStackTrace();
		}

	}

	@Override
	public boolean getNetworkCache() throws Exception {
		if (null != mNetworkCacheListener) {
			return mNetworkCacheListener.getNetworkCache();
		}
		return true;
	}

}
