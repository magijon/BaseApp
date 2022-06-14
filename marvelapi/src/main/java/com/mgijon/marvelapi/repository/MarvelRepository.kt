package com.mgijon.marvelapi.repository

import com.mgijon.baseapp.example.model.CharacterUI
import com.mgijon.domain.repository.BaseRepository

interface MarvelRepository : BaseRepository {
    suspend fun getAllCharacters(offset : Long): List<com.mgijon.baseapp.example.model.CharacterUI>
    suspend fun getOneCharacter(characterId : String): com.mgijon.baseapp.example.model.CharacterUI?
}