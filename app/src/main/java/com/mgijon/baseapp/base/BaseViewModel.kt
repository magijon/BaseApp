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

    private val _state: MutableLiveData<StateBase> = MutableLiveData()
    val state: LiveData<StateBase> = _state


    fun setState(resource: Resource<*>, stateBase: StateBase) {
        when (resource) {
            is Resource.Success -> _state.postValue(stateBase)
            is Resource.Error -> _state.postValue(
                StateBase.ErrorStateBase(
                    ErrorException.BaseErrorExceptionMessage(
                        messageString = resource.message ?: "An unexpected error occurred!"
                    )
                )
            )
            is Resource.Loading -> _state.postValue(StateBase.LoadingStateBase)
        }
    }
}