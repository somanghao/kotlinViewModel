package com.samohao.kotlin.kotlinviewmodel.feature.maintab.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.feature.aac.WorkManagerActivity
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {
//    lateinit var fragmentInterfce : MainFragment.MainFragmentInterface
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel

        val context : Context = context!!
//        fragmentInterfce?.createdFragment()

        btn_workmanager.setOnClickListener{context.startActivity(Intent(activity , WorkManagerActivity::class.java))}
    }

}
