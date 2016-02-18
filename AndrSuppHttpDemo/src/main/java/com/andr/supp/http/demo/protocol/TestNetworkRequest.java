package com.andr.supp.http.demo.protocol;

import com.andr.supp.http.demo.network.request.BaseHttpRequest;
/**
 * 测试只是网络访问
 * @author jiahongfei
 *
 */
public class TestNetworkRequest extends BaseHttpRequest {

	@Override
	public String getAbsoluteUrl() {
		//天气预报接口返回Json
		return ProtocolDef.URL_WEATHER;
//		return "http://www.baidu.com/";
	}

}
