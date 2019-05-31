package com.samohao.kotlin.kotlinviewmodel.core.uiframework.customview

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import com.samohao.kotlin.kotlinviewmodel.R

class CustomDialog( context : Context ,private val r_id : Int,private val type : Int) : Dialog(context , android.R.style.Theme_Translucent_NoTitleBar) {

    companion object {
        const val TYPE_PROGRESS_LOADING = 0
    }

    private lateinit var frameAnimation : AnimationDrawable
    init {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowParam : WindowManager.LayoutParams = WindowManager.LayoutParams()
        windowParam.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        windowParam.dimAmount = 0.8f
        window?.attributes = windowParam

        setContentView(r_id)

        if(type == TYPE_PROGRESS_LOADING) {
            val iv_frame = findViewById<ImageView>(R.id.iv_frame)
            frameAnimation = iv_frame.background as AnimationDrawable
            frameAnimation.start()
//            window.attributes
        }
    }

    override fun show() {
        if(isShowing) return
        super.show()
    }

    override fun dismiss() {
        frameAnimation.stop()
        super.dismiss()
    }
}