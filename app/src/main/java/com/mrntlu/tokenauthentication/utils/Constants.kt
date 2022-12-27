package com.mrntlu.tokenauthentication.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mrntlu.tokenauthentication.models.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun<T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(7500L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    val parsedError: ErrorResponse = Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                    emit(ApiResponse.Failure("Code: ${parsedError.code}, ${parsedError.message}"))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: e.toString()))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again."))
}.flowOn(Dispatchers.IO)