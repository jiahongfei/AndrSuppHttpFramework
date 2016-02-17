package com.android.support.http.fileutils;

import java.io.File;

import com.android.support.http.fileutils.naming.Md5FileNameGenerator;
import com.android.support.http.inetworklistener.IBaseRequest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

/**
 * 文件路径工具类
 * 
 * @author jiahongfei
 * 
 */
public class FilePathUtils {

	/**app缓存root目录,没有前面的Environment.getExternalStorageDirectory()*/
	public static final String APP_CACHE_ROOT_PATH = "Android/data/";

	/**
	 * {@link #getExternalStorageState()} returns MEDIA_MOUNTED if the media is
	 * present and mounted at its mount point with read/write access.
	 */
	private static final String MEDIA_MOUNTED = "mounted";
	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

	/**
	 * 获取缓存路径
	 * 
	 * @param context
	 * @param iBaseRequest
	 * @return
	 */
	public static String getCacheFileAbsolutepath(Context context,
			IBaseRequest iBaseRequest) {
		Md5FileNameGenerator md5FileNameGenerator = new Md5FileNameGenerator();
		String fileName = md5FileNameGenerator.generate(iBaseRequest
				.getAbsoluteUrl()
				+ ((null == iBaseRequest.getRequestParamsCacheKey()) ? ""
						: iBaseRequest.getRequestParamsCacheKey()));
		String cachePathString = "cache/network";
		File file = getCachePath(context, cachePathString);
		return file.getAbsolutePath() + "/" + fileName;
	}

	/**
	 * 判断是否有读写权限
	 * 
	 * @param context
	 * @return
	 */
	private static boolean hasExternalStoragePermission(Context context) {
		int perm = context
				.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * 根据dir参数在缓存根路径上创建文件夹
	 *
	 * @param context
	 * @param dir
	 *            例如:cache/network
	 * @return
	 */
	public final static File getCachePath(Context context, String dir) {
		String packageName = context.getApplicationInfo().packageName;
		return getCacheDirectory(context,
				APP_CACHE_ROOT_PATH + packageName + "/" + dir);

	}

//	/**
//	 * 根据dir参数在缓存根路径上创建文件夹
//	 *
//	 * @param context
//	 * @param dir
//	 *            例如:cache/network
//	 * @return
//	 */
//	private final static File getCachePath(Context context, String dir) {
//		String appCacheRootPath = context.getApplicationInfo().packageName
//				.replace(".", "/");
//		return getCacheDirectory(context, appCacheRootPath + "/" + dir);
//
//	}

	/**
	 * Returns specified application cache directory. Cache directory will be
	 * created on SD card by defined path if card is mounted and app has
	 * appropriate permission. Else - Android defines cache directory on
	 * device's file system.
	 * 
	 * @param context
	 *            Application context
	 * @param cacheDir
	 *            Cache directory path (e.g.: "AppCacheDir",
	 *            "AppDir/cache/images")
	 *            如果有SD卡返回Environment.getExternalStorageDirectory() + cacheDir
	 *            没有SD卡或没有权限写SD卡返回 context.getCacheDir()
	 * @return Cache {@link File directory}
	 */
	private static File getCacheDirectory(Context context, String cacheDir) {
		File appCacheDir = null;
		if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				&& hasExternalStoragePermission(context)) {
			appCacheDir = new File(Environment.getExternalStorageDirectory(),
					cacheDir);
		}
		if (appCacheDir == null
				|| (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}

}
