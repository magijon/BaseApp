package com.mgijon.usecase

import com.mgijon.domain.common.Resource
import com.mgijon.domain.model.marvel.Character
import com.mgijon.usecase.marvel.GetAllCharactersUseCase
import com.mgijon.usecase.marvel.GetFilterCharacterUseCase
import com.mgijon.usecase.marvel.GetNewCharactersUseCase
import com.mgijon.usecase.marvel.GetOneCharacterUseCase
import com.mgijon.usecase.marvel.RemoveVisibilityUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import java.io.IOException
import java.lang.RuntimeException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class RemoveVisibilityUseCaseTest : BaseUseCaseTest<RemoveVisibilityUseCase>(::RemoveVisibilityUseCase) {

    override lateinit var usecase: RemoveVisibilityUseCase

    @Mock
    lateinit var character: Character

    @Test
    fun `verify invoke and loading`() {
        runBlocking {
            val id = "id"
            Mockito.`when`(repository.getAll()).thenReturn(listOf(character, character))

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
            verify(repository, times(1)).removeVisibility(any())
            verify(repository, times(1)).getAll()
        }
    }

    @Test
    fun `error getFilter`() {
        runBlocking {
            val id = "id"
            Mockito.`when`(repository.removeVisibility(any())).thenThrow(RuntimeException())
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