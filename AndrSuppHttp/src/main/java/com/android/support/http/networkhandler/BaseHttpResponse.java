package com.android.support.http.networkhandler;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;

/**
 * 网络返回的基类
 * @author jiahongfei
 *
 */
public abstract class BaseHttpResponse {

	public static final String HEADER_CONTENT_CHARSET = "charset";

	protected String charset = "UTF-8";
	
	/**
	 * 获取Header的Value通过Key
	 * @param headers
	 * @param headerKey
	 * @return
	 */
	protected String getHeaderValueByKey(Header[] headers, String headerKey) {
		String result = null;
		jump: for (Header header : headers) {
			for (HeaderElement headerElement : header.getElements()) {
				for (NameValuePair nameValuePair : headerElement
						.getParameters()) {
					if (headerKey.equals(nameValuePair.getName())) {
						result = nameValuePair.getValue();
						break jump;
					}
				}
			}
		}
		return result;
	}
	
	
	protected String getHeadersCharset(Header[] headers) {
		return getHeaderValueByKey(headers, HEADER_CONTENT_CHARSET);
	}
}
