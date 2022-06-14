package com.mgijon.usecase

import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetAllCharactersUseCase
import com.mgijon.usecase.marvel.GetFilterCharacterUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetFilterCharactersUseCaseTest : BaseUseCaseTest<GetFilterCharacterUseCase>(::GetFilterCharacterUseCase) {

    override lateinit var usecase: GetFilterCharacterUseCase

    @Mock
    lateinit var character: Character

    @Test
    fun `verify invoke and loading`() {
        runBlocking {
            val id = "id"
            Mockito.`when`(repository.getFilter(id)).thenReturn(listOf(character, character))

            var isLoading = false
            var data: List<Character>? = listOf()
            usecase.invoke(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        isLoading = true
                    }
                    is Resource.Success -> {
                        data = it.data
                    }
                    else -> {}
                }
                assert(isLoading)
            }
            assert(data!![0] == character)
            verify(repository, times(1)).getFilter(any())
        }
    }

    @Test
    fun `error getFilter`() {
        runBlocking {
            val id = "id"
            Mockito.`when`(repository.getFilter(any())).thenThrow(RuntimeException())
            var message: String? = null
            usecase.invoke(id).collect {
                when (it) {
                    is Resource.Error -> {
                        message = it.message
                    }
                    else -> {}
                }
            }.run {
                assert(message != null)
            }
        }
    }

}