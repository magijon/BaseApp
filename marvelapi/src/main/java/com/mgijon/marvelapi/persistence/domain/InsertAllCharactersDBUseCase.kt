package com.mgijon.marvelapi.persistence.domain

import com.mgijon.data.model.Character
import com.mgijon.marvelapi.persistence.dao.CharacterDao
import com.mgijon.marvelapi.persistence.entity.CharacterEntity
import javax.inject.Inject

class InsertAllCharactersDBUseCase @Inject constructor(private val characterDao: CharacterDao) {

    operator fun invoke(list: List<Character>) {
        characterDao.insertAll(list.map { CharacterEntity.characterEntityMapper(it) })
    }
}