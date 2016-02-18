package com.andr.supp.http.demo.protocol;

import org.apache.http.HttpEntity;

import com.andr.supp.http.demo.network.request.BaseHttpRequest;
import com.loopj.android.http.RequestParams;
/**
 * post请求测试，缓存设置方式都是一样的，主要区别就是参数的设置，post传参数有两种方式
 * 1.RequestParam
 * 2.HttpEntity
 * @author jiahongfei
 *
 */
public class TestPostNetworkRequest extends BaseHttpRequest{

	public TestPostNetworkRequest() {
		RequestParams requestParams = new RequestParams();
//		requestParams.put("key", "value");
		setRequestParams(requestParams);
	}
	
	@Override
	public HttpEntity getPostRequestEntity() {
		//这是第二种方式，假如同时设置了，getPostRequestEntity有效
		return null;
	}
	
	@Override
	public String getAbsoluteUrl() {
		return ProtocolDef.URL_WEATHER;
	}

}
