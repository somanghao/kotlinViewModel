package com.samohao.kotlin.kotlinviewmodel.api

import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("service/common/setDeviceInfo")
    fun  requestSetDeviceInfo(
        @Field("android_api_level")     android_api_level:String,
        @Field("android_device_id")     android_device_id:String,
        @Field("android_version_name")     android_version_name:String,
        @Field("android_phone_model")     android_phone_model:String,
        @Field("version_name")     version_name:String,
        @Field("version_code")     version_code:String ) : Call<ResultVo>

    @FormUrlEncoded
    @POST("service/common/logout")
    fun  requestLogout(
        @Field("u_id")     u_id:String) : Call<ResultVo>

    @FormUrlEncoded
    @POST("service/common/login")
    fun  requestLogin(
        @Field("user_id")     user_id:String,
        @Field("password")     password:String
    ) : Call<ResultVo>

    @FormUrlEncoded
    @POST("service/common/login")
    fun  requestObservableLogin(
        @Field("user_id")     user_id:String,
        @Field("password")     password:String
    ) : Observable<ResultVo>
}