package com.samohao.kotlin.kotlinviewmodel.core.network

import com.samohao.kotlin.kotlinviewmodel.GlobalApplication
import com.samohao.kotlin.kotlinviewmodel.util.PrefererenceHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {

    private var okHttpClient: OkHttpClient
    private var retofit: Retrofit

    init {
        val httpLogging = HttpLoggingInterceptor();
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
            addInterceptor(AddCookiesInterceptor())
            addInterceptor(ReceivedCookiesInterceptor())
            readTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
        }.build()

        retofit = Retrofit.Builder().apply {
            baseUrl("http://www.daum.net/")
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    internal fun <T> getRetrofitService(restClass: Class<T>): T {
        return retofit.create(restClass)
    }

    private class AddCookiesInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val chainRequest = chain.request()
            val request = chainRequest.newBuilder().apply {
                val userCookie = PrefererenceHelper.getAuthCookie(GlobalApplication.getContext())
                if(userCookie != null) {
                    for(cookie in userCookie) {
                        addHeader("Cookie", cookie)
                    }
                    removeHeader("User-Agent").addHeader("User-Agent", "Android")
                }
            }.build()

            return chain.proceed(request)
        }
    }

    private class AddCookiesInterceptor1 : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            val userCookie = PrefererenceHelper.getAuthCookie(GlobalApplication.getContext())
            if(userCookie != null) {
                for(cookie in userCookie) {
                    builder.addHeader("Cookie", cookie)
                }
            }

            return chain.proceed(builder.build())
        }
    }

    private class ReceivedCookiesInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse = chain.proceed(chain.request())
            if(!originalResponse.headers("Set-Cookie").isEmpty()) {
                val userCookie :MutableSet<String>? = mutableSetOf()

                for(header in originalResponse.headers("Set-Cookie")) {
                    userCookie?.add(header)
                }
                if(userCookie != null)
                    PrefererenceHelper.setAuthCookie(GlobalApplication.getContext() , userCookie.toHashSet())
            }
            return originalResponse
        }
    }

}