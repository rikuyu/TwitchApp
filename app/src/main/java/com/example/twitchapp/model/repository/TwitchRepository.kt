package com.example.twitchapp.model.repository

import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.RetrofitInstance
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import retrofit2.Response

class TwitchRepository(
    private val db: TwitchDatabase?
) {
    suspend fun fetchStream(gameTitle: String): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream(gameTitle)
    }

    suspend fun fetchClip(gameTitle: String): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = gameTitle)
    }

    suspend fun insertClip(clip: Clip){
        db!!.twitchDao().insertClip(clip)
    }

    suspend fun deleteClip(clip: Clip){
        db!!.twitchDao().deleteClip(clip)
    }

    fun getFavoriteClips(): List<Clip>{
        return db!!.twitchDao().getAllClips()
    }
}