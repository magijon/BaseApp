package com.mgijon.baseapp.example.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.mgijon.baseapp.base.BaseViewModel
import com.mgijon.baseapp.example.model.CharacterUI
import com.mgijon.baseapp.example.model.StateBase.CharacterListState
import com.mgijon.baseapp.example.model.StateBase.NewCharacterListState
import com.mgijon.domain.common.Resource
import com.mgijon.usecase.marvel.GetAllCharactersUseCase
import com.mgijon.usecase.marvel.GetFilterCharacterUseCase
import com.mgijon.usecase.marvel.GetNewCharactersUseCase
import com.mgijon.usecase.marvel.RemoveVisibilityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getAllCharacterUseCase: GetAllCharactersUseCase,
    private val getFilterCharacterUseCase: GetFilterCharacterUseCase,
    private val removeVisibilityUseCase: RemoveVisibilityUseCase,
    private val getNewCharactersUseCase: GetNewCharactersUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    var isFilter = false

    override fun startLogic(bundle: Bundle?) {
        isFilter = false
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch(dispatcher) {
            getAllCharacterUseCase().collect { result ->
                val characters = result.data?.map { character -> CharacterUI.mapperCharacterUI(character) }
                setState(result, CharacterListState(characters ?: emptyList()))
            }
        }
    }

    fun getNewCharacters() {
        viewModelScope.launch(dispatcher) {
            getNewCharactersUseCase().collect { result ->
                setState(result, NewCharacterListState(result.data?.map { CharacterUI.mapperCharacterUI(it) } ?: emptyList()))
            }
        }
    }

    fun filterCharacters(name: String) {
        viewModelScope.launch(dispatcher) {
            isFilter = name.isNotEmpty()
            getFilterCharacterUseCase.invoke(name).collect { result ->
                val list = result.data
                val characters = list?.map { character -> CharacterUI.mapperCharacterUI(character) }
                setState(result, CharacterListState(characters ?: emptyList()))
            }
        }
    }

    fun removeVisibility(id: String) {
        viewModelScope.launch(dispatcher) {
            removeVisibilityUseCase.invoke(id).collect { result ->
                result.data?.let {
                    val list = result.data
                    val characters = list?.map { character -> CharacterUI.mapperCharacterUI(character) }
                    setState(result, CharacterListState(characters ?: emptyList()))
                } ?: kotlin.run {
                    setState(result, CharacterListState(emptyList()))
                }
            }
        }
    }
}