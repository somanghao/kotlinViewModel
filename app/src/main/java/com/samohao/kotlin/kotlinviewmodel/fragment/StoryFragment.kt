package com.samohao.kotlin.kotlinviewmodel.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.databinding.StoryFragmentBinding
import com.samohao.kotlin.kotlinviewmodel.feature.blog.BlogActivity
import com.samohao.kotlin.kotlinviewmodel.feature.weather.WeatherActivity
import com.samohao.kotlin.kotlinviewmodel.viewModel.MissionViewModel
import com.samohao.kotlin.kotlinviewmodel.viewModel.StoryViewModel
import kotlinx.android.synthetic.main.story_fragment.*

class StoryFragment : Fragment() {

    companion object {
        fun newInstance() = StoryFragment()
    }

    private lateinit var storyViewModel: StoryViewModel
    private lateinit var storyFragmentBinding: StoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.story_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        storyViewModel = ViewModelProviders.of(this).get(StoryViewModel::class.java)
        // TODO: Use the ViewModel

        btnBlog.setOnClickListener{startActivity(Intent(activity , BlogActivity::class.java))}
        btnWeather.setOnClickListener { startActivity(Intent(activity , WeatherActivity::class.java)) }
    }
}
