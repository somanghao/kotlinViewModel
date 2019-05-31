package com.samohao.kotlin.kotlinviewmodel.feature.loading

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
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
import java.security.MessageDigest
import com.kakao.auth.Session
import com.kakao.auth.ISessionCallback
import com.kakao.util.exception.KakaoException
import com.samohao.kotlin.kotlinviewmodel.core.uiframework.CommonActivity
import com.samohao.kotlin.kotlinviewmodel.feature.TestActivity
import com.samohao.kotlin.kotlinviewmodel.feature.maintab.HomeActivity

class LoadingActivity : CommonActivity() ,ActivityCompat.OnRequestPermissionsResultCallback{
    private val reqeustPermission : Int = 15
    private val activityResultSetting : Int = 16
    private lateinit var sessionCallback : ISessionCallback
    private var permissionsItems = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) arrayOf(Manifest.permission.READ_PHONE_STATE
        , Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.ACCESS_FINE_LOCATION
        , Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.RECORD_AUDIO)
                                    else arrayOf(Manifest.permission.READ_PHONE_STATE
        , Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.RECORD_AUDIO)
    private val disposables = CompositeDisposable()
    private val retrofitManager : RestfulApi by lazy { DountLifeRetrofitManager.getRetrofitService(RestfulApi::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        iv_bg.startAnimation(AnimationUtils.loadAnimation(this@LoadingActivity,
            R.anim.loading_slow
        ))
//        getHashKey()
        if(hasAllPermissionWithRequestPermission(true)) {
            Toast.makeText(this , "all permission ok" , Toast.LENGTH_LONG).show()
//            loginObservable()
        }
        else Toast.makeText(this , "not all permission" , Toast.LENGTH_LONG).show()

        Session.getCurrentSession().addCallback(object  : ISessionCallback {
            override fun onSessionOpenFailed(exception: KakaoException?) {
                Toast.makeText(this@LoadingActivity , "로그인 실패 " , Toast.LENGTH_SHORT)
            }

            override fun onSessionOpened() {
                startActivity(Intent(this@LoadingActivity , HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
            }
        })

        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onResume() {
        super.onResume()

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
        startActivity(Intent(this , TestActivity::class.java))
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

                startActivity(Intent(this@LoadingActivity , HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
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
                    startActivity(Intent(this@LoadingActivity , HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
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
                    startActivity(Intent(this@LoadingActivity , HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                } , 2000)
            }
        })
    }

    private fun hasAllPermissionWithRequestPermission(requestPermission : Boolean) :Boolean {
        val arrDeniedPermission = getDeniedPermissions()
        if(arrDeniedPermission.isNotEmpty()) {
            if(requestPermission) ActivityCompat.requestPermissions(this@LoadingActivity , arrDeniedPermission , reqeustPermission)
            return false
        }
        return true
    }

    private fun getDeniedPermissions() : Array<String> {
        val arrNeedPermissionItem : MutableList<String> = mutableListOf()
        for(permisionItem : String in permissionsItems) {
            if(isPermissionItemDenied(permisionItem)) arrNeedPermissionItem.add(permisionItem)
        }
        return arrNeedPermissionItem.toTypedArray()
    }

    private fun isPermissionItemDenied(persmissionItem :String) : Boolean {
        return ActivityCompat.checkSelfPermission(this@LoadingActivity, persmissionItem) != PackageManager.PERMISSION_GRANTED
    }

//    fun isExplanationNeeded(permissionItem : String) : Boolean {
//        return ActivityCompat.shouldShowRequestPermissionRationale(this , permissionItem)
//    }

    private fun showPermissionSetDialog() {
        val builder = AlertDialog.Builder(this@LoadingActivity)

        builder.setMessage(String.format(getString(R.string.alert_msg_required_permission),getString(
            R.string.app_name
        )))
        builder.setPositiveButton("설정") {_, _ ->
            startActivityForResult(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:$packageName")) , activityResultSetting)
        }
        builder.setNegativeButton("닫기") {_,_ -> finish() }
        builder.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            reqeustPermission -> {
                //한개라도 거절하면 팝업
                if(grantResults.any { it != PackageManager.PERMISSION_GRANTED }) showPermissionSetDialog()
                else login()
            }
        }
    }



    private fun getHashKey() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                val info = packageManager.getPackageInfo("com.samohao.kotlin.kotlinviewmodel" , PackageManager.GET_SIGNING_CERTIFICATES)
                for(signature : Signature in info.signingInfo.apkContentsSigners) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val str = Base64.encodeToString(md.digest() , Base64.DEFAULT)
                    Log.e("samohao" , "key = $str")
                }
            } catch (exception : java.lang.Exception) {

            }
        }

    }
}
