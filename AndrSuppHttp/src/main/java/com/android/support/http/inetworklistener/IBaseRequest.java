package com.android.support.http.inetworklistener;

import java.io.File;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import com.android.support.http.idialog.INetworkAlertDialog;
import com.android.support.http.idialog.INetworkToast;
import com.android.support.http.idialog.INetworkWaitDialog;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.widget.Toast;

public interface IBaseRequest {
	
	/**没有缓存*/
	public static int NO_CACHE = 0;
	/**先从网络获取，然后判断缓存规则（getCacheRule()），是否更新缓存，如果出错读取缓存*/
	public static int NETWORK_CACHE = 1;
	/**
	 * 先读取缓存，后链接网络，
	 * 根据规则getCacheRule()判断是否需要更新缓存，如果true链接网络获取数据刷新缓存，如果false不连接网络
	 */
	public static int CACHE_NETWORK = 2;
	
	/**
	 * NO_CACHE  NETWORK_CACHE  CACHE_NETWORK
	 * @return
	 */
	public int getCacheMode();
	
	/**
	 * 获取离线缓存，当getCacheMode()返回CACHE_NETWORK这个的时候，先判断离线缓存是否存在，
	 * @param iBaseResponse
	 * @return true表示离线缓存存在，false表示离线缓存不存在
	 */
	@Deprecated
	public boolean getOfflineCache(IBaseResponse iBaseResponse);
	
	/**
	 * 缓存规则
	 * @param lastModified
	 * 	缓存文件的最后修改时间
	 * @return true可以更新，false不可以更新
	 */
	public boolean getCacheRule(long lastModified);
	
	/**
	 * 在CACHE_NETWORK缓存状态下，当规则需要联网的时候，请求网络之前是否还需要返回缓存数据
	 * @return true 当规则需要联网，联网之前获取本地缓存数据返回，刷新界面，相当于刷新两次界面，一次是联网之前获取本地缓存数据，另外一次是连接网络成功了刷新缓存同事刷新界面
	 * 		   false 当规则需要联网，联网之前不获取本地缓存数据，只是从网络获取数据刷新一遍界面
	 */
	public boolean isCacheResponseInCacheNetworkStatus();
	
	public String getAbsoluteUrl();

	public Header[] getHeaders();
	
	/**
	 * Sets the User-Agent header to be sent with each request.
	 * @return
	 */
	public String getUserAgent();
	
	/**
	 * Set both the connection and socket timeouts.
	 * @return
	 */
	public int getTimeOut();

	/**
	 * POST
	 * the content type of the payload you are sending, for example
	 * application/json if sending a json payload.
	 * 
	 * @return
	 */
	public String getContentType();

	public RequestParams getRequestParams();

	/**
	 * post方式，输入流的方式
	 * @return
	 */
	public HttpEntity getPostRequestEntity() ;
	
	/**
	 * 返回缓存数据的键值，一般都是url+参数，每次请求相同的url和参数固定不变，不要加当前时间否则没办法对应
	 * @return
	 */
	public String getRequestParamsCacheKey();
	
	/**
	 * 用于文件下载，表示下载文件绝对路径
	 * @return
	 */
	public File getTargetFile();
	
	/**
	 * 用于文件下载，表示是否追加
	 * @return true文件追加，false覆盖文件
	 */
	public boolean getFileAppend();
	
	/**
	 * 返回自定义的网络对话框，每个应用的对话框有可能风格不一样
	 * 主要是用来弹出网络连接错误对话框，显示设置网络，或者重试
	 * 查看IBaseErrorListener这个接口的isNetworkConnectErrorTipsMode()方法返回false
	 * @param context 当前Activity的Context
	 * @return
	 */
	public INetworkAlertDialog getNetworkAlertDialog(Context context);
	
	/**
	 * 返回自定义网络等待对话框，每个应用的对话框风格可能不一样
	 * @param context 当前Activity的Context
	 * @return
	 */
	public INetworkWaitDialog getNetworkWaitDialog(Context context);
	
	/**
	 * 返回网络连接错误Toast
	 * @param context
	 * @return
	 */
	public INetworkToast getNetworkConnectErrorToast(Context context);
	
	/**
	 * 获取缓存路径
	 * @param context
	 * @return
	 */
	public String getCacheFileAbsolutepath(Context context);
}
