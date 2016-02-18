package com.android.support.http.networkutils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;

/**
 * @version V1.0.0
 * @Description: 同步网络工具类，阻塞UI线程，用来调用网络库，弹出交互对话框等
 */
public class SyncNetworkUtils extends BaseNetwork {

    private static SyncNetworkUtils sNetworkUtils = null;

    /**
     * 网络工具类,单例
     *
     * @return
     */
    public static SyncNetworkUtils getSyncNetworkUtils() {
        if (null == sNetworkUtils) {
            newInstance();
        }
        return sNetworkUtils;
    }

    private static synchronized void newInstance() {
        if (null == sNetworkUtils) {
            sNetworkUtils = new SyncNetworkUtils();
        }
    }

    private SyncNetworkUtils(){
        this(false);
    }

    private SyncNetworkUtils(boolean fixNoHttpResponseException) {
        AsyncHttpClient asyncHttpClient = new SyncHttpClient(fixNoHttpResponseException, 80, 443);
        setAsyncHttpClient(asyncHttpClient);
    }


}
