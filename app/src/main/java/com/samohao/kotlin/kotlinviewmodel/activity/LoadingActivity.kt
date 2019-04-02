package com.samohao.kotlin.kotlinviewmodel.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.samohao.kotlin.kotlinviewmodel.R
import kotlinx.android.synthetic.main.activity_main.*

class LoadingActivity : AppCompatActivity() ,ActivityCompat.OnRequestPermissionsResultCallback{
    private val reqeustPermission : Int = 15
    private val activityResultSetting : Int = 16
    private var permissionsItems = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION)
                                    else arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_bg.startAnimation(AnimationUtils.loadAnimation(this@LoadingActivity,
            R.anim.loading_slow
        ))

        Handler().postDelayed({} , 2000)
        if(hasAllPermissionWithRequestPermission(true)) moveToNextActivity()
        else Toast.makeText(this , "not all permission" , Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            activityResultSetting -> {
                if(hasAllPermissionWithRequestPermission(true)) Toast.makeText(this@LoadingActivity , "all permission next activity" , Toast.LENGTH_LONG)
                else Toast.makeText(this , "not all permission" , Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun moveToNextActivity() {
        startActivity(Intent(this@LoadingActivity , HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
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
                else moveToNextActivity()
            }
        }
    }
}
