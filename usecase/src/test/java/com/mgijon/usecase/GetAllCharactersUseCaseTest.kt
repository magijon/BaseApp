package com.mgijon.usecase

import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetAllCharactersUseCase
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetAllCharactersUseCaseTest : BaseUseCaseTest<GetAllCharactersUseCase>(::GetAllCharactersUseCase) {

    override lateinit var usecase: GetAllCharactersUseCase

    @Mock
    lateinit var character: Character

    @Test
    fun `verify invoke and loading`() {
        runBlocking {
            Mockito.`when`(repository.getAll()).thenReturn(listOf(character, character))

            var isLoading = false
            var data: List<Character>? = listOf()
            usecase.invoke().collect {
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
            verify(repository, times(1)).getAll()
        }
    }

    @Test
    fun `error getAll`() {
        runBlocking {
            Mockito.`when`(repository.getAll()).thenThrow(RuntimeException())
            var message: String? = null
            usecase.invoke().collect {
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