package com.example.twitchapp.di

import com.example.twitchapp.model.api.TwitchApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.twitch.tv/kraken/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    const val PAGE_SIZE = 7

    @Singleton
    @Provides
    fun provideRetrofitInstance(): TwitchApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TwitchApi::class.java)
    }
}