package com.android.support.http.networkhandler;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.HttpHostConnectException;

import android.R.string;
import android.content.Context;
import android.text.TextUtils;

/**
 * 错误异常处理类
 * 
 * @author Administrator
 * 
 */
public class ErrorExceptionHandler {
//	100 Continue 初始的请求已经接受，客户应当继续发送请求的其余部分。
//	101 Switching Protocols 服务器将遵从客户的请求转换到另外一种协议
//	200 OK 一切正常，对GET和POST请求的应答文档跟在后面。
//	201 Created 服务器已经创建了文档，Location头给出了它的URL。
//	202 Accepted 已经接受请求，但处理尚未完成。
//	203 Non-Authoritative Information 文档已经正常地返回，但一些应答头可能不正确，因为使用的是文档的拷贝
//	204 No Content 没有新文档，浏览器应该继续显示原来的文档。如果用户定期地刷新页面，而Servlet可以确定用户文档足够新，这个状态代码是很有用的。
//	205 Reset Content 没有新的内容，但浏览器应该重置它所显示的内容。用来强制浏览器清除表单输入内容
//	206 Partial Content 客户发送了一个带有Range头的GET请求，服务器完成了它。
//	300 Multiple Choices 客户请求的文档可以在多个位置找到，这些位置已经在返回的文档内列出。如果服务器要提出优先选择，则应该在Location应答头指明。
//	301 Moved Permanently 客户请求的文档在其他地方，新的URL在Location头中给出，浏览器应该自动地访问新的URL。
//	302 Found 类似于301，但新的URL应该被视为临时性的替代，而不是永久性的。注意，在HTTP1.0中对应的状态信息是“Moved”
//	303 See Other 类似于301/302，不同之处在于，如果原来的请求是POST，Location头指定的重定向目标文档应该通过GET提取
//	304 Not Modified 客户端有缓冲的文档并发出了一个条件性的请求（一般是提供If-Modified-Since头表示客户只想比指定日期更新的文档）。服务器告诉客户，原来缓冲的文档还可以继续使用。
//	305 Use Proxy 客户请求的文档应该通过Location头所指明的代理服务器提取
//	307 Temporary Redirect 和302 （Found）相同。许多浏览器会错误地响应302应答进行重定向，即使原来的请求是POST，即使它实际上只能在POST请求的应答是
//	303时才能重定向。由于这个原因，HTTP 1.1新增了307，以便更加清除地区分几个状态代码：当出现303应答时，浏览器可以跟随重定向的GET和POST请求；如果是307应答，则浏览器只能跟随对GET请求的重定向。
//	400 Bad Request 请求出现语法错误。
//	401 Unauthorized 客户试图未经授权访问受密码保护的页面。应答中会包含一个WWW-Authenticate头，浏览器据此显示用户名字/密码对话框，然后在填写合适的Authorization头后再次发出请求。
//	403 Forbidden 资源不可用。服务器理解客户的请求，但拒绝处理它。通常由于服务器上文件或目录的权限设置导致。
//	404 Not Found 无法找到指定位置的资源。这也是一个常用的应答。
//	405 Method Not Allowed 请求方法（GET、POST、HEAD、DELETE、PUT、TRACE等）对指定的资源不适用。
//	406 Not Acceptable 指定的资源已经找到，但它的MIME类型和客户在Accpet头中所指定的不兼容
//	407 Proxy 类似于401，表示客户必须先经过代理服务器的授权。
//	408 Request Timeout 在服务器许可的等待时间内，客户一直没有发出任何请求。客户可以在以后重复同一请求。
//	409 Conflict 通常和PUT请求有关。由于请求和资源的当前状态相冲突，因此请求不能成功。
//	410 Gone 所请求的文档已经不再可用，而且服务器不知道应该重定向到哪一个地址。它和404的不同在于，返回407表示文档永久地离开了指定的位置，而404表示由于未知的原因文档不可用。
//	411 Length Require 服务器不能处理请求，除非客户发送一个Content-Length头。
//	412 Precondition Failed 请求头中指定的一些前提条件失败
//	413 Request Entity Too Large 目标文档的大小超过服务器当前愿意处理的大小。如果服务器认为自己能够稍后再处理该请求，则应该提供一个Retry-After头
//	414 Request URI Too Long URI太长 。
//	416 Requested Range Not Satisfiable 服务器不能满足客户在请求中指定的Range头。
//	500 Internal Server Error 服务器遇到了意料不到的情况，不能完成客户的请求。
//	501 Not Implemented 服务器不支持实现请求所需要的功能。例如，客户发出了一个服务器不支持的PUT请求。
//	502 Bad Gateway 服务器作为网关或者代理时，为了完成请求访问下一个服务器，但该服务器返回了非法的应答。
//	503 Service Unavailable 服务器由于维护或者负载过重未能应答。例如，Servlet可能在数据库连接池已满的情况下返回503。服务器返回503时可以提供一个Retry-After头。
//	504 Gateway Timeout 由作为代理或网关的服务器使用，表示不能及时地从远程服务器获得应答。
//	505 HTTP Version Not Supported 服务器不支持请求中所指明的HTTP版本。
//	在实际过程中比较有价值的是301/302重定向：

	/** 数据返回了，解析数据错误，包括（返回的数据不是Json，返回的数据提示错误等） */
	public static final int ERROR_CODE_ANALYTICAL = -100;
	/**错误监听，已经得到缓存*/
	public static final int ERROR_CODE_HAVE_CACHE = -200;

	public static final String ERR_NET_CONN = "你的网络不给力";
	public static final String ERR_NET_TIMEOUT = ERR_NET_CONN;
	public static final String ERR_ANALYTICAL = "解析数据错误";

	public ErrorExceptionHandler() {
	}

	/**
	 * 网络异常处理类
	 * 
	 * @param errorString
	 *            用于输出错误字符串
	 * @param error
	 */
	public synchronized static void NetworkExceptionHandler(
			StringBuffer errorString, Throwable error) {

		errorString.delete(0, errorString.length());

		if (null != error) {
			if (error instanceof HttpResponseException) {
				// 返回错误
				HttpResponseException errorResponse = (HttpResponseException) error;
				if (errorResponse.getStatusCode() == ERROR_CODE_ANALYTICAL) {
					String tempError = error.getMessage();
					try {
						//error + "@errcode=" + errcode   @后面是参数
						tempError = tempError.split("@")[0];
					} catch (Exception e) {
						e.printStackTrace();
						tempError = error.getMessage();
					}
					errorString.append(tempError);
				} else {
					errorString.append("网络返回错误");
				}
			} else if (error instanceof UnknownHostException) {
				// 主机名错误
				errorString.append(ERR_NET_CONN);
			} else if (error instanceof UnsupportedEncodingException) {
				// errorString.append("字符串解码错误");
				errorString.append(ERR_NET_CONN);

			} else if (error instanceof ConnectException) {
				// errorString.append("IP地址错误");
				errorString.append(ERR_NET_CONN);

				// 原因:指定ip地址的机器不能找到（也就是说从当前机器不存在到指定ip路由），
				// 或者是该ip存在，但找不到指定的端口进行监听。应该首先检查客户端的ip和port是否写错了，
				// 假如正确则从客户端ping一下服务器看是否能ping通，假如能ping通（服务服务器端把ping禁掉则需要另外的办法），
				// 则看在服务器端的监听指定端口的程序是否启动。
			} else if (error instanceof IllegalStateException) {
				// errorString.append("不是有效的URL地址");
				errorString.append(ERR_NET_CONN);

				// 1）同一个页面中再次调用response.sendRedirect()方法。
				// 2）提交的URL错误，即不是个有效的URL。
			} else if (error instanceof IllegalArgumentException) {
				// errorString.append("参数不正确");
				errorString.append(ERR_NET_CONN);
				// 抛出的异常表明向方法传递了一个不合法或不正确的参数。

			} else if (error instanceof SocketException) {
				errorString.append(ERR_NET_CONN);

				// 切换连接方式，返回false一般表明之前有切换尝试过，
				// 仍失败应该是手机当前没有可用连接,故返回错误回复.
			} else if (error instanceof ClientProtocolException) {
				// errorString.append("网络协议错误");
				errorString.append(ERR_NET_CONN);

				// 协议错误 在HTTP协议发出错误信号。
			} else if (error instanceof SocketTimeoutException) {
				errorString.append(ERR_NET_TIMEOUT);

				// 这个异常比较常见，socket超时。
				// 一般有2个地方会抛出这个，一个是connect的时候，
				// 这个超时参数由connect(SocketAddress endpoint,int timeout)中的后者来决定，
				// 还有就是setSoTimeout(int timeout)，这个是设定读取的超时时间。它们设置成0均表示无限大。
				// 超时
			} else if (error instanceof MalformedURLException) {
				// "No valid URI scheme was provided"
				// errorString.append("无效的URL");
				errorString.append(ERR_NET_CONN);

			} else if (error instanceof InterruptedIOException) {
				errorString.append(ERR_NET_CONN);
			} else if (error instanceof IOException) {
				errorString.append(ERR_NET_CONN);
			} else if (error instanceof Exception) {
				errorString.append(ERR_NET_CONN);
			} else if (error instanceof Throwable) {
				errorString.append(ERR_NET_CONN);
			} else {
				errorString.append(ERR_NET_CONN);
			}
			error.printStackTrace();
		} else {

		}

	}

}
