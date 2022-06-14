package com.mgijon.usecase

import com.mgijon.usecase.marvel.GetAllCharactersUseCase

class GetAllCharactersUseCaseTest : BaseUseCaseTest<GetAllCharactersUseCase>(::GetAllCharactersUseCase) {

    override lateinit var usecase: GetAllCharactersUseCase



}