package com.samohao.kotlin.kotlinviewmodel.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samohao.kotlin.kotlinviewmodel.viewModel.DrawerViewModel
import com.samohao.kotlin.kotlinviewmodel.R


class DrawerFragment : Fragment() {

    companion object {
        fun newInstance() = DrawerFragment()
    }

    private lateinit var viewModel: DrawerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drawer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DrawerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
