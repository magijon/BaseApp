package com.mgijon.baseapp.example.model

sealed class ErrorException(val isError: Boolean = false, val message: String? = null) {
    object BaseErrorException : ErrorException()
    class BaseErrorExceptionMessage(private val messageString: String) : ErrorException(isError = true, messageString)
}