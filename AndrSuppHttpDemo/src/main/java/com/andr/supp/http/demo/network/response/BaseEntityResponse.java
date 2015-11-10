package com.andr.supp.http.demo.network.response;

import org.apache.http.Header;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: 网络返回对象
 * @version V1.0.0
 */
public abstract class BaseEntityResponse<T> extends BaseHttpJsonResponse {

	/**表示返回json文件中需要取出的Object对象的key，这个在网络协议中必须事先定义好，返回对象的协议都是统一的*/
	private static final String RESULT_OBJ = "result";
	
	private Class<T> clazz;

	private T object;

	public BaseEntityResponse(Class<T> clazz) {

		this.clazz = clazz;
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers,
			JSONObject mainJsonObject) throws Exception {
		
		object = mainJsonObject.getObject(RESULT_OBJ, clazz);

		onSuccess(object);

		boolean isCache = false;
		if (null == headers) {
			// 缓存
			isCache = true;
		} else {
			// 网络
			isCache = false;
		}
		onSuccess(object, isCache);
	}

	public abstract void onSuccess(T t) throws Exception;
	/**
	 * 
	 * @param t
	 * @param isCache true获取缓存返回，false获取网络返回
	 * @throws Exception
	 */
	public void onSuccess(T t, boolean isCache) throws Exception {
	}

}
