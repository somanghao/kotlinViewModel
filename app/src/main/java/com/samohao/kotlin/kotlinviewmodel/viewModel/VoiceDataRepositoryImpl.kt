package com.samohao.kotlin.kotlinviewmodel.viewModel

import com.samohao.kotlin.kotlinviewmodel.api.DonutLifeApi
import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import com.samohao.kotlin.kotlinviewmodel.model.VoiceDataRepository
import com.samohao.kotlin.kotlinviewmodel.model.VoiceData
import com.samohao.kotlin.kotlinviewmodel.network.DountLifeRetrofitManager
import io.reactivex.Single

class VoiceDataRepositoryImpl : VoiceDataRepository {
    override fun getVoiceUrl(url: String): Single<ResultVo> {

        val voiceDataApi = DountLifeRetrofitManager.getRetrofitService(DonutLifeApi::class.java)
        return voiceDataApi.requestObservableGetVoiceData(url)
    }
}