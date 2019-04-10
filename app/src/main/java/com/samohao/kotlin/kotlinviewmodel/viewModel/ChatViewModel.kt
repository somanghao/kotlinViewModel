package com.samohao.kotlin.kotlinviewmodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.samohao.kotlin.kotlinviewmodel.data.MemberRoomVo
import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import com.samohao.kotlin.kotlinviewmodel.model.ChatRoomData
import com.samohao.kotlin.kotlinviewmodel.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers

class ChatViewModel : CustomViewModel() {
    private val _chatRoomList = SingleLiveEvent<ChatRoomData>()
    val chatRoomList : LiveData<ChatRoomData> get() = _chatRoomList

    fun getMyChatRoomInfo(u_id : String , room_id : Long) {
        addDisposable(retrofitManager.requestObservableRoomInfo(u_id , room_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getMember(it)
            })
    }

    private fun getMember( data : ResultVo?) {
        if(data != null) {
            val gson = Gson()
            _chatRoomList.postValue(gson.fromJson(data.json , ChatRoomData::class.java))
        }
    }

    fun clickButton() {
        val aa = ""
    }
}
