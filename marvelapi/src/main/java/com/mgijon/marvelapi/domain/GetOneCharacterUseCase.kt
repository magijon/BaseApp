package com.mgijon.marvelapi.domain

import com.mgijon.domain.common.Resource
import com.mgijon.data.model.Character
import com.mgijon.marvelapi.repository.MarvelRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetOneCharacterUseCase @Inject constructor(
    private val marvelRepository: MarvelRepository
) {
    operator fun invoke(id : String): Flow<Resource<Character>> = flow {
        try {
            emit(Resource.Loading())
            marvelRepository.getOneCharacter(id)?.let {
                emit(Resource.Success(it))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection!"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        }
    }
}