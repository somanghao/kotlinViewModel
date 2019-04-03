package com.samohao.kotlin.kotlinviewmodel.data

data class ResultVo(val code : String?) {
    val message : String? = null
    val json : String? = null
    val totalCount : Int = 0
    val serverDateTime : Long = 0
    val json_sub : String? = null
}

data class  MemberVo(val u_id : String?) {

    val u_login_auth_key : String? = null
    val u_name : String? = null
    val u_nickname : String? = null
    val profile_filename : String? = null
}