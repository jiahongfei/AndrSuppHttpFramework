package com.android.support.http.networkhandler;

import java.lang.ref.WeakReference;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.android.support.http.idialog.IDialogOnKeyCancel;
import com.android.support.http.idialog.INetworkAlertDialog;
import com.android.support.http.idialog.INetworkToast;
import com.android.support.http.idialog.INetworkWaitDialog;
import com.android.support.http.inetworklistener.IBaseErrorListener;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.inetworklistener.IBaseStateListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

/**
 * 
 * @Description: 用于设置网络请求中的个个状态的积累（弹出等待框，错误处理，缓存处理等）
 * @version V1.0.0
 */
public abstract class BaseAsyncHttpResponseHandler extends
		AsyncHttpResponseHandler {

	/**
	 * 
	 * @Title: BaseAsyncHttpResponseHandler.java
	 * @Package hbyc.china.medical.view.base.baseactivity.network
	 * @ClassName: RequestRetry
	 * @Description: 访问网络出错的时候点击重试调用的监听
	 * @author jiahongfei jiahongfeinew@163.com
	 * @date 2014-11-25 上午9:22:16
	 * @version V1.0.0
	 */
	public interface RequestRetry {
		public void onRequestRetry();
	}
	
	private Handler mHandler = new Handler(Looper.getMainLooper());

	protected WeakReference<Context> mWeakReference = null;

	protected IBaseStateListener mIBaseStateListener = null;
	protected IBaseErrorListener mIBaseErrorListener = null;
	protected IBaseRequest mIBaseRequest = null;
	protected IBaseResponse mIBaseResponse = null;

	protected RequestHandle mRequestHandle = null;

	protected INetworkWaitDialog mWaitDialog = null;
	protected INetworkAlertDialog mErrorDialog = null;

	protected RequestRetry mRequestRetry;

	public BaseAsyncHttpResponseHandler(final Context context,
			final IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener) {

		mWeakReference = new WeakReference<Context>(context);
		this.mIBaseStateListener = iBaseStateListener;
		this.mIBaseErrorListener = iBaseErrorListener;
		this.mIBaseRequest = iBaseRequest;
		this.mIBaseResponse = iBaseResponse;

	}

	@Override
	public void onStart() {
		if (null != mIBaseStateListener) {
			mIBaseStateListener.onStart();
		} else {
			showWaitDialog();
		}
	}

	@Override
	public void onProgress(int bytesWritten, int totalSize) {
		if (null != mIBaseStateListener) {
			mIBaseStateListener.onProgress(bytesWritten, totalSize);
		} else {

		}
	}

	@Override
	public void onFinish() {
		if (null != mIBaseStateListener) {
			mIBaseStateListener.onFinish();
		} else {
			hideWaitDialog();
		}
	}
	
	protected void showNetworkConnectToast(final Throwable error) {
		final Context context = mWeakReference.get();
		if (null != context) {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					StringBuffer stringBuffer = new StringBuffer();
					ErrorExceptionHandler.NetworkExceptionHandler(stringBuffer, error);
					INetworkToast networkToast = mIBaseRequest.getNetworkConnectErrorToast(context);
					if(null != networkToast){
						networkToast.makeText(context, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	/**
	 * 弹出网络错误对话框，左边按钮重试，右边按钮设置网络，右上角退出对话框，点击返回键退出对话框
	 * 
	 * @param context
	 * @param error
	 */
	protected void showErrorDialog(final Throwable error) {
		final Context context = mWeakReference.get();
		if (null != context && context instanceof Activity
				&& !((Activity) context).isFinishing()) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					StringBuffer stringBuffer = new StringBuffer();
					ErrorExceptionHandler.NetworkExceptionHandler(stringBuffer,
							error);
					INetworkAlertDialog iNetworkAlertDialog = mIBaseRequest.getNetworkAlertDialog(context);
					if(null == iNetworkAlertDialog){
						return ;
					}
					iNetworkAlertDialog.setMessage(stringBuffer.toString());
					iNetworkAlertDialog.setLeftButton("设置网络",
							new View.OnClickListener() {

								@Override
								public void onClick(View arg0) {
									goSettingsActivity(context);
								}
							});
					iNetworkAlertDialog.setRightButton("重试",
							new View.OnClickListener() {

								@Override
								public void onClick(View arg0) {
									if (null != mRequestRetry) {
										mRequestRetry.onRequestRetry();
									}
								}
							});
					iNetworkAlertDialog.show();
					mErrorDialog = iNetworkAlertDialog;
				}
			});
		}

	}

	/**
	 * 错误的Toast提示
	 * 
	 * @param error
	 */
	protected void showErrorToast(final Throwable error) {
		final Context context = mWeakReference.get();
		if (null != context) {
			
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					StringBuffer stringBuffer = new StringBuffer();
					ErrorExceptionHandler.NetworkExceptionHandler(stringBuffer, error);
					INetworkToast networkToast = mIBaseRequest.getNetworkConnectErrorToast(context);
					if(null != networkToast){
						networkToast.makeText(context, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	protected void hideErrorDialog() {
		mHandler.post(new Runnable() {
			public void run() {
				if (null != mErrorDialog) {
					mErrorDialog.cancel();
					mErrorDialog = null;
				}
			}
		});
	}

	/**
	 * 弹出等待对话框
	 * 
	 */
	protected void showWaitDialog() {
		final Context context = mWeakReference.get();
		if (null != context && context instanceof Activity
				&& !((Activity) context).isFinishing()) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					final INetworkWaitDialog iNetworkWaitDialog = mIBaseRequest.getNetworkWaitDialog(context);
					if(null == iNetworkWaitDialog){
						return ;
					}
					iNetworkWaitDialog.setMessage("正在加载请等待");
					iNetworkWaitDialog.setOnKeyCancelListener(new IDialogOnKeyCancel() {
						
						@Override
						public void onKeyCancelListener() {
							if (null != mRequestHandle) {
								mRequestHandle.cancel(true);
								if (null != iNetworkWaitDialog) {
									iNetworkWaitDialog.cancel();
								}
							}
						}
					});
					iNetworkWaitDialog.show();
					mWaitDialog = iNetworkWaitDialog;
					
				}
			});
		}
	}

	protected void hideWaitDialog() {
		mHandler.post(new Runnable() {
			public void run() {
				if (null != mWaitDialog) {
					mWaitDialog.cancel();
					mWaitDialog = null;
				}
			}
		});
	}
	
	protected String getCustomErrorParam(String url, String paramKey) {
		String[] urlStrings = url.split("@");
		if (urlStrings.length >= 2) {
			String[] params = urlStrings[1].split("&");
			if (params.length >= 1) {
				for (int i = 0; i < params.length; i++) {
					String[] paramValues = params[i].split("=");
					if (paramValues.length >= 2) {
						if (paramValues[0].equals(paramKey)) {
							return paramValues[1];
						}
					}
				}
			}
		}
		return null;
	}

	public void setRequestHandle(RequestHandle requestHandle) {
		mRequestHandle = requestHandle;
	}

	/**
	 * 设置error点击重试调用的监听
	 * 
	 * @param requestRetry
	 */
	public void setRequestRetry(RequestRetry requestRetry) {
		mRequestRetry = requestRetry;
	}

	@Override
	public boolean getNetworkCache() throws Exception {

		return true;
	}
	
	/**
	 * 跳转到系统设置界面
	 * @param context
	 */
	public void goSettingsActivity(Context context){
		  Intent intent =  new Intent(Settings.ACTION_SETTINGS);  
		  context.startActivity(intent);  
	}

}
