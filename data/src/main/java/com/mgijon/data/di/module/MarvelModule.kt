package com.mgijon.data.di.module

import com.mgijon.data.Constants
import com.mgijon.data.api.MarvelApi
import com.mgijon.data.persistence.AppDataBase
import com.mgijon.data.persistence.dao.CharacterDao
import com.mgijon.data.repository.MarvelRepositoryImpl
import com.mgijon.data.util.MD5Tool
import com.mgijon.domain.repository.marvel.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class MarvelModule {
    @Provides
    @Singleton
    fun provideMarvelApi(client: OkHttpClient): MarvelApi =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(MarvelApi::class.java)

    @Provides
    @Singleton
    fun provideMarvelRepository(marvelApi: MarvelApi, mD5Tool: MD5Tool, characterDao: CharacterDao): MarvelRepository =
        MarvelRepositoryImpl(marvelApi, mD5Tool, characterDao)

    @Provides
    @Singleton
    fun provideMd5Tool(): MD5Tool = MD5Tool(Constants.PUBLIC_KEY, Constants.PRIVATE_KEY)

    @Provides
    @Singleton
    fun provideCharacterDao(appDataBase: AppDataBase): CharacterDao = appDataBase.characterDao()

}