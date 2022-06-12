package com.mgijon.marvelapi.persistence.di

import android.content.Context
import androidx.room.Room
import com.mgijon.marvelapi.persistence.AppDataBase
import com.mgijon.marvelapi.persistence.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "database-name").build()

    @Provides
    @Singleton
    fun provideCharacterDao(appDataBase: AppDataBase): CharacterDao = appDataBase.characterDao()

}