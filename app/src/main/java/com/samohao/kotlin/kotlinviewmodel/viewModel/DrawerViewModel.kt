package com.samohao.kotlin.kotlinviewmodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.samohao.kotlin.kotlinviewmodel.data.MemberRoomVo
import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import io.reactivex.android.schedulers.AndroidSchedulers

class DrawerViewModel : CustomViewModel() {
    // TODO: Implement the ViewModel

    private val _memberRoomVo = MutableLiveData<MemberRoomVo>()

    val memberRoomVo : LiveData<MemberRoomVo> get() = _memberRoomVo

    fun getMyRoomInfo(u_id : String , room_id : Long) {
        addDisposable(retrofitManager.requestObservableRoomInfo(u_id , room_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getMember(it)
            })
    }

    private fun getMember( data : ResultVo?) {
        if(data != null) {
            val gson = Gson()
            _memberRoomVo.postValue(gson.fromJson(data.json , MemberRoomVo::class.java))
        }
    }


}
