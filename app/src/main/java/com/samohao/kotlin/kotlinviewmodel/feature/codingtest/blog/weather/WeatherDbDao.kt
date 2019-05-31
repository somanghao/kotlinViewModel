package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.weather

import androidx.room.*

@Dao
interface WeatherDbDao {
    @Query("SELECT * FROM weatherdbmodel")
    fun getAll(): List<WeatherDBModel>

    @Query("SELECT * FROM weatherdbmodel WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<WeatherDBModel>

    @Insert
    fun insertAll(vararg citys: WeatherDBModel)

    @Delete
    fun delete(city: WeatherDBModel)
}