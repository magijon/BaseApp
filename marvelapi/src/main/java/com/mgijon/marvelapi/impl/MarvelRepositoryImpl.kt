package com.mgijon.marvelapi.impl

import com.mgijon.baseapp.example.model.CharacterUI
import com.mgijon.data.util.MD5Tool
import com.mgijon.data.api.MarvelApi
import com.mgijon.marvelapi.repository.MarvelRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val mD5Tool: MD5Tool
) : MarvelRepository {
    override suspend fun getAllCharacters(offset: Long): List<com.mgijon.baseapp.example.model.CharacterUI> =
        api.getAllCharacters(
            mD5Tool.getPublicKey(),
            mD5Tool.getTs(),
            mD5Tool.getHash(),
            offset
        ).data?.results?.map { it.toCharacter() } ?: emptyList()

    override suspend fun getOneCharacter(characterId: String): com.mgijon.baseapp.example.model.CharacterUI? =
        api.getOneCharacter(
            characterId,
            mD5Tool.getPublicKey(),
            mD5Tool.getTs(),
            mD5Tool.getHash(),
        ).data?.results?.map { it.toCharacter() }?.get(0)

}