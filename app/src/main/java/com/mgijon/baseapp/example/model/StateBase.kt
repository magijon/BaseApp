package com.mgijon.baseapp.example.model

import com.mgijon.baseapp.example.model.ErrorException.*
import com.mgijon.domain.model.Character

sealed class StateBase(val isLoading: Boolean = false, val error: ErrorException? = BaseErrorException) {
    data class CharacterListState(
        val characters: List<Character> = emptyList(),
        val loading: Boolean = false,
        val errorException: ErrorException? = BaseErrorException
    ) : StateBase(loading, errorException)
}


