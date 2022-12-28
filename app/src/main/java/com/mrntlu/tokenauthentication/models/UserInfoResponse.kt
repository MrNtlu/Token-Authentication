package com.mrntlu.tokenauthentication.models

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("data")
    val userInfo: UserInfo,
    val message: String
)