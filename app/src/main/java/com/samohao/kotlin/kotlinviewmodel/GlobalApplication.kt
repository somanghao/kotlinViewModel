package com.samohao.kotlin.kotlinviewmodel

import android.app.Application
import android.content.Context

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
    }
}