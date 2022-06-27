package com.mgijon.data.repository

import com.mgijon.data.api.MarvelApi
import com.mgijon.data.extensions.toCharacter
import com.mgijon.data.extensions.toCharacterEntity
import com.mgijon.data.persistence.dao.CharacterDao
import com.mgijon.data.util.MD5Tool
import com.mgijon.domain.model.marvel.Character
import com.mgijon.domain.repository.marvel.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val marvelApi: MarvelApi,
    private val mD5Tool: MD5Tool,
    private val characterDao: CharacterDao
) : MarvelRepository {

    override suspend fun getNewCharacters(): List<Character>? {
        val newCharacters = marvelApi.getAllCharacters(
            mD5Tool.getPublicKey(),
            mD5Tool.getTs(),
            mD5Tool.getHash(),
            characterDao.countCharacters().toLong()
        ).data?.results
        newCharacters?.map { it.toCharacterEntity() }?.let {
            characterDao.insertAll(it)
        }
        return newCharacters?.map { it.toCharacter() }
    }


    override suspend fun getOneCharacter(characterId: String): Character? {
        val character = characterDao.getOne(characterId)
        return character?.toCharacter() ?: marvelApi.getOneCharacter(
            characterId,
            mD5Tool.getPublicKey(),
            mD5Tool.getTs(),
            mD5Tool.getHash(),
        ).data?.results?.map { it.toCharacter() }?.get(0)
    }

    override suspend fun getAll(): List<Character> {
        return if (characterDao.countCharacters() > 0)
            characterDao.getAll().filter { it.visible }.map { it.toCharacter() }
        else
            getNewCharacters() ?: emptyList()
    }

    override suspend fun getFilter(name: String): List<Character> = characterDao.getFilter(name).filter { it.visible }.map { it.toCharacter() }

    override suspend fun removeVisibility(characterId: String): List<Character> {
        characterDao.removeVisibility(characterId)
        return getAll()
    }
}