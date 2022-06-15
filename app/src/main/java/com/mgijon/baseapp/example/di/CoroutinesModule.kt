package com.mgijon.baseapp.example.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesModule {

    @Provides
    @Singleton
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}