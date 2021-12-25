package com.example.twitchapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twitchapp.model.data.clipdata.Clip

@Dao
interface TwitchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClip(clip: Clip)

    @Delete
    suspend fun deleteClip(clip: Clip)

    @Query("SELECT * FROM clips")
    fun getAllClips(): List<Clip>
}