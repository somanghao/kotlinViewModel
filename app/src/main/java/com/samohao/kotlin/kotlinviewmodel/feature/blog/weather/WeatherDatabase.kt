package com.samohao.kotlin.kotlinviewmodel.feature.blog.weather

import androidx.room.*

@Database(entities = arrayOf(WeatherModel::class), version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}