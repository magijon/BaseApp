package com.mgijon.baseapp.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mgijon.baseapp.example.model.ErrorException
import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.domain.common.Resource


abstract class BaseViewModel : ViewModel() {
    abstract fun startLogic(bundle: Bundle?)

    private val _stateApi: MutableLiveData<StateBase> = MutableLiveData()
    val stateApi: LiveData<StateBase> = _stateApi
    private val _stateDB: MutableLiveData<StateBase> = MutableLiveData()
    val stateDB: LiveData<StateBase> = _stateDB


    fun setStateApi(resource: Resource<*>, stateBase: StateBase) {
        when (resource) {
            is Resource.Success -> _stateApi.postValue(stateBase)
            is Resource.Error -> _stateApi.value = StateBase.ErrorStateBase(
                ErrorException.BaseErrorExceptionMessage(
                    messageString = resource.message ?: "An unexpected error occurred!"
                )
            )
            is Resource.Loading -> _stateApi.value = StateBase.LoadingStateBase
        }
    }

    fun setStateDB(resource: Resource<*>, stateBase: StateBase) {
        when (resource) {
            is Resource.Success -> _stateDB.postValue(stateBase)
            is Resource.Error -> _stateDB.value = StateBase.ErrorStateBase(
                ErrorException.BaseErrorExceptionMessage(
                    messageString = resource.message ?: "An unexpected error occurred!"
                )
            )
            is Resource.Loading -> _stateDB.postValue(StateBase.LoadingStateBase)
        }
    }
}