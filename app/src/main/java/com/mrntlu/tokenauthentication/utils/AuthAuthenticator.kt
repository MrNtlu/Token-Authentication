package com.mrntlu.tokenauthentication.utils

import com.mrntlu.tokenauthentication.models.LoginResponse
import com.mrntlu.tokenauthentication.service.auth.AuthApiService
import com.mrntlu.tokenauthentication.ui.main.MainFragment
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val token = MainFragment.staticToken
            val newToken = getNewToken(token)

            //Couldn't refresh the token, so restart the login process
            if (!newToken.isSuccessful || newToken.body() == null) {
                TODO("Restart login process")
            }

            newToken.body()?.let {
                MainFragment.staticToken = it.token
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.token}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jwt-test-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApiService::class.java)
        return service.refreshToken("Bearer $refreshToken")
    }
}