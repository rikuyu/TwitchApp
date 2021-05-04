package com.example.twitchapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twitchapp.model.data.clipdata.Clip

@Database(entities = [Clip::class], version = 2)
@TypeConverters(Converters::class)
abstract class TwitchDatabase: RoomDatabase() {

    abstract fun twitchDao(): TwitchDao

    companion object {
        private var database: TwitchDatabase? = null

        private const val DATABASE_NAME = "twitch_database"

        @Synchronized
        fun getInstance(context: Context): TwitchDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(context.applicationContext, TwitchDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return database
        }
    }
}