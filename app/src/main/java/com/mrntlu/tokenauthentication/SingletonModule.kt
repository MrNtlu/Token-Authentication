package com.mrntlu.tokenauthentication

import com.mrntlu.tokenauthentication.service.auth.AuthApiService
import com.mrntlu.tokenauthentication.service.auth.AuthInterceptor
import com.mrntlu.tokenauthentication.service.main.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(
                "Bearer",
                ""
            ))
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jwt-test-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideMainAPIService(retrofit: Retrofit): MainApiService =
        retrofit.create(MainApiService::class.java)
}