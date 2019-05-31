package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog

import com.google.gson.annotations.SerializedName

class BlogResponseModel {
    @SerializedName("code")
    val code: Int =  0

    @SerializedName("message")
    val message: String? = null
}