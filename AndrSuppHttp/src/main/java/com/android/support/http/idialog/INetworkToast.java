package com.android.support.http.idialog;

import android.content.Context;

/**
 * 网络Toast提示的接口
 * @author jiahongfei
 *
 */
public interface INetworkToast {

	public INetworkToast makeText(Context context, CharSequence text,
			int duration);
	
	public void show();
	
}
