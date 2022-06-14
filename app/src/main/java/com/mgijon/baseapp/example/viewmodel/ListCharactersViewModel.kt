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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getAllCharacterUseCase: GetAllCharactersUseCase,
    private val getFilterCharacterUseCase: GetFilterCharacterUseCase,
    private val removeVisibilityUseCase: RemoveVisibilityUseCase,
    private val getNewCharactersUseCase: GetNewCharactersUseCase
) : BaseViewModel() {

    private val characterList: MutableList<CharacterUI> = mutableListOf()
    var isFilter = false

    override fun startLogic(bundle: Bundle?) {
        isFilter = false
        if (characterList.isNotEmpty()) {
            setState(Resource.Success(characterList), CharacterListState(characters = characterList))
        } else {
            getCharacters(firstTime = true)
        }
    }

    fun getCharacters(firstTime: Boolean = false) {
        if (firstTime) {
            viewModelScope.launch(Dispatchers.IO) {
                getAllCharacterUseCase().collect { result ->
                    val list = result.data
                    val characters = list?.map { character -> CharacterUI.mapperCharacterUI(character) }
                    characters?.let {
                        characterList.addAll(it)
                    }
                    setState(result, CharacterListState(characters ?: emptyList()))
                    if (result is Resource.Success && result.data?.isEmpty() == true){
                        getRemoteCharacters(0)
                    }
                }
            }
        } else {
            getRemoteCharacters(characterList.size)
        }
    }

    private fun getRemoteCharacters(characterPosition: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getNewCharactersUseCase(characterPosition.toLong()).collect { result ->
                val listData = result.data
                listData?.let { list ->
                    list.map { characterList.add(CharacterUI.mapperCharacterUI(it)) }
                }
                setState(result, NewCharacterListState(listData?.map { CharacterUI.mapperCharacterUI(it) } ?: emptyList()))
            }
        }
    }

    fun filterCharacters(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isFilter = name.isNotEmpty()
            getFilterCharacterUseCase.invoke(name).collect { result ->
                val list = result.data
                val characters = list?.map { character -> CharacterUI.mapperCharacterUI(character) }
                setState(result, CharacterListState(characters ?: emptyList()))
            }
        }
    }

    fun removeVisibility(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeVisibilityUseCase.invoke(id).collect { result ->
                result.data?.let {
                    characterList.clear()
                    val list = result.data
                    val characters = list?.map { character -> CharacterUI.mapperCharacterUI(character) }
                    characters?.let {
                        characterList.addAll(it)
                    }
                    setState(result, CharacterListState(characters ?: emptyList()))
                }
            }
        }
    }
}