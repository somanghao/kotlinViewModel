package com.samohao.kotlin.kotlinviewmodel.core.di

import com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.voice.VoiceDataRepository
import com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.voice.VoiceDataRepositoryImpl
import com.samohao.kotlin.kotlinviewmodel.feature.codingtest.blog.voice.VoiceDataViewModelFactory
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