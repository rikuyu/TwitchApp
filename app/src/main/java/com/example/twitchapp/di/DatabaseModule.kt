package com.example.twitchapp.di

import android.content.Context
import androidx.room.Room
import com.example.twitchapp.db.TwitchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
//    @Provides
//    @Singleton
//    fun provideTwitchDao(twitchDatabase: TwitchDatabase): TwitchDao {
//        return twitchDatabase.twitchDao()
//    }

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