package com.android.support.http.fileutils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;

import android.content.Context;
import android.text.TextUtils;

import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;

/**
 * 
 * @Description: 用于保存网络缓存
 * @version V1.0.0
 */
public class FileCacheUtils {

	private static final Object FILE_CACHE_LOCK_OBJ = new Object();

	/**
	 * 根据IBaseRequest判断这个请求是否有数据
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @return true这个请求有缓存数据，false这个请求没有缓存数据
	 */
	public static boolean isNetworkCacheByRequest(Context context,
			IBaseRequest iBaseRequest) {
		synchronized (FILE_CACHE_LOCK_OBJ) {

			boolean result = false;
			try {
				byte[] responseBody = getNetworkCacheByte(context, iBaseRequest);
				if (null != responseBody && 0 != responseBody.length) {
					result = true;
				} else {
					result = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = false;

			}

			return result;

		}
	}

	/**
	 * 获取网络缓存，如果存在直接调用onResponse监听
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @param statusCode
	 * @param headers
	 * @param iBaseResponse
	 * @return
	 */
	public static boolean getNetworkCache(Context context,
			IBaseRequest iBaseRequest, int statusCode, Header[] headers,
			IBaseResponse iBaseResponse) {
		synchronized (FILE_CACHE_LOCK_OBJ) {
			boolean result = false;
			try {
				byte[] responseBody = getNetworkCacheByte(context, iBaseRequest);
				if (null != responseBody && 0 != responseBody.length) {
					if (null != iBaseResponse) {
						iBaseResponse.onResponse(statusCode, headers,
								responseBody);
					} else {

					}
					result = true;
				} else {
					result = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = false;

			}

			return result;
		}
	}

	/**
	 * 保存网络请求数据到指定文件
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @param responseBody
	 * @throws HttpResponseException
	 */
	public static void saveNetworkCache(Context context,
			IBaseRequest iBaseRequest, byte[] responseBody)
			throws HttpResponseException {
		synchronized (FILE_CACHE_LOCK_OBJ) {
			try {
				String filePathString = iBaseRequest
						.getCacheFileAbsolutepath(context);

				try {
					FileOutputStream fileOutputStream = new FileOutputStream(
							filePathString);
					try {
						fileOutputStream.write(responseBody, 0,
								responseBody.length);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							fileOutputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取网络缓存
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @return
	 */
	private static byte[] getNetworkCacheByte(Context context,
			IBaseRequest iBaseRequest) {
		synchronized (FILE_CACHE_LOCK_OBJ) {
			String filePathString = iBaseRequest
					.getCacheFileAbsolutepath(context);
			if (TextUtils.isEmpty(filePathString)) {
				return null;
			}
			File file = new File(filePathString);
			if (!file.exists()) {
				return null;
			}
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				byte[] buffer = new byte[4096];
				int len = -1;
				FileInputStream fileInputStream = new FileInputStream(
						filePathString);
				try {
					while (-1 != (len = fileInputStream.read(buffer))) {
						byteArrayOutputStream.write(buffer, 0, len);
					}
					byteArrayOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fileInputStream.close();
						byteArrayOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return byteArrayOutputStream.toByteArray();
		}
	}

}
