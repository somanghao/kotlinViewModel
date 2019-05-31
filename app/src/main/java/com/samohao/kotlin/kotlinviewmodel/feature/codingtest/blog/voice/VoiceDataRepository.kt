package com.samohao.kotlin.kotlinviewmodel.model

import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import io.reactivex.Single

interface VoiceDataRepository {
    fun getVoiceUrl(url: String) : Single<ResultVo>
}