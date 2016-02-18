package com.andr.supp.http.demo;

import java.io.File;

import org.apache.http.Header;

import com.alibaba.fastjson.JSONObject;
import com.andr.supp.http.demo.network.request.BaseHttpRequest;
import com.andr.supp.http.demo.network.response.BaseEntityResponse;
import com.andr.supp.http.demo.network.response.BaseHttpJsonResponse;
import com.andr.supp.http.demo.network.response.BaseSuccessResponse;
import com.andr.supp.http.demo.protocol.TestCacheRequest;
import com.andr.supp.http.demo.protocol.TestDownloadRequest;
import com.andr.supp.http.demo.protocol.TestNetworkRequest;
import com.andr.supp.http.demo.protocol.TestPostNetworkRequest;
import com.andr.supp.http.demo.protocol.TestUploadImageRequest;
import com.andr.supp.http.demo.protocol.entity.WeatherInfo;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.networkhandler.BaseErrorListener;
import com.android.support.http.networkhandler.BaseHttpFileResponse;
import com.android.support.http.networkhandler.BaseStateListener;
import com.android.support.http.networkutils.AsyncNetworkUtils;
import com.android.support.http.networkutils.activity.BaseNetworkActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 测试http,继承BaseNetworkActivity可以使用网络方法
 * 
 * @author jiahongfei
 * 
 */
public class MainActivity extends BaseNetworkActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 所有的返回监听，状态监听，错误监听都不是线程安全的，需要在Handler更新UI

		 testNetwork();

		// testCacheNetwork();

		// testPostNetwork();

		// testDownloadNetwork();

		// testUploadImageData();
	}

	private void testHttps(){
		//https需要自己创建AsyncNetworkUtils
//		AsyncNetworkUtils asyncNetworkUtils = new AsyncNetworkUtils(true);
//		asyncNetworkUtils.GetRequest()
	}

	private void testUploadImageData() {
		// 上传照片接口
		PostRequest(new TestUploadImageRequest(), new BaseSuccessResponse() {

			@Override
			public void onSuccess() throws Exception {

			}
		}, new BaseStateListener() {
			@Override
			public void onStart() {
				// 弹出进度条框
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// 显示上传进度
			}

			@Override
			public void onFinish() {
				// 取消进度条框
			}
		});

	}

	private void testDownloadNetwork() {
		// 测试下载，需要自己在TestDownloadRequest添加一个url
		// 下载注册BaseStateListener监听就没有默认的等待框了，需要自己添加进度条
		GetRequest(new TestDownloadRequest(), new BaseHttpFileResponse() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, File file)
					throws Exception {
				// file返回的文件类
			}
		}, new BaseStateListener() {
			@Override
			public void onStart() {
				// 弹出进度条框
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				// 显示下载进度
			}

			@Override
			public void onFinish() {
				// 取消进度条框
			}
		});
	}

	private void testPostNetwork() {
		PostRequest(new TestPostNetworkRequest(),
				new BaseEntityResponse<WeatherInfo>(WeatherInfo.class) {

					@Override
					public void onSuccess(WeatherInfo t) throws Exception {
						// 正确的返回处理
						Log.e("", "succeed");
					}
				}, new BaseErrorListener(true, true, true) {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable error) {
						// 错误时返回处理，两种错误
						// 1.网络链接错误（没有网络、超时等）
						// 2.返回的数据错误（解析错误、服务器返回错误结果）
					}
				});
	}

	private void testCacheNetwork() {
		// 先获取本地缓存，如果没有缓存链接网络获取数据，然后保存本地
		GetRequest(new TestCacheRequest(), new BaseEntityResponse<WeatherInfo>(
				WeatherInfo.class) {

			@Override
			public void onSuccess(WeatherInfo t) throws Exception {
				// 正确的返回处理
				Log.e("", "succeed");
			}
		}, new BaseErrorListener(true, true, true) {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable error) {
				// 错误时返回处理，两种错误
				// 1.网络链接错误（没有网络、超时等）
				// 2.返回的数据错误（解析错误、服务器返回错误结果）
			}
		});
	}

	private void testNetwork() {
		// 接口返回的是实体类的形式
		GetRequest(new TestNetworkRequest(),
				new BaseEntityResponse<WeatherInfo>(WeatherInfo.class) {

					@Override
					public void onSuccess(WeatherInfo t) throws Exception {
						// 正确的返回处理
						Log.e("", "succeed");
						mHandler.sendEmptyMessage(0);
					}
				}, new BaseErrorListener(true, true, true) {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable error) {
						// 错误时返回处理，两种错误
						// 1.网络链接错误（没有网络、超时等）
						// 2.返回的数据错误（解析错误、服务器返回错误结果）
						mHandler.sendEmptyMessage(1);
					}
				});
	}

	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what){
				case 0: {
					Toast.makeText(getApplicationContext(), "succeed", Toast.LENGTH_SHORT).show();
					break;
				}
				case 1:{
					Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
					break;
				}
			}
			return false;
		}
	});
}
