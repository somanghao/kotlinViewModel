package com.samohao.kotlin.kotlinviewmodel.feature.maintab.mission

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.samohao.kotlin.kotlinviewmodel.R

class MissionFragment : Fragment() {

    companion object {
        fun newInstance() = MissionFragment()
    }

    private lateinit var viewModel: MissionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mission_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MissionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
