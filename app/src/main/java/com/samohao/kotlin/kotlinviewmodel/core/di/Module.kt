package com.samohao.kotlin.kotlinviewmodel.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

val basicModule : Module = module {

}

val appModule = listOf(
    basicModule,
    voiceDataModule
)