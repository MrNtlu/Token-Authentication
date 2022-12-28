package com.mrntlu.tokenauthentication.service.main

import com.mrntlu.tokenauthentication.models.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {
    @GET("user/info")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}