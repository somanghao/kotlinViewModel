package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.samohao.kotlin.kotlinviewmodel.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.samohao.kotlin.kotlinviewmodel.databinding.ActivityBlogBinding

class BlogActivity : AppCompatActivity() {

    private lateinit var viewModel: BlogViewModel
    private lateinit var activityMainBinding : ActivityBlogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_blog)
        viewModel = ViewModelProviders.of(this).get(BlogViewModel::class.java)
        activityMainBinding.mainViewModel = viewModel

        viewModel.responseA.observe( this, Observer {
            activityMainBinding.tvResponseA.append(it)
        })
        viewModel.responseB.observe( this, Observer {
            activityMainBinding.tvResponseB.append(it)
        })
    }

    override fun onResume() {
        super.onResume()
    }
}
