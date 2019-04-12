package com.samohao.kotlin.kotlinviewmodel

import android.app.Application
import android.content.Context
import com.kakao.auth.*
import com.samohao.kotlin.kotlinviewmodel.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GlobalApplication : Application() {
    companion object {
        lateinit var instance : Application
            private set
        fun getContext() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@GlobalApplication)
            modules(appModule)
        }
        KakaoSDK.init(object : KakaoAdapter() {
            override fun getApplicationConfig(): IApplicationConfig {
                return object : IApplicationConfig {
                    override fun getApplicationContext(): Context {
                        return GlobalApplication.getContext()
                    }
                }
            }
        })
    }
}