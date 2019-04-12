package com.samohao.kotlin.kotlinviewmodel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samohao.kotlin.kotlinviewmodel.model.VoiceDataRepository

class VoiceDataViewModelFactory(private val repository: VoiceDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VoiceDataViewModel(repository) as T
    }
}