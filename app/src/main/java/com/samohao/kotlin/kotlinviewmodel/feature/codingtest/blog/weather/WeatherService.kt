package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.weather

import io.reactivex.Single
import retrofit2.http.*


interface WeatherService {

    @GET("data/2.5/weather")
    fun  getWeather(
        @Query("id")     id:Int,
        @Query("appid")  appid:String
    ) : Single<WeatherModel>
}