package com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.voice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VoiceDataViewModelFactory(private val repository: VoiceDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VoiceDataViewModel(repository) as T
    }
}