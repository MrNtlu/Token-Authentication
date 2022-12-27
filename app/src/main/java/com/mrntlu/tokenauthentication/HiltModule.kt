package com.mrntlu.tokenauthentication

import com.mrntlu.tokenauthentication.repository.AuthRepository
import com.mrntlu.tokenauthentication.repository.MainRepository
import com.mrntlu.tokenauthentication.service.auth.AuthApiService
import com.mrntlu.tokenauthentication.service.main.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideAuthRepository(authApiService: AuthApiService) = AuthRepository(authApiService)

    @Provides
    fun provideMainRepository(mainApiService: MainApiService) = MainRepository(mainApiService)
}