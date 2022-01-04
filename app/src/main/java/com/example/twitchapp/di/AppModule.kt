package com.example.twitchapp.di

import android.content.Context
import androidx.room.Room
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.api.TwitchApi
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.model.repository.TwitchRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindTwitchRepository(twitchRepositoryImpl: TwitchRepositoryImpl): TwitchRepository

    @InstallIn(SingletonComponent::class)
    @Module
    internal object TwitchModule {

        @Provides
        @Singleton
        fun provideTwitchDatabase(@ApplicationContext appContext: Context): TwitchDatabase {
            return Room.databaseBuilder(
                appContext,
                TwitchDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries().build()
        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(): TwitchApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(TwitchApi::class.java)
        }

        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        private const val BASE_URL = "https://api.twitch.tv/kraken/"
        private const val DATABASE_NAME = "twitch_database"
    }
}