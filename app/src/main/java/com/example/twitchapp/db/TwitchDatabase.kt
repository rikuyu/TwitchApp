package com.example.twitchapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twitchapp.model.data.clipdata.Clip

@Database(entities = [Clip::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TwitchDatabase : RoomDatabase() {

    abstract fun twitchDao(): TwitchDao

}