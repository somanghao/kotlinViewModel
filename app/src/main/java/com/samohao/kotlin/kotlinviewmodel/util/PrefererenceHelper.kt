package com.samohao.kotlin.kotlinviewmodel.util

import android.content.Context
import com.google.gson.Gson
import com.samohao.kotlin.kotlinviewmodel.data.MemberVo

    class PrefererenceHelper {
        companion object {
            private const val TAG = "KOTLIN_TEST"
            private const val KEY_AUTH = "KEY_AUTH"
            private const val KEY_MEMBER = "KEY_MEMBER"

            fun setAuthCookie(context : Context , cookie : HashSet<String>?) {
                context.getSharedPreferences(TAG , Context.MODE_PRIVATE).edit().putStringSet(KEY_AUTH ,cookie).commit()
            }

            fun getAuthCookie(context : Context) : MutableSet<String>? {
                return context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getStringSet(KEY_AUTH , null)
            }
            fun setAuthCookieString(context : Context , cookie : String?) {
                context.getSharedPreferences(TAG , Context.MODE_PRIVATE).edit().putString(KEY_AUTH ,cookie).commit()
            }

            fun getAuthCookieString(context : Context) : String? {
                return context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getString(KEY_AUTH , null)
            }

            fun setMemberVo(context : Context , member : MemberVo) {
                val gsonMember = Gson().toJson(member)
                context.getSharedPreferences(TAG , Context.MODE_PRIVATE).edit().putString(KEY_MEMBER ,gsonMember).commit()
            }

            fun getMemberVo(context : Context) : MemberVo {

                val member =  context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getString(KEY_MEMBER , null)
                return Gson().fromJson(member , MemberVo::class.java)
            }
        }
    }