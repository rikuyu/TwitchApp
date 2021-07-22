package com.example.twitchapp.di

import android.content.Context
import androidx.room.Room
import com.example.twitchapp.db.TwitchDao
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.api.TwitchApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val BASE_URL = "https://api.twitch.tv/kraken/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideRetrofitInstance(): TwitchApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TwitchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTwitchDao(twitchDatabase: TwitchDatabase): TwitchDao{
        return twitchDatabase.twitchDao()
    }

    @Provides
    @Singleton
    fun provideTwitchDatabase(@ApplicationContext appContext: Context): TwitchDatabase {
        return Room.databaseBuilder(
            appContext,
            TwitchDatabase::class.java,
            "twitch_database"
        ).allowMainThreadQueries().build()
    }
}