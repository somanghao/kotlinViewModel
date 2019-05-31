package com.samohao.kotlin.kotlinviewmodel.feature.drawer

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.feature.login.MemberRoomVo
import com.samohao.kotlin.kotlinviewmodel.databinding.DrawerFragmentBinding
import com.samohao.kotlin.kotlinviewmodel.util.PrefererenceHelper


class DrawerFragment : Fragment() {


    companion object {
        fun newInstance() = DrawerFragment()
    }

    private lateinit var viewModel: DrawerViewModel
    private lateinit var drawerFragmentBinding: DrawerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerFragmentBinding = DataBindingUtil.inflate(inflater ,R.layout.drawer_fragment, container, false)
        drawerFragmentBinding.lifecycleOwner = this
        return drawerFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DrawerViewModel::class.java)

        val context : Context = context!!
        val memberVo = PrefererenceHelper.getMemberVo(context)

        viewModel.getMyRoomInfo(memberVo.u_id , memberVo.member_room_id)

        viewModel.memberRoomVo.observe(this , Observer<MemberRoomVo> {
            drawerFragmentBinding.memberRoom = it
        })

        viewModel.clickProfile.observe(this , Observer<Any> {
            Toast.makeText(context , "아파" , Toast.LENGTH_SHORT).show()
        })
    }

}
