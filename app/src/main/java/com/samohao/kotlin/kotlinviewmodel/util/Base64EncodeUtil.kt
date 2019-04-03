package com.samohao.kotlin.kotlinviewmodel.util

import android.content.Context
import android.util.Base64
import java.io.File
import java.lang.Exception

//    private const val TAG = "KOTLIN_TEST"
//    private const val KEY_AUTH = "KEY_AUTH"
//
//    fun setAuthCookie(context : Context , cookie : Set<String>) {
//        context.getSharedPreferences("KOTLIN_TEST" , Context.MODE_PRIVATE).edit().putStringSet(KEY_AUTH ,cookie)
//    }


    class Base64EncodeUtil {
        companion object {

            fun encoder(input: String): String{
                var byteInput : ByteArray
                var output : String
                try {
                    byteInput = input.toByteArray(Charsets.UTF_8)
                    output = String(Base64.encode(byteInput , Base64.DEFAULT) , Charsets.UTF_8)
                } catch (e : Exception) {
                    output = ""
                }
                return output
            }
        }
    }