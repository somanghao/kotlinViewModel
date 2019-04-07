package com.samohao.kotlin.kotlinviewmodel.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.samohao.kotlin.kotlinviewmodel.CustomDialog
import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.data.MemberVo
import com.samohao.kotlin.kotlinviewmodel.data.ResultVo
import com.samohao.kotlin.kotlinviewmodel.api.ApiService
import com.samohao.kotlin.kotlinviewmodel.api.DonutLifeApi
import com.samohao.kotlin.kotlinviewmodel.data.UserVo
import com.samohao.kotlin.kotlinviewmodel.network.DountLifeRetrofitManager
import com.samohao.kotlin.kotlinviewmodel.network.HttpManager
import com.samohao.kotlin.kotlinviewmodel.util.Base64EncodeUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class LoadingActivity : CommonActivity() ,ActivityCompat.OnRequestPermissionsResultCallback{
    private val reqeustPermission : Int = 15
    private val activityResultSetting : Int = 16
    private var permissionsItems = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION)
                                    else arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION)
    private val disposables = CompositeDisposable()
    private val retrofitManager : DonutLifeApi by lazy { DountLifeRetrofitManager.getRetrofitService(DonutLifeApi::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_bg.startAnimation(AnimationUtils.loadAnimation(this@LoadingActivity,
            R.anim.loading_slow
        ))

        if(hasAllPermissionWithRequestPermission(true)) loginObservable() //login()
        else Toast.makeText(this , "not all permission" , Toast.LENGTH_LONG).show()
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
        }
    }

    private fun login() {
        val restClient : ApiService = HttpManager.getRetrofitService(ApiService::class.java)
        requestLogin(restClient)


    }

    private fun loginObservable() {

        Toast.makeText(this@LoadingActivity , "로그인 시도 합니다." , Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
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
            } , 4000)



        } , 2000)


    }

    private fun getMember( data : ResultVo?) {
        try {
            if(data != null) {
                val gson = Gson()
                val userVo :UserVo = gson.fromJson(data.json , UserVo::class.java)
                val memberVo = userVo.memberVo
                Toast.makeText(this@LoadingActivity , "${memberVo.u_nickname}님 환영합니다." , Toast.LENGTH_SHORT).show()

                Handler().postDelayed({
                    startActivity(Intent(this@LoadingActivity , HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                } , 2000)
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
}
