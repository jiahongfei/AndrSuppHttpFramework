package com.andr.supp.http.demo.network.request;

import java.io.File;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import com.andr.supp.http.demo.network.tips.NetworkAlertDialog;
import com.andr.supp.http.demo.network.tips.NetworkToast;
import com.andr.supp.http.demo.network.tips.NetworkWaitDialog;
import com.android.support.http.fileutils.FilePathUtils;
import com.android.support.http.idialog.INetworkAlertDialog;
import com.android.support.http.idialog.INetworkToast;
import com.android.support.http.idialog.INetworkWaitDialog;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.loopj.android.http.RequestParams;

import android.content.Context;


/**
 * 
 * @Description: 用于Http请求的基类，子类需要覆盖其中的方法才可以请求网络
 * 这里只给出了一个继承IBaseRequest的Demo可以自己去修改
 * @version V1.0.0
 */
public abstract class BaseHttpRequest implements IBaseRequest {

	public static final int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;

	/**如果不设置是最大值，表示一定会返回true*/
	private Long mCacheRuleTimeMillis = Long.MAX_VALUE;
	
	private RequestParams mRequestParams = null;

	public BaseHttpRequest() {
	}

	@Override
	public int getCacheMode() {
		//查看IBaseRequest有定义的意思
		return NO_CACHE;
	}

	@Override
	public final boolean getCacheRule(long lastModified) {
		if (0 == lastModified) {
			// 如果是0表示没有缓存文件，返回true更新缓存
			return true;
		}
		// 判断间隔多长时间可以刷新缓存，
		long current = System.currentTimeMillis();

		if (current - lastModified > mCacheRuleTimeMillis) {
			return true;
		}

		return false;
	}

	@Override
	public boolean getOfflineCache(IBaseResponse iBaseResponse) {
		return false;
	}
	
	@Override
	public boolean isCacheResponseInCacheNetworkStatus() {
		return true;
	}

	// @Override
	// public String getAbsoluteUrl() {
	// return null;
	// }

	@Override
	public Header[] getHeaders() {
		return null;
	}

	@Override
	public HttpEntity getPostRequestEntity() {
		return null;
	}
	
	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public String getUserAgent() {
		return null;
	}

	@Override
	public int getTimeOut() {
		return DEFAULT_SOCKET_TIMEOUT;
	}

	@Override
	public final RequestParams getRequestParams() {
		return mRequestParams;
	}

	@Override
	public String getRequestParamsCacheKey() {
		return mRequestParams.toString();
	}

	@Override
	public File getTargetFile() {
		return null;
	}

	@Override
	public boolean getFileAppend() {
		return false;
	}

	@Override
	public INetworkAlertDialog getNetworkAlertDialog(Context context) {
		return new NetworkAlertDialog(context);
	}

	@Override
	public INetworkWaitDialog getNetworkWaitDialog(Context context) {
		return new NetworkWaitDialog(context);
	}

	@Override
	public INetworkToast getNetworkConnectErrorToast(Context context) {
		return new NetworkToast(context);
	}

	@Override
	public String getCacheFileAbsolutepath(Context context) {
		//如果要设置缓存这个绝对路径必须设置，下面这种方式是默认的绝对路径强烈推荐，但是也可以自己修改成其他的路径
		return FilePathUtils.getCacheFileAbsolutepath(context, this);
	}

	/**
	 * 设置缓存时间，毫秒
	 * 
	 * @param timeMillis
	 *            毫秒 Long型
	 */
	public void setCacheRuleTimeMillis(Long timeMillis) {
		mCacheRuleTimeMillis = timeMillis;
	}

	/**
	 * 传递参数
	 * 
	 * @param params
	 *  @param iuserMultipart
	 *            是否强制使用Multipart提交表单
	 */
	public void setRequestParams(RequestParams requestParams, boolean userMultipart) {
		mRequestParams = requestParams;
		mRequestParams.setUserMultipart(userMultipart);
	}

	/**
	 * 传递参数
	 * 
	 * @param params
	 */
	public void setRequestParams(RequestParams requestParams) {
		setRequestParams(requestParams, false);
	}
}
