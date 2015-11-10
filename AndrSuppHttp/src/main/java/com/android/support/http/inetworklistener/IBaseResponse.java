package com.android.support.http.inetworklistener;

import java.io.File;

import org.apache.http.Header;

public interface IBaseResponse {
	
	/**
	 * 用于返回文本，在内存中存储
	 * @param statusCode
	 * @param headers
	 * @param responseBody
	 * @throws Exception
	 */
	public void onResponse(int statusCode, Header[] headers, byte[] responseBody) throws Exception;
	
	/**
	 * 用于返回文件，保存在硬盘上
	 * @param statusCode
	 * @param headers
	 * @param file
	 * @throws Exception
	 */
	public void onResponse(int statusCode, Header[] headers, File file) throws Exception;
	
}
