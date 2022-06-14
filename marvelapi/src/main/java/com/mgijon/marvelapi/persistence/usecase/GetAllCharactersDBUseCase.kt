package com.mgijon.marvelapi.persistence.usecase

import com.mgijon.domain.common.Resource
import com.mgijon.data.persistence.dao.CharacterDao
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCharactersDBUseCase @Inject constructor(private val characterDao: CharacterDao) {

    operator fun invoke(): Flow<Resource<List<Character>>> = flow {
        try {
            emit(Resource.Loading())
            val characterDataWrapper = characterDao.getAll().filter { it.visible }.map { it.characterMapper() }
            emit(Resource.Success(characterDataWrapper))
        } catch (e: IOException) {
            emit(Resource.Error("Error getting data from data base"))
        }
    }
}