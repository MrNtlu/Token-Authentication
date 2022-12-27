package com.mrntlu.tokenauthentication.service.auth

import com.mrntlu.tokenauthentication.models.Auth
import com.mrntlu.tokenauthentication.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/register")
    suspend fun register(
        @Body auth: Auth,
    )

    @POST("auth/login")
    suspend fun login(
        @Body auth: Auth,
    ): Response<LoginResponse>

    @GET("auth/refresh")
    suspend fun refreshToken(): Response<LoginResponse>
}