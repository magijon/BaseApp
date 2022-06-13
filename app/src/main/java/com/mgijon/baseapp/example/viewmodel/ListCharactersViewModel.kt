package com.mgijon.baseapp.example.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.mgijon.baseapp.base.BaseViewModel
import com.mgijon.baseapp.example.model.StateBase.CharacterListState
import com.mgijon.data.model.Character
import com.mgijon.domain.common.Resource
import com.mgijon.marvelapi.domain.GetAllCharactersUseCase
import com.mgijon.marvelapi.persistence.domain.GetAllCharactersDBUseCase
import com.mgijon.marvelapi.persistence.domain.GetFilterCharactersDBUseCase
import com.mgijon.marvelapi.persistence.domain.InsertAllCharactersDBUseCase
import com.mgijon.marvelapi.persistence.domain.RemoveVisibilityDBUseCae
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getAllCharacterUseCase: GetAllCharactersUseCase,
    private val getAllCharactersDBUseCase: GetAllCharactersDBUseCase,
    private val insertAllCharactersDBUseCase: InsertAllCharactersDBUseCase,
    private val getFilterCharactersDBUseCase: GetFilterCharactersDBUseCase,
    private val removeVisibilityDBUseCae : RemoveVisibilityDBUseCae
) : BaseViewModel() {

    private val characterList: MutableList<Character> = mutableListOf()
    var isFilter = false

    override fun startLogic(bundle: Bundle?) {
        isFilter = false
        if (characterList.isNotEmpty()) {
            setStateDB(Resource.Success(characterList), CharacterListState(characters = characterList))
        } else {
            getCharacters(firstTime = true)
        }
    }

    fun getCharacters(firstTime: Boolean = false) {
        if (firstTime) {
            viewModelScope.launch(Dispatchers.IO) {
                getAllCharactersDBUseCase().collect { result ->
                    result.data?.let {
                        characterList.addAll(it)
                        setStateDB(result, CharacterListState(it))
                    }
                    if (result.data?.isEmpty() == true)
                        getRemoteCharacters(0)
                }
            }
        } else {
            getRemoteCharacters(characterList.size)
        }
    }

    private fun getRemoteCharacters(characterPosition: Int) {
        getAllCharacterUseCase(characterPosition.toLong()).onEach { result ->
            result.data?.let { list ->
                insertAllCharacters(list)
                list.map { characterList.add(it) }
            }
            setStateApi(result, CharacterListState(result.data ?: emptyList()))
        }.launchIn(viewModelScope)
    }

    private fun insertAllCharacters(list: List<Character>) {
        viewModelScope.launch(Dispatchers.IO) {
            insertAllCharactersDBUseCase(list)
        }
    }

    fun filterCharacters(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isFilter = name.isNotEmpty()
            getFilterCharactersDBUseCase.invoke(name).collect { result ->
                result.data?.let {
                    characterList.addAll(it)
                    setStateDB(result, CharacterListState(it))
                }
            }
        }
    }

    fun removeVisibility(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeVisibilityDBUseCae.invoke(id).collect{ result ->
                result.data?.let {
                    characterList.clear()
                    characterList.addAll(it)
                    setStateDB(result, CharacterListState(it))
                }
            }
        }
    }
}