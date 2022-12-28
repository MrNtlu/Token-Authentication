package com.mrntlu.tokenauthentication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrntlu.tokenauthentication.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,
): ViewModel() {

    val token = MutableLiveData<String?>()
    private var mJob: Job? = null

    init {
        mJob = viewModelScope.launch {
            tokenManager.getToken().collect {
                token.value = it
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            tokenManager.saveToken(token)
        }
    }

    fun deleteToken() {
        viewModelScope.launch {
            tokenManager.deleteToken()
        }
    }

    private fun removeObservables() {
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
        mJob = null
    }

    override fun onCleared() {
        super.onCleared()
        removeObservables()
    }
}