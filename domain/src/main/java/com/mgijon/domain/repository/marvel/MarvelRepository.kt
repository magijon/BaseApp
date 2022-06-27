package com.mgijon.domain.repository.marvel

import com.mgijon.domain.model.marvel.Character

interface MarvelRepository {
    suspend fun getNewCharacters(): List<Character>?
    suspend fun getOneCharacter(characterId : String): Character?
    suspend fun getAll(): List<Character>
    suspend fun getFilter(name : String): List<Character>
    suspend fun removeVisibility(characterId : String) : List<Character>
}