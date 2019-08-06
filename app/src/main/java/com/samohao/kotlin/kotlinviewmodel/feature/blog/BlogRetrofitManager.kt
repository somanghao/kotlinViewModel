package com.samohao.kotlin.kotlinviewmodel.feature.blog

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BlogRetrofitManager {

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
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        return retofit.create(restClass)
    }

//    private class AddCookiesInterceptor : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val chainRequest = chain.request()
//            val request = chainRequest.newBuilder().apply {
//                val userCookie = PrefererenceHelper.getAuthCookie(GlobalApplication.getContext())
//                if(userCookie != null) {
//                    for(cookie in userCookie) {
//                        addHeader("Cookie", cookie)
//                    }
//                    removeHeader("User-Agent").addHeader("User-Agent", "Android")
//                }
//            }.build()
//
//            return chain.proceed(request)
//        }
//    }

//    private class ReceivedCookiesInterceptor : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val originalResponse = chain.proceed(chain.request())
//            if(!originalResponse.headers("Set-Cookie").isEmpty()) {
//                val userCookie :MutableSet<String>? = mutableSetOf()
//
//                for(header in originalResponse.headers("Set-Cookie")) {
//                    userCookie?.add(header)
//                }
//                if(userCookie != null)
//                    PrefererenceHelper.setAuthCookie(GlobalApplication.getContext() , userCookie.toHashSet())
//            }
//            return originalResponse
//        }
//    }

}