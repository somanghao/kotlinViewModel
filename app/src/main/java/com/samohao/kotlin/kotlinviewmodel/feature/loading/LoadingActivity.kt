package com.samohao.kotlin.kotlinviewmodel.feature.loading

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.gson.Gson
import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.feature.login.ResultVo
import com.samohao.kotlin.kotlinviewmodel.core.api.ApiService
import com.samohao.kotlin.kotlinviewmodel.core.api.RestfulApi
import com.samohao.kotlin.kotlinviewmodel.feature.login.UserVo
import com.samohao.kotlin.kotlinviewmodel.core.network.DountLifeRetrofitManager
import com.samohao.kotlin.kotlinviewmodel.core.network.HttpManager
import com.samohao.kotlin.kotlinviewmodel.util.Base64EncodeUtil
import com.samohao.kotlin.kotlinviewmodel.util.PrefererenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.kakao.auth.Session
import com.kakao.auth.ISessionCallback
import com.kakao.util.exception.KakaoException
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.PermissionActivity
import com.samohao.kotlin.kotlinviewmodel.feature.maintab.MainTabActivity

class LoadingActivity : PermissionActivity() {
    private lateinit var sessionCallback : ISessionCallback
    private val disposables = CompositeDisposable()
    private val retrofitManager : RestfulApi by lazy { DountLifeRetrofitManager.getRetrofitService(RestfulApi::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        iv_bg.startAnimation(AnimationUtils.loadAnimation(this@LoadingActivity,
            R.anim.loading_slow
        ))

        if(hasAllPermissionWithRequestPermission(true)) {
            Toast.makeText(this , "all permission ok" , Toast.LENGTH_LONG).show()
        }
        else Toast.makeText(this , "not all permission" , Toast.LENGTH_LONG).show()

        Session.getCurrentSession().addCallback(object  : ISessionCallback {
            override fun onSessionOpenFailed(exception: KakaoException?) {
                Toast.makeText(this@LoadingActivity , "로그인 실패 " , Toast.LENGTH_SHORT).show()
            }

            override fun onSessionOpened() {
                startActivity(Intent(this@LoadingActivity , MainTabActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
            }
        })

        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            activityResultSetting -> {
                if(hasAllPermissionWithRequestPermission(true)) Toast.makeText(this@LoadingActivity , "all permission next activity" , Toast.LENGTH_LONG).show()
                else Toast.makeText(this , "not all permission" , Toast.LENGTH_LONG).show()
            }
            else -> {
                if(Session.getCurrentSession().handleActivityResult(requestCode , resultCode , data)) {
                    return
                }
            }
        }
    }

    fun clickButton(view : View) {
//        startActivity(Intent(this , TestActivity::class.java))
        startActivity(Intent(this@LoadingActivity , MainTabActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    private fun login() {
        val restClient : ApiService = HttpManager.getRetrofitService(ApiService::class.java)
        requestLogin(restClient)

    }

    //기본 restful api 호출
    private fun loginObservable() {

        Toast.makeText(this@LoadingActivity , "로그인 시도 합니다." , Toast.LENGTH_SHORT).show()
        commonDialog.show()
        Handler().postDelayed({
            disposables.add(retrofitManager.requestObservableLogin(Base64EncodeUtil.encoder("samohae"),Base64EncodeUtil.encoder("1234"))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { commonDialog.show() }
                .doOnError { commonDialog.dismiss() }
                .doOnComplete { commonDialog.dismiss() }
                .subscribe {resultVo ->
                    Log.e("samohao" , resultVo.toString())
                    getMember(resultVo)
                })
        } , 3000)
    }

    private fun getMember( data : ResultVo?) {
        try {
            if(data != null) {
                val gson = Gson()
                val userVo : UserVo = gson.fromJson(data.json , UserVo::class.java)
                val memberVo = userVo.memberVo

                PrefererenceHelper.setMemberVo(this@LoadingActivity , memberVo)
                Toast.makeText(this@LoadingActivity , "${memberVo.u_nickname}님 환영합니다." , Toast.LENGTH_SHORT).show()

                startActivity(Intent(this@LoadingActivity , MainTabActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
            }
        } catch (e: Exception) {
        }
    }

    private fun requestLogin(restClient : ApiService) {
        val login = restClient.requestLogin(Base64EncodeUtil.encoder("samohae"),Base64EncodeUtil.encoder("1234"))

        login.enqueue(object : Callback<ResultVo> {
            override fun onFailure(call: Call<ResultVo>, t: Throwable) {
                Toast.makeText(this@LoadingActivity , t.toString() , Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResultVo>, response: Response<ResultVo>) {
                if(response !=null && response.isSuccessful)
                    getMember(response.body())

                Handler().postDelayed({
                    startActivity(Intent(this@LoadingActivity , MainTabActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                } , 2000)
            }
        })
    }

    private fun requestObservableLogin(restClient : ApiService) {
        val login = restClient.requestLogin(Base64EncodeUtil.encoder("samohae"),Base64EncodeUtil.encoder("1234"))

        login.enqueue(object : Callback<ResultVo> {
            override fun onFailure(call: Call<ResultVo>, t: Throwable) {
                Toast.makeText(this@LoadingActivity , t.toString() , Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResultVo>, response: Response<ResultVo>) {
                if(response !=null && response.isSuccessful)
                    getMember(response.body())

                Handler().postDelayed({
                    startActivity(Intent(this@LoadingActivity , MainTabActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                } , 2000)
            }
        })
    }

    override fun doNextJobWhenGetAllPermission() {
        super.doNextJobWhenGetAllPermission()
    }
}
