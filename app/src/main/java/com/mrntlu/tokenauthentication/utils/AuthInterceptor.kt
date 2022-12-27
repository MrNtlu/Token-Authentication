package com.mrntlu.tokenauthentication.utils

import com.mrntlu.tokenauthentication.ui.main.MainFragment
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer ${MainFragment.staticToken}")
        return chain.proceed(request.build())
    }
}