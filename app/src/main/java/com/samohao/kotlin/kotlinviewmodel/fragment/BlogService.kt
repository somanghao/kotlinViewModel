package com.samohao.kotlin.kotlinviewmodel.fragment

import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import io.reactivex.Single
import retrofit2.http.*


interface BlogService {

    @FormUrlEncoded
    @POST("1/functions/createPost")
    fun  postBlogA(
        @Field("body")     body:String
    ) : Single<Result<ResultVo>>

    @FormUrlEncoded
    @POST("post")
    fun  postBlogB(
        @Field("content")     content:String
    ) : Single<Result<ResultVo>>
}