package com.mgijon.baseapp.example.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.mgijon.baseapp.base.BaseViewModel
import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.baseapp.example.util.ConstantsNavigation.CHARACTER_ID
import com.mgijon.marvelapi.domain.GetOneCharacterUseCase
import com.mgijon.marvelapi.persistence.domain.GetOneCharacterDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterViewModel @Inject constructor(private val getOneCharacterDBUseCase: GetOneCharacterDBUseCase) : BaseViewModel() {

    override fun startLogic(bundle: Bundle?) {
        bundle?.getString(CHARACTER_ID)?.let {
            getCharacter(it)
        }
    }

    private fun getCharacter(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getOneCharacterDBUseCase(id).collect { result ->
                setStateDB(result, StateBase.CharacterState(result.data))
            }
        }
    }

}
