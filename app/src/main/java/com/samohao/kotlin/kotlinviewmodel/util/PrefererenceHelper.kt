package com.samohao.kotlin.kotlinviewmodel.util

import android.content.Context

//    private const val TAG = "KOTLIN_TEST"
//    private const val KEY_AUTH = "KEY_AUTH"
//
//    fun setAuthCookie(context : Context , cookie : Set<String>) {
//        context.getSharedPreferences("KOTLIN_TEST" , Context.MODE_PRIVATE).edit().putStringSet(KEY_AUTH ,cookie)
//    }


    class PrefererenceHelper {
        companion object {
            private const val TAG = "KOTLIN_TEST"
            private const val KEY_AUTH = "KEY_AUTH"

            fun setAuthCookie(context : Context , cookie : HashSet<String>?) {
                context.getSharedPreferences(TAG , Context.MODE_PRIVATE).edit().putStringSet(KEY_AUTH ,cookie)
            }

            fun getAuthCookie(context : Context) : MutableSet<String>? {
                return context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getStringSet(KEY_AUTH , null)
            }
        }
    }