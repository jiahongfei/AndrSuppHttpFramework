package com.andr.supp.http.demo.network.tips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.support.http.idialog.INetworkAlertDialog;

/**
 * 可以自定义弹出提示对话框的样式只要满足INetworkAlertDialog接口
 * 
 * @author jiahongfei
 * 
 */
public class NetworkAlertDialog implements INetworkAlertDialog {

	private AlertDialog.Builder mBuilder;

	public NetworkAlertDialog(Context context) {
		mBuilder = new AlertDialog.Builder(context);
	}

	@Override
	public void setTitle(String title) {
		mBuilder.setTitle(title);
	}

	@Override
	public void setMessage(String message) {
		mBuilder.setMessage(message);
	}

	@Override
	public void setLeftButton(String text, final OnClickListener leftOnClickListener) {
		mBuilder.setPositiveButton(text, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(null != leftOnClickListener){
					leftOnClickListener.onClick(null);
				}
			}
		});
	}

	@Override
	public void setRightButton(String text, final OnClickListener rightOnClickListener) {
		mBuilder.setNegativeButton(text, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(null != rightOnClickListener){
					rightOnClickListener.onClick(null);
				}
			}
		});
	}

	@Override
	public void show() {
		mBuilder.show();
	}

	@Override
	public void cancel() {
		mBuilder.create().cancel();
	}

}
