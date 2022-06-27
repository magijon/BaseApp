package com.mgijon.usecase

import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetNewCharactersUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetNewCharactersUseCaseTest : BaseUseCaseTest<GetNewCharactersUseCase>(::GetNewCharactersUseCase) {

    override lateinit var usecase: GetNewCharactersUseCase

    @Mock
    lateinit var character: Character

    @Test
    fun `verify invoke and loading`() {
        runBlocking {
            Mockito.`when`(repository.getNewCharacters()).thenReturn(listOf(character, character))

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
            verify(repository, times(1)).getNewCharacters()
        }
    }

    @Test
    fun `error getNewCharacters`() {
        runBlocking {
            val number: Long = 20
            Mockito.`when`(repository.getNewCharacters()).thenThrow(RuntimeException())
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