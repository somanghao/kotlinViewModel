package com.samohao.kotlin.kotlinviewmodel.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.databinding.StoryFragmentBinding
import com.samohao.kotlin.kotlinviewmodel.viewModel.StoryViewModel

class StoryFragment : CustomFragment() {

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
    }

    override fun initViewModel() {
        storyViewModel = ViewModelProviders.of(this).get(StoryViewModel::class.java)
    }

    override fun initDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        storyFragmentBinding = DataBindingUtil.inflate(inflater ,R.layout.drawer_fragment, container, false)
        storyFragmentBinding.storyViewModel = storyViewModel
        storyFragmentBinding.lifecycleOwner = this
        return storyFragmentBinding.root
    }

    override fun observerViewModel() {
        storyViewModel.responseA.observe( this, Observer {
            storyFragmentBinding.tvResponseA.append(it)
        })
        storyViewModel.responseB.observe( this, Observer {
            storyFragmentBinding.tvResponseB.append(it)
        })
    }
}
