package com.mgijon.data.api

import com.mgijon.data.model.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
        @Query("offset") offset: Long
    ) : CharacterDataWrapper

    @GET("v1/public/characters/{characterId}")
    suspend fun getOneCharacter(
        @Path("characterId") characterId: String,
        @Query("apikey") apiKey: String,
        @Query("ts") ts: Long,
        @Query("hash") hash: String,
    ) : CharacterDataWrapper
}