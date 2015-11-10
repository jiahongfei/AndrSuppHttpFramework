package com.andr.supp.http.demo.network.tips;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.android.support.http.idialog.IDialogOnKeyCancel;
import com.android.support.http.idialog.INetworkWaitDialog;
/**
 * 链接网络时候的等待框，可以显示加载内容，点击返回按钮可以取消
 * @author jiahongfei
 *
 */
public class NetworkWaitDialog implements INetworkWaitDialog {

	private ProgressDialog mProgressDialog;
	private IDialogOnKeyCancel mIDialogOnKeyCancel;
	
	public NetworkWaitDialog(Context context) {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				if(null != mIDialogOnKeyCancel){
					mIDialogOnKeyCancel.onKeyCancelListener();
				}
			}
		});
	}
	
	@Override
	public void setMessage(String message) {
		mProgressDialog.setMessage(message);
	}

	@Override
	public void show() {
		mProgressDialog.show();
	}

	@Override
	public void cancel() {
		mProgressDialog.cancel();
	}

	@Override
	public void setOnKeyCancelListener(IDialogOnKeyCancel iDialogOnKeyCancel) {
		mIDialogOnKeyCancel = iDialogOnKeyCancel;
	}

}
