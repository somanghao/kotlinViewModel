package com.samohao.kotlin.kotlinviewmodel.di

import com.samohao.kotlin.kotlinviewmodel.model.VoiceDataRepository
import com.samohao.kotlin.kotlinviewmodel.viewModel.VoiceDataRepositoryImpl
import com.samohao.kotlin.kotlinviewmodel.viewModel.VoiceDataViewModelFactory
import org.koin.core.module.Module
import org.koin.dsl.module

val voiceDataModule : Module = module {
    single<VoiceDataRepository> {
        VoiceDataRepositoryImpl()
    }

    factory {
        VoiceDataViewModelFactory(get())
    }
}