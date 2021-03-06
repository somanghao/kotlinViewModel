package com.samohao.kotlin.kotlinviewmodel.feature.voice

import com.samohao.kotlin.kotlinviewmodel.feature.login.ResultVo
import io.reactivex.Single

interface VoiceDataRepository {
    fun getVoiceUrl(url: String) : Single<ResultVo>
}