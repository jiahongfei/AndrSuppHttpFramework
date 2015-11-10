package com.andr.supp.http.demo.network.response;

import org.apache.http.Header;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: 返回正确还是错误
 * @version V1.0.0
 */
public abstract class BaseSuccessResponse extends BaseHttpJsonResponse {

	@Override
	public void onSuccess(int statusCode, Header[] headers,
			JSONObject mainJsonObject) throws Exception {
		onSuccess();
	}

	public abstract void onSuccess() throws Exception;
}
