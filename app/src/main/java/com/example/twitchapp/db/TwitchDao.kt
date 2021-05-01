package com.example.twitchapp.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
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