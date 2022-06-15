package com.mgijon.baseapp.example.model

import com.mgijon.baseapp.example.model.ErrorException.BaseErrorException

sealed class StateBase(val isLoading: Boolean = false, val error: ErrorException? = BaseErrorException) {
    object LoadingStateBase : StateBase(isLoading = true)
    class ErrorStateBase(message: ErrorException) : StateBase(error = message)
    data class CharacterListState(val characters: List<CharacterUI> = emptyList()) : StateBase()
    data class NewCharacterListState(val characters: List<CharacterUI> = emptyList()) : StateBase()
    data class CharacterState(val character: CharacterUI?) : StateBase()
}


