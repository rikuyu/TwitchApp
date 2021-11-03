package com.example.twitchapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.twitchapp.model.data.clipdata.Clip

@Database(entities = [Clip::class], version = 1, exportSchema = false)
abstract class TwitchDatabase : RoomDatabase() {
    abstract fun twitchDao(): TwitchDao
}