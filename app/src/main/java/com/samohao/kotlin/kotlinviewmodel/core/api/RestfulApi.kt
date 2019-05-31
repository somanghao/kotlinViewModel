package com.samohao.kotlin.kotlinviewmodel.core.api

import com.samohao.kotlin.kotlinviewmodel.feature.login.ResultVo
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*


interface RestfulApi {

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