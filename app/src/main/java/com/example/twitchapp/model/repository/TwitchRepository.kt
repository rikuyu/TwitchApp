package com.example.twitchapp.model.repository

import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TwitchRepository {

    suspend fun fetchStream(page: Int): Response<Streams>

    suspend fun fetchClip(gameTitle: String): Response<ClipResponse>

    suspend fun insertClip(clip: Clip)

    suspend fun deleteClip(clip: Clip)

    fun getFavoriteGame(): Flow<List<Clip>>
}