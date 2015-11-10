package com.andr.supp.http.demo.network.response;

import java.io.File;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.networkhandler.ErrorExceptionHandler;

/**
 * 
 * @Description: 用来监听网络返回,返回文本等文件
 * @version V1.0.0
 */
public abstract class BaseHttpJsonResponse implements IBaseResponse {

	public static final String HEADER_CONTENT_CHARSET = "charset";

	private String charset = "UTF-8";

	@Override
	public void onResponse(int statusCode, Header[] headers, byte[] responseBody)
			throws Exception {

		if (null != headers){
			//根据返回的header取出字符集
			charset = getHeadersCharset(headers);
		}

		//将返回的byte数组转换成字符串
		String responseString = new String(responseBody, charset);
		
		Log.e("","response string : " + responseString);
		//使用fastjson解析，但是在定义实体类的时候不能定义基本类型，例如int用Integer代替
		JSONObject mainJsonObject = checkJson(responseString);
		if (null == mainJsonObject) {
			// 不是json数据
			throw new HttpResponseException(ErrorExceptionHandler.ERROR_CODE_ANALYTICAL, ErrorExceptionHandler.ERR_ANALYTICAL);
		}
		
		//如果返回的json格式没问题，下面解析json格式判断返回的结果是否是正确的
		String error = checkResponse(mainJsonObject);
		if (null != error) {
			// 返回数据错误
			String errcode = mainJsonObject.getString("errcode");
			throw new HttpResponseException(ErrorExceptionHandler.ERROR_CODE_ANALYTICAL, error + "@errcode=" + errcode);
		}
		
//		String status = mainJsonObject.getString("status");
//		if ("0".equals(status)) {
//			// 成功返回
//		} else if ("1".equals(status)) {
//			// 失败返回
//		} else if ("2".equals(status)) {
//			// 空结果
//		}
		int resultCode = 0;
//		try {
//			resultCode = Integer.parseInt(status);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			onSuccess(resultCode, headers, mainJsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpResponseException(ErrorExceptionHandler.ERROR_CODE_ANALYTICAL, ErrorExceptionHandler.ERR_ANALYTICAL);
		}

	}

	private String getHeadersCharset(Header[] headers) {
		String result = null;
		jump: for (Header header : headers) {
			for (HeaderElement headerElement : header.getElements()) {
				for (NameValuePair nameValuePair : headerElement
						.getParameters()) {
					if (HEADER_CONTENT_CHARSET.equals(nameValuePair.getName())) {
						result = nameValuePair.getValue();
						break jump;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 判断是否是json格式
	 * 
	 * @param responseString
	 * @return null不是json格式
	 */
	private JSONObject checkJson(String responseString) {
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.parseObject(responseString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	// 成功返回
	// { "result": { "c1": "" }, "status": "0" }
	// 空结果
	// { "status": "2" }
	// 失败返回
	// { "status": "1", "errmsg": "请求参数错误 t1:不能为null", "errcode": "100" }

	/**
	 * 判断返回数据是否正确，
	 * 
	 * @param jsonObject
	 * @return 返回错误提示信息
	 */
	private String checkResponse(JSONObject jsonObject) {
		String result = null;
//		String status = jsonObject.getString("status");
//		if ("0".equals(status)) {
//			// 成功返回
//		} else if ("1".equals(status)) {
//			// 失败返回
//			result = jsonObject.getString("errmsg");
//		} else if ("2".equals(status)) {
//			// 空结果
//		}
		return result;
	}

	@Override
	public void onResponse(int statusCode, Header[] headers, File file)
			throws Exception {

	}

	/**
	 * 
	 * @param statusCode
	 *            0成功，2空,
	 * @param headers
	 * @param mainJsonObject
	 * @throws Exception
	 */
	public abstract void onSuccess(int statusCode, Header[] headers,
			JSONObject mainJsonObject) throws Exception;

}
