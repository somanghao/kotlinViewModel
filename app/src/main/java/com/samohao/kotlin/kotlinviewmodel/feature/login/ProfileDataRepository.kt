package com.samohao.kotlin.kotlinviewmodel.feature.login

import com.kakao.network.ErrorResult
import com.kakao.usermgmt.response.MeV2Response

interface ProfileDataRepository {
    fun onSuccess(result: MeV2Response?) {
    }

    fun onSessionClosed(errorResult: ErrorResult?) {
    }
}