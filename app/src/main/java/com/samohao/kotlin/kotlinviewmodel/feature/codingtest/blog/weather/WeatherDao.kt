package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.weather

import androidx.room.*

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weathermodel")
    fun getAll(): List<WeatherModel>

    @Query("SELECT * FROM weathermodel WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<WeatherModel>

    @Query("SELECT * FROM weathermodel WHERE id LIKE :id")
    fun loadWeatherByIds(id: Int): WeatherModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg ids: WeatherModel)

    @Delete
    fun delete(city: WeatherModel)
}