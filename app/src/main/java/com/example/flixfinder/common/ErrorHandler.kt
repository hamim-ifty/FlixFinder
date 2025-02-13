package com.example.flixfinder.common

sealed class ErrorType {
    object Network : ErrorType()
    object ApiError : ErrorType()
    data class Unknown(val message: String) : ErrorType()
}

object ErrorHandler {
    fun handleError(throwable: Throwable): ErrorType {
        return when (throwable) {
            is java.net.UnknownHostException -> ErrorType.Network
            is retrofit2.HttpException -> ErrorType.ApiError
            else -> ErrorType.Unknown(throwable.message ?: "Unknown error occurred")
        }
    }
}