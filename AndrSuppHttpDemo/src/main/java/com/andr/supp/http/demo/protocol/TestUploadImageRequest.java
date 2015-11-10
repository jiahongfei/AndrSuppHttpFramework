package com.andr.supp.http.demo.protocol;

import java.io.File;

import com.andr.supp.http.demo.network.request.BaseHttpRequest;
import com.loopj.android.http.RequestParams;

public class TestUploadImageRequest extends BaseHttpRequest {
	
	public TestUploadImageRequest(){
		
		RequestParams requestParams = new RequestParams();
//		requestParams.put("fileName", new File(""));
		//直接将文件File类传递给RequestParam使用的事Multipart上传
		setRequestParams(requestParams);
	}

	@Override
	public String getAbsoluteUrl() {
		return "upload_image_url";
	}

}
