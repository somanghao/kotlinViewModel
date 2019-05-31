package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.weather

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WeatherRetrofitManager {

    private var okHttpClient: OkHttpClient
    private lateinit var retofit: Retrofit

    init {
        val httpLogging = HttpLoggingInterceptor();
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
//            addInterceptor(AddCookiesInterceptor())
//            addInterceptor(ReceivedCookiesInterceptor())
            readTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
        }.build()

        retofit = Retrofit.Builder().apply {
            baseUrl("http://api.openweathermap.org/")
            client(okHttpClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    internal fun <T> getRetrofitService(restClass: Class<T>): T {
        return retofit.create(restClass)
    }

}