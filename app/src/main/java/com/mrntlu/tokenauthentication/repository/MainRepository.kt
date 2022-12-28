package com.mrntlu.tokenauthentication.repository

import com.mrntlu.tokenauthentication.service.main.MainApiService
import com.mrntlu.tokenauthentication.utils.apiRequestFlow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService,
) {
    fun getUserInfo() = apiRequestFlow {
        mainApiService.getUserInfo()
    }
}