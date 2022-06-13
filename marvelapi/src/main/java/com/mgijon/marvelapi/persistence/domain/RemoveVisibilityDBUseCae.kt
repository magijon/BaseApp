package com.mgijon.marvelapi.persistence.domain

import com.mgijon.data.model.Character
import com.mgijon.domain.common.Resource
import com.mgijon.marvelapi.persistence.dao.CharacterDao
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveVisibilityDBUseCae @Inject constructor(private val characterDao: CharacterDao) {

    operator fun invoke(id : String): Flow<Resource<List<Character>>> = flow {
        try {
            emit(Resource.Loading())
            characterDao.removeVisibility(id)
            val characterDataWrapper = characterDao.getAll().map { it.characterMapper() }
            emit(Resource.Success(characterDataWrapper))
        } catch (e: IOException) {
            emit(Resource.Error("Error getting data from data base"))
        }
    }
}