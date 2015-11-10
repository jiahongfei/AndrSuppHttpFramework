package com.android.support.http.networkutils.activity.networkhelper;

import com.android.support.http.inetworklistener.IBaseErrorListener;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.inetworklistener.IBaseStateListener;
import com.loopj.android.http.RequestHandle;

/**
 * 
 * @Description: 
 *               基类暴露的接口，方便修改代码，BaseActivity、BaseFragment、BaseFragmentActivity等积累中实现
 * @version V1.0.0
 */
public interface INetworkActivityHelper {

	/********************** GET REQUEST START **********************/
	/**
	 * GET方式请求，状态监听和错误监听都自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @param iBaseErrorListener
	 * @return
	 */
	public RequestHandle GetRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse, IBaseStateListener iBaseStateListener,
			IBaseErrorListener iBaseErrorListener);

	/**
	 * GET方式请求，状态监听自定义，错误监听默认
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @return
	 */
	public RequestHandle GetRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse, IBaseStateListener iBaseStateListener);

	/**
	 * GET方式请求，错误监听默认，状态监听自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseErrorListener
	 * @return
	 */
	public RequestHandle GetRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse, IBaseErrorListener iBaseErrorListener);

	/**
	 * GET方式请求，状态监听和错误监听都默认（状态监听弹出等待框，错误监听弹出toast）
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public RequestHandle GetRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse);

	/**
	 * GET方式请求，忽略状态监听和错误监听
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public RequestHandle GetRequestIgnoreListener(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse);

	/********************** GET REQUEST END **********************/

	/********************** POST REQUEST START **********************/

	/**
	 * POST方式请求，状态监听和错误监听都自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @param iBaseErrorListener
	 * @return
	 */
	public RequestHandle PostRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse, IBaseStateListener iBaseStateListener,
			IBaseErrorListener iBaseErrorListener);

	/**
	 * POST方式请求，状态监听自定义，错误监听默认
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseStateListener
	 * @return
	 */
	public RequestHandle PostRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse, IBaseStateListener iBaseStateListener);

	/**
	 * POST方式请求，错误监听默认，状态监听自定义
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseErrorListener
	 * @return
	 */
	public RequestHandle PostRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse, IBaseErrorListener iBaseErrorListener);

	/**
	 * POST方式请求，状态监听和错误监听都默认（状态监听弹出等待框，错误监听弹出toast）
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public RequestHandle PostRequest(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse);

	/**
	 * POST方式请求，忽略状态监听和错误监听
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @return
	 */
	public RequestHandle PostRequestIgnoreListener(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse);
	
	/**
	 * POST方式请求，忽略状态监听
	 * 
	 * @param iBaseRequest
	 * @param iBaseResponse
	 * @param iBaseErrorListener
	 * @return
	 */
	public RequestHandle PostRequestIgnoreStateListener(IBaseRequest iBaseRequest,
			IBaseResponse iBaseResponse ,IBaseErrorListener iBaseErrorListener);

	/********************** POST REQUEST END **********************/

	/**
	 * 取消当前activity所有的请求
	 */
	public void cancelCurrentActivityAllRequest();

}
