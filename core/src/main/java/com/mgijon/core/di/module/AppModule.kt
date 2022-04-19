package com.mgijon.core.di.module

import android.content.Context
import com.mgijon.baseapp.util.StringHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideStringHelper(@ApplicationContext context: Context): StringHelper = StringHelper(context)

}
