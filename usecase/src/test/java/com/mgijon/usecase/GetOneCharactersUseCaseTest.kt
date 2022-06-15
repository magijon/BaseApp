package com.mgijon.usecase

import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetAllCharactersUseCase
import com.mgijon.usecase.marvel.GetFilterCharacterUseCase
import com.mgijon.usecase.marvel.GetNewCharactersUseCase
import com.mgijon.usecase.marvel.GetOneCharacterUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import java.io.IOException
import java.lang.RuntimeException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetOneCharactersUseCaseTest : BaseUseCaseTest<GetOneCharacterUseCase>(::GetOneCharacterUseCase) {

    override lateinit var usecase: GetOneCharacterUseCase

    @Mock
    lateinit var character: Character

    @Test
    fun `verify invoke and loading`() {
        runBlocking {
            val id = "id"
            Mockito.`when`(repository.getOneCharacter(id)).thenReturn(character)

            var isLoading = false
            var data: Character? = null
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
            assert(data == character)
            verify(repository, times(1)).getOneCharacter(any())
        }
    }

    @Test
    fun `error getOneCharacter`() {
        runBlocking {
            val id = "id"
            Mockito.`when`(repository.getOneCharacter(any())).thenThrow(RuntimeException())
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