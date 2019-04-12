package com.samohao.kotlin.kotlinviewmodel.model

import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response

class ProfileKakaoRepository() :ProfileDataRepository {

    init {
        val keys = arrayListOf<String>()
        keys.add("properties.nickname")
        keys.add("properties.profile_image")

        UserManagement.getInstance().me(keys , object : MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response?) {
                this@ProfileKakaoRepository.onSuccess(result)
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                this@ProfileKakaoRepository.onSessionClosed(errorResult)
            }
        })
    }
}