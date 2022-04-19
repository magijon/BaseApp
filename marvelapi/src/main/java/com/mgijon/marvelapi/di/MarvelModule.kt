package com.mgijon.marvelapi.di

import com.mgijon.data.util.MD5Tool
import com.mgijon.marvelapi.Constants
import com.mgijon.marvelapi.api.MarvelApi
import com.mgijon.marvelapi.impl.MarvelRepositoryImpl
import com.mgijon.marvelapi.repository.MarvelRepository
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
    fun provideMarvelRepository(marvelApi: MarvelApi): MarvelRepository =
        MarvelRepositoryImpl(marvelApi, provideMd5Tool())

    @Provides
    @Singleton
    fun provideMd5Tool(): MD5Tool = MD5Tool(Constants.PUBLIC_KEY, Constants.PRIVATE_KEY)

}