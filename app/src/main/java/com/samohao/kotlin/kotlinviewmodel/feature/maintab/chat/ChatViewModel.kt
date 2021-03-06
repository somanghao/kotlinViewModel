package com.samohao.kotlin.kotlinviewmodel.feature.maintab.chat

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.samohao.kotlin.kotlinviewmodel.feature.login.ResultVo
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.SingleLiveEvent
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.CustomViewModel
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
}
