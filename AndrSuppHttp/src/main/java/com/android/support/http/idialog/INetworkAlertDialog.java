package com.android.support.http.idialog;

import android.app.Dialog;
import android.view.View.OnClickListener;
/**
 * 自定义网络提示对话框
 * @author jiahongfei
 *
 */
public interface INetworkAlertDialog {
	
	public abstract void setTitle(String title);
	
	public abstract void setMessage(String message);

	public abstract void setLeftButton(String text, OnClickListener leftOnClickListener);
	
	public abstract void setRightButton(String text, OnClickListener rightOnClickListener);
	
	public abstract void show();
	
	public abstract void cancel();
}
