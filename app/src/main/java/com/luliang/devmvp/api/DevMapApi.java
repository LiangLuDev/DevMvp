package com.luliang.devmvp.api;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.luliang.devmvp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liang_Lu on 2017/8/31.
 */

public class DevMapApi {
    private static Retrofit mRetrofit;

    /**
     * Retrofit初始化
     *
     * @return
     */
    public static Retrofit createApi() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
//        httpClientBuilder.addInterceptor(new LoggingInterceptor.Builder()
//                .setLevel(Level.BODY)
//                .log(Platform.INFO)
//                .request("Request")
//                .response("Response")
//                .addHeader("version", BuildConfig.VERSION_NAME)
//                .build());
        mRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();
        return mRetrofit;
    }
}
