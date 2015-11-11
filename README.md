# AndrSuppHttpFramework
针对android平台，对async-http-client网络库进行封装，使其方便调用，在保证原来库功能不变的基础上，主要添加对网络请求的本地缓存功能，将网络返回的文本保存到本地，有多种模式可以选择（先读取缓存后连接网络，先连接网络如果失败后获取缓存），下次没有网络或者没有到规定的时间不会去连接网络，而是直接用本地的缓存。（android网络缓存库）已经过测试可以直接用，有详细的demo

#特点
1.对async-http-client库进行封装，支持网络返回文本数据的缓存功能（支持两种缓存模式）<br>
2.使用方便直接继承BaseNetworkActivity OR BaseNetworkFragmentActivity就可以用网络方法<br>
3.可扩展性强继承IBaseRequest接口实现自己的请求（可以设置网络等待对话框，错误提示对话框，Header，缓存模式等）<br>
4.返回监听很好的结合了fastjson来处理返回结果<br>
4.调用方便只需要一行就可以发送请求接收返回数据<br>

#例子
查看工程中的Demo里面有很详细的介绍，下面只展示出来一个 接口返回的Json是实体类的形式

		GetRequest(new TestNetworkRequest(),
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
