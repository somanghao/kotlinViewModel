package com.samohao.kotlin.kotlinviewmodel.api

import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface DonutLifeApi {

    @FormUrlEncoded
    @POST("service/common/login")
    fun  requestObservableLogin(
        @Field("user_id")     user_id:String,
        @Field("password")     password:String
    ) : Observable<ResultVo>

    @FormUrlEncoded
    @POST("service/chef/getChefRoomInfo")
    fun  requestObservableRoomInfo(
        @Field("u_id")      u_id:String,
        @Field("room_id")     room_id:Long
    ) : Observable<ResultVo>

    @FormUrlEncoded
    @POST("service/chef/getVoiceData")
    fun  requestObservableGetVoiceData(
        @Field("u_id")      u_id:String
    ) : Single<ResultVo>
}