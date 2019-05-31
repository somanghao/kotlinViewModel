package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.samohao.kotlin.kotlinviewmodel.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.room.Room
import com.samohao.kotlin.kotlinviewmodel.databinding.ActivityWeatherBinding
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var activityMainBinding : ActivityWeatherBinding
    private var enableRefresh = true
    private val mapCities = mapOf(R.id.rbLondon to 1835848,R.id.rbParis to 6455259,R.id.rbArizona to 5551665)
    private var statusStr = "서울의 날씨는"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        activityMainBinding.weatherViewModel = weatherViewModel

    }

    override fun onResume() {
        super.onResume()
        initObserver()
    }

    private fun initObserver() {
        weatherViewModel.responseWeather.observe( this, Observer {
            activityMainBinding.tvWeatherStatus.append("\n")
            val cityId = it
            if(cityId == -1) {
                activityMainBinding.tvWeatherStatus.append("fail")
            } else {
                val db = Room.databaseBuilder(this , WeatherDatabase::class.java , "weathermodel").build()
                Observable.just(db)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        val weatherModel = db.weatherDao().loadWeatherByIds(cityId)
                        activityMainBinding.tvWeatherStatus.append(weatherModel.toString())
                    }
            }
        })

        btnRefresh.setOnClickListener {
            if(!enableRefresh) {
                Toast.makeText(this , "1분후 조회 가능합니다." , Toast.LENGTH_SHORT).show()
            } else {
                updateRefresh(false)
                weatherViewModel.fetchWeather(this@WeatherActivity ,
                    mapCities.getOrElse(rgCity.checkedRadioButtonId ,defaultValue = {0}))
            }
        }

        swipe.setOnRefreshListener {
            if(!enableRefresh) {
                Toast.makeText(this , "1분후 조회 가능합니다." , Toast.LENGTH_SHORT).show()
            } else {
                updateRefresh(false)
                weatherViewModel.fetchWeather(this@WeatherActivity,
                    mapCities.getOrElse(rgCity.checkedRadioButtonId ,defaultValue = {0}))
            }
            swipe.isRefreshing = false
        }

        rgCity.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, i ->
            when(i) {
                R.id.rbLondon -> statusStr = "서울의 날시는"
                R.id.rbParis -> statusStr = "파리의 날시는"
                R.id.rbArizona -> statusStr = "아리조나의 날시는"
            }
        })
    }

    private fun updateRefresh(isShow : Boolean) {
        enableRefresh = isShow
        if(isShow) {
            tvWeatherStatus.text = statusStr
            btnRefresh.text = "refresh"
        }
        else {
            Handler().postDelayed({updateRefresh(true)}, 1000 * 10)
            btnRefresh.text = "wait"
        }
    }
}
