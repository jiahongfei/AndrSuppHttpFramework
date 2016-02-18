package com.andr.supp.http.demo.network.tips;

import android.content.Context;
import android.widget.Toast;

import com.android.support.http.idialog.INetworkToast;
/**
 * 这里可以自定义自己样式的Toast，只要满足INetworkToast这个接口就可以弹出来,但是有个限制，必须是自动消失的
 * @author jiahongfei
 *
 */
public class NetworkToast implements INetworkToast {

	private Toast mToast;
	
	public NetworkToast(Context context) {
	}
	
	@Override
	public INetworkToast makeText(Context context, CharSequence text, int duration) {
		mToast = Toast.makeText(context, text, duration);
		return this;
	}
	
	public void show() {
		mToast.show();
	}

}
