package com.android.support.http.networkhandler;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;

import android.content.Context;

import com.android.support.http.inetworklistener.IBaseErrorListener;
import com.android.support.http.inetworklistener.IBaseRequest;
import com.android.support.http.inetworklistener.IBaseResponse;
import com.android.support.http.inetworklistener.IBaseStateListener;
import com.loopj.android.http.AsyncHttpClient;
/**
 * 
* @Description: 用于从网络中下载大文件，保存在内存中会崩溃，例如：zip，rar压缩文件，视频文件等
* @version V1.0.0
 */
public class CustomFileAsyncHttpResponseHandler extends
		BaseAsyncHttpResponseHandler {

	protected final File mFile;
    protected final boolean append;
    
	public CustomFileAsyncHttpResponseHandler(final Context context,
			final IBaseRequest iBaseRequest, final IBaseResponse iBaseResponse,
			final IBaseStateListener iBaseStateListener,
			final IBaseErrorListener iBaseErrorListener) {
		super(context, iBaseRequest, iBaseResponse, iBaseStateListener, iBaseErrorListener);
		
		mFile = iBaseRequest.getTargetFile();
		this.append = iBaseRequest.getFileAppend();

	}


	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		// 返回结果
		try {
			if (null != mIBaseResponse) {
				mIBaseResponse.onResponse(statusCode, headers, getTargetFile());
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();

			sendFailureMessage(statusCode, headers, responseBody, e);

		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, final Throwable error) {
		try {
			if(getTargetFile().exists()){
				getTargetFile().delete();
			}
			
			if (null != mIBaseErrorListener) {
				int errCode = 0;
				try {
					//error + "@errcode=" + errcode   @后面是参数
					String tempError = error.getMessage();
					String errCodeString = getCustomErrorParam(tempError, "errcode");
					errCode = Integer.parseInt(errCodeString);
				} catch (Exception e) {
					e.printStackTrace();
					errCode = 0;
				}
				mIBaseErrorListener.onFailure(statusCode, errCode, headers, error);
			} else {
			}
			if (error instanceof HttpResponseException) {
				// 网络返回错误
				if (null != mIBaseErrorListener) {
					if (mIBaseErrorListener.isShowNetworkResponseError()) {
						showErrorToast(error);
					}
				} else {
					showErrorToast(error);
				}

			} else {
				// 网络连接错误
				if (null != mIBaseErrorListener) {
					if (mIBaseErrorListener.isShowNetworkConnectError()) {
						if (mIBaseErrorListener.isNetworkConnectErrorTipsMode()) {
							showNetworkConnectToast(error);
						} else {
							showErrorDialog(error);
						}
					}
				} else {
					showNetworkConnectToast(error);
				}
			}
			
		} catch (final Throwable e) {
			showErrorDialog( e);
		}

	}

	
	 /**
     * Retrieves File object in which the response is stored
     *
     * @return File file in which the response is stored
     */
    protected File getTargetFile() {
        assert (mFile != null);
        return mFile;
    }

    @Override
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream instream = entity.getContent();
            long contentLength = entity.getContentLength();
            FileOutputStream buffer = new FileOutputStream(getTargetFile(), this.append);
            if (instream != null) {
                try {
                    byte[] tmp = new byte[BUFFER_SIZE];
                    int l, count = 0;
                    // do not send messages if request has been cancelled
                    while ((l = instream.read(tmp)) != -1 && !Thread.currentThread().isInterrupted()) {
                        count += l;
                        buffer.write(tmp, 0, l);
                        sendProgressMessage(count, (int) contentLength);
                    }
                } finally {
                    AsyncHttpClient.silentCloseInputStream(instream);
                    buffer.flush();
                    AsyncHttpClient.silentCloseOutputStream(buffer);
                }
            }
        }
        return null;
    }


}
