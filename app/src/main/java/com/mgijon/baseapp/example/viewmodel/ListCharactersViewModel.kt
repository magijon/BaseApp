package com.mgijon.baseapp.example.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.mgijon.baseapp.base.BaseViewModel
import com.mgijon.baseapp.example.model.StateBase.CharacterListState
import com.mgijon.data.model.Character
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

    private val characterList: MutableList<Character> = mutableListOf()

    override fun startLogic(bundle: Bundle?) {
        if (characterList.isNotEmpty()) {
            setState(Resource.Success(characterList), CharacterListState(characters = characterList))
        } else {
            getCharacters()
        }
    }

    fun getCharacters() {
        getAllCharacterUseCase(characterList.size.toLong()).onEach { result ->
            result.data?.map {
                characterList.add(it)
            }
            setState(result, CharacterListState(characters = result.data ?: emptyList()))
        }.launchIn(viewModelScope)
    }
}