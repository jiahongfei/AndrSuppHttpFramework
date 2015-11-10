package com.android.support.http.idialog;
/**
 * 自定义网络等待对话框
 * @author jiahongfei
 *
 */
public interface INetworkWaitDialog{
	
	public abstract void setMessage(String message);

	public abstract void setOnKeyCancelListener(IDialogOnKeyCancel iDialogOnKeyCancel);
	
	public abstract void show();
	
	public abstract void cancel();

}
