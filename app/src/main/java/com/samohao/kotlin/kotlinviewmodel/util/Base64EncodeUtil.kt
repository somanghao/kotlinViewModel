package com.samohao.kotlin.kotlinviewmodel.util

import android.util.Base64
import java.lang.Exception


    class Base64EncodeUtil {
        companion object {

            fun encoder(input: String): String{
                val byteInput : ByteArray
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