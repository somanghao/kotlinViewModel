package com.samohao.kotlin.kotlinviewmodel.data

import com.google.gson.annotations.SerializedName

data class ResultVo(val code : String? , val json : String?, val message : String?) {
    val totalCount : Int = 0
    val serverDateTime : Long = 0
    val json_sub : String? = null
}

class  UserVo(val u_id : String, val memberVo: MemberVo)

class  MemberVo(val u_id : String,
    @SerializedName("u_nickname") val u_nickname : String,
    @SerializedName("profile_filename") val profile_filename : String? = null,
    val u_login_auth_key : String? = null,
    val u_name : String? = null
    )