package com.mrntlu.tokenauthentication.viewmodels

import androidx.lifecycle.MutableLiveData
import com.mrntlu.tokenauthentication.models.UserInfoResponse
import com.mrntlu.tokenauthentication.repository.MainRepository
import com.mrntlu.tokenauthentication.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
): BaseViewModel() {

    private val _userInfoResponse = MutableLiveData<ApiResponse<UserInfoResponse>>()
    val userInfoResponse = _userInfoResponse

    fun getUserInfo(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _userInfoResponse,
        coroutinesErrorHandler,
    ) {
        mainRepository.getUserInfo()
    }
}