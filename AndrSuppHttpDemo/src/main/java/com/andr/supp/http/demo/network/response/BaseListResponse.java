package com.andr.supp.http.demo.network.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: 网络返回,List
 * @version V1.0.0
 */
public abstract class BaseListResponse<T> extends BaseHttpJsonResponse {

	/**表示返回json文件中需要取出的List Object对象的key，这个在网络协议中必须事先定义好，返回对象的协议都是统一的*/
	private static final String RESULT_OBJ = "result";
	
	private Class<T> clazz;

	private List<T> list;

	// 消息记录数
	public int total;

	public BaseListResponse(Class<T> clazz) {

		this.clazz = clazz;
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers,
			JSONObject mainJsonObject) throws Exception {

		JSONArray jsonArray = mainJsonObject.getJSONArray(RESULT_OBJ);

		list = new ArrayList<T>();
		if (null == jsonArray || 0 == jsonArray.size()) {
			// 返回空
		} else {
			for (int i = 0; i < jsonArray.size(); i++) {

				JSONObject jsonObject = jsonArray.getJSONObject(i);

				T t = JSON.toJavaObject(jsonObject, clazz);

				list.add(t);

			}
		}

		onSuccess(list);
		
		
		boolean isCache = false;
		if (null == headers) {
			// 缓存
			isCache = true;
		} else {
			// 网络
			isCache = false;
		}
		onSuccess(list, isCache);

	}

	public abstract void onSuccess(List<T> list) throws Exception;
	
	/**
	 * 
	 * @param list
	 * @param isCache true获取缓存返回，false获取网络返回
	 * @throws Exception
	 */
	public void onSuccess(List<T> list, boolean isCache) throws Exception {
	}

}
