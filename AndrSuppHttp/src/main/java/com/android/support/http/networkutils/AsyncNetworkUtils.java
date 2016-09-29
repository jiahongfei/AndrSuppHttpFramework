package com.android.support.http.networkutils;

import com.loopj.android.http.AsyncHttpClient;

/**
 * @version V1.0.0
 * @Description: 异步网络工具类，用来调用网络库，弹出交互对话框等
 */
public class AsyncNetworkUtils extends BaseNetwork {

    private static AsyncNetworkUtils sNetworkUtils = null;

    /**
     * 网络工具类,单例
     *
     * @return
     */
    public static AsyncNetworkUtils getAsyncNetworkUtils() {
        if (null == sNetworkUtils) {
            newInstance(false);
        }
        return sNetworkUtils;
    }

    /**
     * 网络工具类,单例
     *
     * @return
     */
    public static AsyncNetworkUtils getAsyncNetworkUtils(boolean fixNoHttpResponseException) {
        if (null == sNetworkUtils) {
            newInstance(fixNoHttpResponseException);
        }
        return sNetworkUtils;
    }

    private static synchronized void newInstance(boolean fixNoHttpResponseException) {
        if (null == sNetworkUtils) {
            sNetworkUtils = new AsyncNetworkUtils(fixNoHttpResponseException);
        }
    }

    /**
     * 构造函数
     */
    public AsyncNetworkUtils(boolean fixNoHttpResponseException) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(fixNoHttpResponseException, 80, 443);
        setAsyncHttpClient(asyncHttpClient);
    }

}
