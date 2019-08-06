package com.samohao.kotlin.kotlinviewmodel.feature.blog.weather

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherModel(
    @PrimaryKey val id: Int,
    @Embedded val main: WeatherMainModel?,
    @Embedded val wind: WeatherWindModel?,
    @ColumnInfo(name = "cod") val cod: Int?,
    @ColumnInfo(name = "name") val name: String?
)

//data class WeatherModel(
//    val id: Int,
//    val main: WeatherMainModel,
//    val wind: WeatherWindModel,
//    val code: Int,
//    val name: String?
//) {
//}
@Entity
data class WeatherMainModel(
    @ColumnInfo(name = "temp") val temp: Float,
    @ColumnInfo(name = "temp_min") val temp_min: Float,
    @ColumnInfo(name = "temp_max") val temp_max: Float,
    @ColumnInfo(name = "humidity") val humidity: Int?
)

@Entity
data class WeatherWindModel(
    @ColumnInfo(name = "speed")val speed: Float,
    @ColumnInfo(name = "deg")val deg: Float?
)