package com.example.qiu.bookstore.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qiu on 2017/12/23.
 */

public class RRetrofit {
    public static String BASE_URL = "http://120.25.229.177:8080";
    public static <T> T create(final Class<T> cls) {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(BASE_URL)//注意此处,设置服务器的地址
                .addConverterFactory(GsonConverterFactory.create())//用于Json数据的转换,非必须
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//用于返回Rxjava调用,非必须
                .build();
        return retrofit.create(cls);
    }
}
