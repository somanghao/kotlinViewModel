package com.samohao.kotlin.kotlinviewmodel.feature.voice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VoiceDataViewModelFactory(private val repository: VoiceDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VoiceDataViewModel(repository) as T
    }
}