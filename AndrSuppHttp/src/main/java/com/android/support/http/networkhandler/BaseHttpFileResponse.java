package com.android.support.http.networkhandler;

import java.io.File;

import org.apache.http.Header;

import com.android.support.http.inetworklistener.IBaseResponse;

/**
 * 
* @Description: 下载文件返回的基类
* @version V1.0.0
 */
public abstract class BaseHttpFileResponse implements IBaseResponse {

	@Override
	public void onResponse(int statusCode, Header[] headers, File file)
			throws Exception {
		onSuccess(statusCode, headers, file);

	}

	@Override
	public void onResponse(int statusCode, Header[] headers, byte[] responseBody)
			throws Exception {

	}

	public abstract void onSuccess(int statusCode, Header[] headers, File file)
			throws Exception;

}
