package com.mgijon.baseapp.example.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.mgijon.baseapp.base.BaseViewModel
import com.mgijon.baseapp.example.model.CharacterUI
import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.baseapp.example.util.ConstantsNavigation.CHARACTER_ID
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetOneCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterViewModel @Inject constructor(private val getOneCharacterUseCase: GetOneCharacterUseCase) : BaseViewModel() {

    override fun startLogic(bundle: Bundle?) {
        bundle?.getString(CHARACTER_ID)?.let {
            getCharacter(it)
        }
    }

    private fun getCharacter(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getOneCharacterUseCase(id).collect { result ->
                val character = result.data ?: Character("","", "", "")
                setState(result, StateBase.CharacterState(CharacterUI.mapperCharacterUI(character)))
            }
        }
    }

}
