package com.mgijon.usecase.marvel

import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.domain.repository.marvel.MarvelRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class RemoveVisibilityUseCase @Inject constructor(private val marvelRepository: MarvelRepository) : MarvelBaseUseCase() {

    operator fun invoke(id: String): Flow<Resource<List<Character>>> =
        getFlow({ Resource.Success(marvelRepository.getAll()) }) { marvelRepository.removeVisibility(id) }
}