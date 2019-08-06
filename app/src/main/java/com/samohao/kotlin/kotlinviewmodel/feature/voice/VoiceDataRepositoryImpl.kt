package com.samohao.kotlin.kotlinviewmodel.feature.voice

import com.samohao.kotlin.kotlinviewmodel.core.api.RestfulApi
import com.samohao.kotlin.kotlinviewmodel.feature.login.ResultVo
import com.samohao.kotlin.kotlinviewmodel.core.network.DountLifeRetrofitManager
import io.reactivex.Single

class VoiceDataRepositoryImpl : VoiceDataRepository {
    override fun getVoiceUrl(url: String): Single<ResultVo> {

        val voiceDataApi = DountLifeRetrofitManager.getRetrofitService(RestfulApi::class.java)
        return voiceDataApi.requestObservableGetVoiceData(url)
    }
}