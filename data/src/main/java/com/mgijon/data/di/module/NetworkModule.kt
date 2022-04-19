package com.mgijon.data.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(60L, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60L, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60L, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }

}