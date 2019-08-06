package com.samohao.kotlin.kotlinviewmodel.feature.drawer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.samohao.kotlin.kotlinviewmodel.feature.login.MemberRoomVo
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.SingleLiveEvent
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.CustomViewModel
import com.samohao.kotlin.kotlinviewmodel.feature.login.MemberVo
import com.samohao.kotlin.kotlinviewmodel.util.PrefererenceHelper

class DrawerViewModel : CustomViewModel() {
    // TODO: Implement the ViewModel

    private val _memberRoomVo = MutableLiveData<MemberRoomVo>()
    val memberRoomVo : LiveData<MemberRoomVo> get() = _memberRoomVo

    private val _clickProfile = SingleLiveEvent<Any>()
    val clickProfile : LiveData<Any> get() = _clickProfile

    fun getMyRoomInfo(context: Context) {

        //통신없이 저장된 데이터로만 한다
        val memberVo = PrefererenceHelper.getMemberVo(context)
        if(memberVo != null) {
            val memberRoomVo = MemberRoomVo()
            memberRoomVo.u_nickname = memberVo.u_nickname
            memberRoomVo.intro_message = ""
            _memberRoomVo.postValue(memberRoomVo)
        } else {
            val memberRoomVo = MemberRoomVo()
            memberRoomVo.u_nickname = "test"
            memberRoomVo.intro_message = "good day to die"
            _memberRoomVo.postValue(memberRoomVo)
        }
        /* 카카오사용
        val keys = arrayListOf<String>()
        keys.add("properties.nickname")
        keys.add("properties.profile_image")

        UserManagement.getInstance().me(keys , object : MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response?) {
                getMember(result)
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                getMember(null)
            }
        })*/

        /* api호출 사용
        //기본 restful api 호출
        addDisposable(retrofitManager.requestObservableRoomInfo(u_id , room_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getMember(it)
            })*/
    }

    private fun getMember( result: MeV2Response?) {
        if(result != null) {
            var memberRoomVo = MemberRoomVo()
            memberRoomVo.intro_message = result.nickname
            memberRoomVo.intro_picture = result.profileImagePath
            _memberRoomVo.postValue(memberRoomVo)
        }
    }

//    private fun getMember( data : ResultVo?) {
//        if(data != null) {
//            val gson = Gson()
//            _memberRoomVo.postValue(gson.fromJson(data.json , MemberRoomVo::class.java))
//        }
//    }

    fun clickProfile() {
        _clickProfile.call()
    }
}
