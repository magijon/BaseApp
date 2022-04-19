package com.mgijon.baseapp.example.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mgijon.baseapp.base.BaseViewModel
import com.mgijon.baseapp.example.model.ErrorException
import com.mgijon.baseapp.example.model.StateBase.CharacterListState
import com.mgijon.domain.common.Resource
import com.mgijon.marvelapi.domain.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getAllCharacterUseCase: GetAllCharactersUseCase
) : BaseViewModel() {

    private val _state: MutableLiveData<CharacterListState> = MutableLiveData()
    val state: LiveData<CharacterListState> = _state

    override fun startLogic(bundle: Bundle?) {
        getCharacters()
    }

    private fun getCharacters() {
        getAllCharacterUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = CharacterListState(characters = result.data ?: emptyList())
                is Resource.Error -> _state.value =
                    CharacterListState(
                        errorException = ErrorException.BaseErrorExceptionMessage(
                            result.message ?: "An unexpected error occurred!"
                        )
                    )
                is Resource.Loading -> _state.value = CharacterListState(loading = true)
            }
        }.launchIn(viewModelScope)
    }
}