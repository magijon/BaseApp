package com.mgijon.baseapp.example.model

import com.mgijon.baseapp.example.model.ErrorException.BaseErrorException
import com.mgijon.data.model.Character

sealed class StateBase(val isLoading: Boolean = false, val error: ErrorException? = BaseErrorException) {
    object LoadingStateBase : StateBase(isLoading = true)
    class ErrorStateBase(message: ErrorException) : StateBase(error = message)
    data class CharacterListState(val characters: List<Character> = emptyList()) : StateBase()
    data class CharacterState(val character: Character?) : StateBase()
}


