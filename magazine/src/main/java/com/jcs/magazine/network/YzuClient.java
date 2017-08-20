package com.jcs.magazine.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jcs.magazine.base.BaseApplication;
import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.util.LocalFileManager;
import com.jcs.magazine.util.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：Jics
 * 2017/7/31 13:30
 *
 */
public class YzuClient {
//	public static final String HOST="http://s1m7444195.iask.in/magazine/";
	public static final String HOST="http://192.168.0.108:3000/";
//	public static final String HOST="http://192.168.191.1:3000/";
//	public static final String HOST="http://172.31.59.74:3000/";
	private static ApiService yzuServer;
	private static Retrofit retrofit;
	private YzuClient(){

	}
	static {


		Gson gson=new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.create();

/*		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (BuildConfig.DEBUG) {
			// Log信息拦截器
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
			//设置 Debug Log 模式
			builder.addInterceptor(loggingInterceptor);
		}*/

		OkHttpClient client = genericClient();

		retrofit=new Retrofit.Builder()
				.client(client)
				.baseUrl(HOST)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();
	}

	public static ApiService getInstance(){
		synchronized (YzuClient.class){
			if (yzuServer == null) {
				yzuServer=retrofit.create(ApiService.class);
			}
			return yzuServer;
		}
	}

	/**
	 * 带缓存的okhttp
	 * @return
	 */
	public static OkHttpClient genericClient() {

		//缓存路径
		File cacheFile = LocalFileManager.getInstance().getCacheDir(BuildConfig.APP_CACHE_DIR);
		Cache cache = new Cache(cacheFile, BuildConfig.RETROFIT_CACHE_SIZE);//缓存文件为10MB

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (BuildConfig.DEBUG) {
			// Log信息拦截器
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
			//设置 Debug Log 模式
			builder.addInterceptor(loggingInterceptor);
		}

		builder.addInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						Request request = chain.request();
						if (NetworkUtil.isConnectingToInternet(BaseApplication.getInstance())) {
							request= request.newBuilder()
									.cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
									.build();
						}else {
							request= request.newBuilder()
									.cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
									.build();
						}
						Response response = chain.proceed(request);
						if (NetworkUtil.isConnectingToInternet(BaseApplication.getInstance())) {
							response= response.newBuilder()
									.removeHeader("Pragma")
									// 有网络时 设置缓存超时时间1个小时
									.header("Cache-Control", "public, max-age=" + BuildConfig.CATCH_MAXAGE)
									.build();
						} else {
							response= response.newBuilder()
									.removeHeader("Pragma")
									// 无网络时，设置超时为4周
									.header("Cache-Control", "public, only-if-cached, max-stale=" + BuildConfig.CATCH_MAXSTALE)
									.build();
						}
						return response;
					}

				})
				.connectTimeout(BuildConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
				.cache(cache);
		return builder.build();
	}


}
