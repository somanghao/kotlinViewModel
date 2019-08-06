package com.samohao.kotlin.kotlinviewmodel.feature.blog

import io.reactivex.Single
import retrofit2.http.*


interface BlogService {

    @FormUrlEncoded
    @POST("1/functions/createPost")
    fun  postBlogA(
        @Field("body")     body:String
    ) : Single<BlogResponseModel>

    @FormUrlEncoded
    @POST("post")
    fun  postBlogB(
        @Field("content")     content:String
    ) : Single<BlogResponseModel>
}