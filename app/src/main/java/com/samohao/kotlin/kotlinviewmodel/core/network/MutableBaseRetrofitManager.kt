package com.samohao.kotlin.kotlinviewmodel.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MutableBaseRetrofitManager {

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

    }

    internal fun <T> getRetrofitService(restClass: Class<T> , baseUrl : String): T {

        retofit = Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        return retofit.create(restClass)
    }

}