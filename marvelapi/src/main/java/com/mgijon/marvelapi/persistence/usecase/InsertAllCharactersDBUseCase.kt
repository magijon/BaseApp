package com.mgijon.marvelapi.persistence.usecase

import com.mgijon.data.persistence.dao.CharacterDao
import com.mgijon.data.persistence.entity.CharacterEntity
import javax.inject.Inject

class InsertAllCharactersDBUseCase @Inject constructor(private val characterDao: CharacterDao) {

    operator fun invoke(list: List<Character>) {
        characterDao.insertAll(list.map { CharacterEntity.characterEntityMapper(it) })
    }
}