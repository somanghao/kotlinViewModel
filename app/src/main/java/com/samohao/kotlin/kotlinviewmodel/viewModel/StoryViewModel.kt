package com.samohao.kotlin.kotlinviewmodel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.samohao.kotlin.kotlinviewmodel.fragment.BlogService
import com.samohao.kotlin.kotlinviewmodel.network.MutableBaseRetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StoryViewModel : CustomViewModel() {
    private val _responseA = MutableLiveData<String>()
    val responseA : LiveData<String> get() = _responseA
    private val _responseB = MutableLiveData<String>()
    val responseB : LiveData<String> get() = _responseB

    fun sendPost(input : String) {
        if(!input.isNullOrEmpty()) return

        addDisposable(MutableBaseRetrofitManager.getRetrofitService(BlogService::class.java , "http://www.a.com/").postBlogA(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _responseA.postValue("success")
            }
            .doOnError{
                _responseA.postValue("fail")
            }
            .subscribe())

        addDisposable(MutableBaseRetrofitManager.getRetrofitService(BlogService::class.java , "http://www.b.com/").postBlogB(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _responseB.postValue("success")
            }
            .doOnError{
                _responseA.postValue("fail")
            }
            .subscribe())
    }
}
