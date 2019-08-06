package com.samohao.kotlin.kotlinviewmodel.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.feature.blog.weather.WeatherActivity

class TestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

    }

    override fun onResume() {
        super.onResume()
    }

    fun clickButton(view : View) {

        when(view.id) {
//            R.id.btnBlog -> startActivity(Intent(this , BlogActivity::class.java))
            R.id.btnWeather -> startActivity(Intent(this , WeatherActivity::class.java))
        }
    }
}
