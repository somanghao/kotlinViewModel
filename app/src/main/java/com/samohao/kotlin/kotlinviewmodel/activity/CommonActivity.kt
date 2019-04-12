package com.samohao.kotlin.kotlinviewmodel.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.samohao.kotlin.kotlinviewmodel.CustomDialog
import com.samohao.kotlin.kotlinviewmodel.R

open class CommonActivity : AppCompatActivity() {

    protected val commonDialog :CustomDialog by lazy { CustomDialog(this@CommonActivity , R.layout.custom_loading_ani , CustomDialog.TYPE_PROGRESS_LOADING) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
