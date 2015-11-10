package com.andr.supp.http.demo.protocol;

import com.andr.supp.http.demo.network.request.BaseHttpRequest;
import com.loopj.android.http.RequestParams;
/**
 * 有缓存的设置
 * @author jiahongfei
 *
 */
public class TestCacheRequest extends BaseHttpRequest {
	
	public TestCacheRequest() {
		//半小时刷新一次
		setCacheRuleTimeMillis(1*1000*60*30L);
		//如果要是有参数
		RequestParams requestParams = new RequestParams();
//		requestParams.put("key", "value");
		setRequestParams(requestParams);
	}

	@Override
	public String getAbsoluteUrl() {
		return ProtocolDef.URL_WEATHER;
	}
	
	@Override
	public int getCacheMode() {
		/**
		 * 先读取缓存，后链接网络，
		 * 根据规则getCacheRule()判断是否需要更新缓存，如果true链接网络获取数据刷新缓存，如果false不连接网络
		 */
		return CACHE_NETWORK;
		//可以变换下面三种形式
//		/**没有缓存*/
//		public static int NO_CACHE = 0;
//		/**先从网络获取，然后判断缓存规则（getCacheRule()），是否更新缓存，如果出错读取缓存*/
//		public static int NETWORK_CACHE = 1;
//		/**
//		 * 先读取缓存，后链接网络，
//		 * 根据规则getCacheRule()判断是否需要更新缓存，如果true链接网络获取数据刷新缓存，如果false不连接网络
//		 */
//		public static int CACHE_NETWORK = 2;
	}
	
	@Override
	public boolean isCacheResponseInCacheNetworkStatus() {
		//这个标志看接口中的文档
		return true;
	}

}
