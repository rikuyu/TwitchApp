package com.example.twitchapp.model.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.api.TwitchApi
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class TwitchRepositoryImpl @Inject constructor(
    private val db: TwitchDatabase,
    private val streamApi: TwitchApi
) : TwitchRepository {

    override suspend fun fetchStreamPaging(page: Int): Response<Streams> {
        return streamApi.fetchStream(PAGE_SIZE, page)
    }

    override suspend fun fetchClip(gameTitle: String): Response<ClipResponse> {
        return streamApi.fetchClip(gameTitle = gameTitle)
    }

    override suspend fun insertClip(clip: Clip) {
        db.twitchDao().insertClip(clip)
    }

    override suspend fun deleteClip(clip: Clip) {
        db.twitchDao().deleteClip(clip)
    }

    override fun getFavoriteGame(): Flow<List<Clip>> {
        return flow {
            emit(db.twitchDao().getAllClips())
        }.flowOn(Dispatchers.IO)
    }
}