package com.mgijon.marvelapi.repository

import com.mgijon.data.model.Character
import com.mgijon.domain.repository.BaseRepository

interface MarvelRepository : BaseRepository {
    suspend fun getAllCharacters(offset : Long): List<Character>
    suspend fun getOneCharacter(characterId : String): Character?
}