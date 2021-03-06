package com.samohao.kotlin.kotlinviewmodel.core.uiframework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface FragmentInterface {
    val resouceId : Int
    fun initViewModel()
    fun initDataBind(inflater: LayoutInflater, container: ViewGroup?) :View?
    fun observerViewModel()
}