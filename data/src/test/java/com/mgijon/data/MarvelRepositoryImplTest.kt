package com.mgijon.data

import kotlinx.coroutines.Dispatchers

class MarvelRepositoryImplTest {

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        MockitoAnnotations.openMocks(this)

        initFunc.invoke()
    }
}