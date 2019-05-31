package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.voice

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.SingleLiveEvent

class VoiceDataViewModel(private val voiceDataRepository : VoiceDataRepository) : ViewModel() , View.OnClickListener{

    private val _result = SingleLiveEvent<String>()
    val result : LiveData<String> get() = _result

    private val _clickSpeech = SingleLiveEvent<Any>()
    val clickSpeech : LiveData<Any> get() = _clickSpeech

    fun clickSpeechButton() {
        _clickSpeech.call()
    }

    fun setResult(result : String) {
        _result.postValue(result)
    }

    override fun onClick(p0: View?) {
        _clickSpeech.call()
    }
}
