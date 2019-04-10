package com.samohao.kotlin.kotlinviewmodel

import android.app.Application
import android.content.Context
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
    }
}