package com.samohao.kotlin.kotlinviewmodel.feature.login

import com.google.gson.annotations.SerializedName

data class ResultVo(val code : String? , val json : String?, val message : String?) {
    val totalCount : Int = 0
    val serverDateTime : Long = 0
    val json_sub : String? = null
}

class  UserVo(val u_id : String, val memberVo: MemberVo)

open class  MemberVo {
    val u_id : String = ""
    @SerializedName("u_nickname") var u_nickname : String = ""
    @SerializedName("profile_filename") var profile_filename : String = ""
    val u_login_auth_key : String = ""
    var u_name : String = ""
    val member_room_id : Long = -1
}

class MemberRoomVo(
    ) : MemberVo() {
    @SerializedName("intro_message") var intro_message : String? = "상태 메세지"
    @SerializedName("intro_picture") var intro_picture : String? = null
}