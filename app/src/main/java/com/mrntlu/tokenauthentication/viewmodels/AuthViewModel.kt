package com.mrntlu.tokenauthentication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrntlu.tokenauthentication.models.Auth
import com.mrntlu.tokenauthentication.models.LoginResponse
import com.mrntlu.tokenauthentication.repository.AuthRepository
import com.mrntlu.tokenauthentication.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel() {

    private val _loginResponse = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResponse = _loginResponse

//    fun login(auth: Auth) {
//        viewModelScope.launch {
//            authRepository.login(auth).collect {
//                _loginResponse.value = it
//            }
//        }
//    }

    fun login(auth: Auth, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ) {
        authRepository.login(auth)
    }
}