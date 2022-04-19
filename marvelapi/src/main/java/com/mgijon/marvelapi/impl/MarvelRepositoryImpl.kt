package com.mgijon.marvelapi.impl

import com.mgijon.marvelapi.api.MarvelApi
import com.mgijon.data.extensions.toCharacter
import com.mgijon.data.util.MD5Tool
import com.mgijon.domain.model.Character
import com.mgijon.marvelapi.repository.MarvelRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val mD5Tool: MD5Tool
) : MarvelRepository {
    override suspend fun getAllCharacters(): List<Character> =
        api.getAllCharacters(
            mD5Tool.getPublicKey(),
            mD5Tool.getTs(),
            mD5Tool.getHash()
        ).data?.results?.map { it.toCharacter() } ?: emptyList()
}