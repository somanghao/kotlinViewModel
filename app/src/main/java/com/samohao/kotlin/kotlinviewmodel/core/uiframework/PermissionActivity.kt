package com.samohao.kotlin.kotlinviewmodel.core.uiframework

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.samohao.kotlin.kotlinviewmodel.R
import java.security.MessageDigest

open class PermissionActivity : CommonActivity() {
    val reqeustPermission : Int = 15
    val activityResultSetting : Int = 16
    var permissionsItems = arrayOf(
        Manifest.permission.READ_PHONE_STATE
        , Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION
        , Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.CAMERA , Manifest.permission.WRITE_EXTERNAL_STORAGE)

    protected open fun doNextJobWhenGetAllPermission() {

    }

    fun hasAllPermissionWithRequestPermission(requestPermission : Boolean) :Boolean {
        val arrDeniedPermission = getDeniedPermissions()
        if(arrDeniedPermission.isNotEmpty()) {
            if(requestPermission) ActivityCompat.requestPermissions(this@PermissionActivity , arrDeniedPermission , reqeustPermission)
            return false
        }
        return true
    }

    fun getDeniedPermissions() : Array<String> {
        val arrNeedPermissionItem : MutableList<String> = mutableListOf()
        for(permisionItem : String in permissionsItems) {
            if(isPermissionItemDenied(permisionItem)) arrNeedPermissionItem.add(permisionItem)
        }
        return arrNeedPermissionItem.toTypedArray()
    }

    fun isPermissionItemDenied(persmissionItem :String) : Boolean {
        return ActivityCompat.checkSelfPermission(this@PermissionActivity, persmissionItem) != PackageManager.PERMISSION_GRANTED
    }

//    fun isExplanationNeeded(permissionItem : String) : Boolean {
//        return ActivityCompat.shouldShowRequestPermissionRationale(this , permissionItem)
//    }

    fun showPermissionSetDialog() {
        val builder = AlertDialog.Builder(this@PermissionActivity)

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
                else doNextJobWhenGetAllPermission()
            }
        }
    }

    fun getHashKey() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                val info = packageManager.getPackageInfo("com.ylland.gmultishop" , PackageManager.GET_SIGNING_CERTIFICATES)
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
