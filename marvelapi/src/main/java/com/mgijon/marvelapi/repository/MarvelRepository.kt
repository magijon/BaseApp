package com.mgijon.marvelapi.repository

import com.mgijon.domain.model.Character
import com.mgijon.domain.repository.BaseRepository

interface MarvelRepository : BaseRepository {
    suspend fun getAllCharacters(): List<Character>
}