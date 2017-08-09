package com.jcs.magazine.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jcs.magazine.config.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：Jics
 * 2017/7/31 13:30
 *
 */
public class YzuClient {
//	public static final String HOST="http://192.168.0.108:3000/";
	public static final String HOST="http://192.168.191.1:3000/";
//	public static final String HOST="http://172.31.59.74:3000/";
	private static ApiService yzuServer;
	private static Retrofit retrofit;
	private YzuClient(){

	}
	static {
		Gson gson=new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.create();

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (BuildConfig.DEBUG) {
			// Log信息拦截器
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
			//设置 Debug Log 模式
			builder.addInterceptor(loggingInterceptor);
		}

		OkHttpClient client = builder.build();

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

}
