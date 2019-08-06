package com.samohao.kotlin.kotlinviewmodel.feature.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.CustomViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BlogViewModel : CustomViewModel() {
    private val _responseA = MutableLiveData<String>()
    val responseA : LiveData<String> get() = _responseA
    private val _responseB = MutableLiveData<String>()
    val responseB : LiveData<String> get() = _responseB

    fun sendPost(input : String) {
        if(input.isNullOrEmpty()) return

        addDisposable(
            BlogRetrofitManager.getRetrofitService(
                BlogService::class.java,
                "http://www.a.com/"
            ).postBlogA(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _responseA.postValue("success")
            }
            .doOnError{
                _responseA.postValue("fail")
            }
            .subscribe(
                {
                        t1: BlogResponseModel? -> //success
                },
                {
                        t2: Throwable? -> _responseA.postValue("fail")
                }
            ))

        addDisposable(
            BlogRetrofitManager.getRetrofitService(
                BlogService::class.java,
                "http://www.b.com/"
            ).postBlogB(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _responseB.postValue("success")
            }
            .doOnError{
                _responseB.postValue("fail")
            }
            .subscribe(
                {
                        t1: BlogResponseModel? -> //success
                },
                {
                        t2: Throwable? -> _responseB.postValue("fail")
                }
            ))
    }
}
