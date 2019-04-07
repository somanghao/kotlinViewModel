package com.samohao.kotlin.kotlinviewmodel.api

import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*


interface DonutLifeApi {

    @FormUrlEncoded
    @POST("service/common/login")
    fun  requestObservableLogin(
        @Field("user_id")     user_id:String,
        @Field("password")     password:String
    ) : Observable<ResultVo>
}