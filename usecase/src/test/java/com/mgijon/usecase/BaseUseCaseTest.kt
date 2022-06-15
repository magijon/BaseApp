package com.mgijon.usecase

import com.mgijon.domain.repository.marvel.MarvelRepository
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations


abstract class BaseUseCaseTest<T>(private val factory: (MarvelRepository) -> T) {

    abstract var usecase: T

    @Mock
    lateinit var repository: MarvelRepository


    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        usecase = factory.invoke(repository)
    }

}