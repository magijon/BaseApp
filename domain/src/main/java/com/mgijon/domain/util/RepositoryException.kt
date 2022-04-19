package com.mgijon.domain.util

sealed class RepositoryException(message: String? = null){
    class UnauthorizedException : RepositoryException()
    class UnknownException : RepositoryException()
    class ApiErrorException(message: String?) : RepositoryException(message)
}
