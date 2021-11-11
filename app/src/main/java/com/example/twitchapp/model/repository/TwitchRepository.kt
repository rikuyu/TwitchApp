package com.example.twitchapp.model.repository

import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.di.NetworkModule.PAGE_SIZE
import com.example.twitchapp.model.api.TwitchApi
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class TwitchRepository @Inject constructor(
    private val db: TwitchDatabase,
    private val streamApi: TwitchApi
) {
    suspend fun fetchStreamPaging(page: Int): Response<Streams> {
        return streamApi.fetchStream(PAGE_SIZE, page)
    }

    suspend fun fetchClip(gameTitle: String): Response<ClipResponse> {
        return streamApi.fetchClip(gameTitle = gameTitle)
    }

    suspend fun insertClip(clip: Clip) {
        db.twitchDao().insertClip(clip)
    }

    suspend fun deleteClip(clip: Clip) {
        db.twitchDao().deleteClip(clip)
    }

    fun getFavoriteClips(): Flow<List<Clip>> {
        return db.twitchDao().getAllClips()
    }
}