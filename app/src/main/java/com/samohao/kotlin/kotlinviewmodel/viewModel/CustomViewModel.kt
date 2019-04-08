package com.samohao.kotlin.kotlinviewmodel.viewModel

import androidx.lifecycle.ViewModel;
import com.samohao.kotlin.kotlinviewmodel.api.DonutLifeApi
import com.samohao.kotlin.kotlinviewmodel.network.DountLifeRetrofitManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class CustomViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val compositeDisposable = CompositeDisposable()
    val retrofitManager : DonutLifeApi by lazy { DountLifeRetrofitManager.getRetrofitService(DonutLifeApi::class.java) }

    fun addDisposable(disposable : Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
