package com.samohao.kotlin.kotlinviewmodel.feature.blog.weather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.CustomViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherViewModel : CustomViewModel() {
    private lateinit var mConText : Context
    private lateinit var weatherActivity: WeatherActivity
    private val _responseWeather = MutableLiveData<Int>()
    val responseWeather : LiveData<Int> get() = _responseWeather

    fun fetchWeather(weatherActivity: WeatherActivity, id : Int) {
        if(id == 0) return
        this.weatherActivity = weatherActivity
        this.mConText = weatherActivity
        val appId = mConText.getString(R.string.weather_api_key)
        addDisposable(
            WeatherRetrofitManager.getRetrofitService(WeatherService::class.java).getWeather(id,appId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                getWeather(it)

            }
            .doOnError{
                _responseWeather.postValue(-1)
            }
            .subscribe({
                    t1: WeatherModel? -> //success
            },
                {
                        t2: Throwable? -> _responseWeather.postValue(-1)
                }
            ))
    }

    fun getWeather(weatherModel: WeatherModel) {


        val db = Room.databaseBuilder(mConText , WeatherDatabase::class.java , "weathermodel").build()
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.weatherDao().insertAll(weatherModel)
                _responseWeather.postValue(weatherModel.id)
            }
//        db.weatherDao().insertAll(weatherModel)
    }
}
