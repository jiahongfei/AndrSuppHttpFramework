package com.andr.supp.http.demo.protocol;

import java.io.File;

import android.os.Environment;

import com.andr.supp.http.demo.network.request.BaseHttpRequest;
/**
 * 测试下载的请求
 * @author jiahongfei
 *
 */
public class TestDownloadRequest extends BaseHttpRequest {

	@Override
	public String getAbsoluteUrl() {
		return "downloadUrl";
	}

	@Override
	public File getTargetFile() {
		//这个是下载文件的绝对路径
		return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/testfile.file");
	}

	@Override
	public boolean getFileAppend() {
		//true表示追加到文件，断点下载的时候很有用
		//false表示覆盖文件
		return false;
	}
	
}
