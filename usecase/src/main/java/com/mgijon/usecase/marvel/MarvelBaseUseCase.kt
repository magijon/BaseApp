package com.mgijon.usecase.marvel

import com.mgijon.domain.common.Resource
import java.io.IOException
import java.lang.Exception
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class MarvelBaseUseCase {

    fun <T> getFlow(success: suspend () -> Resource.Success<T>, extraFunction: (suspend () -> Unit)? = null): Flow<Resource<T>> =
        flow {
            try {
                emit(Resource.Loading())
                extraFunction?.invoke()
                emit(success.invoke())
            } catch (e: IOException) {
                emit(Resource.Error("Unable to access data, database error or network access"))
            }catch (e : Exception){
                emit(Resource.Error("An unexpected error has occurred"))
            }
        }
}