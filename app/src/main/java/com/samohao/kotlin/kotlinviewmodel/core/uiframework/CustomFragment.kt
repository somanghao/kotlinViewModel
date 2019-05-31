package com.samohao.kotlin.kotlinviewmodel.core.uiframework

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open abstract class CustomFragment : Fragment() , FragmentInterface {
    lateinit var mContext : Context
    override val resouceId: Int
        get() = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = context ?: inflater.context
        initViewModel()
        return initDataBind(inflater , container)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observerViewModel()
    }
}
