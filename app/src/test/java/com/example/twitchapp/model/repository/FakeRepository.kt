package com.example.twitchapp.model.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.api.TwitchApi
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

class FakeRepository : TwitchRepository {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    var streamApi: TwitchApi = retrofit.create(TwitchApi::class.java)

    var twitchDatabase: TwitchDatabase = Room.databaseBuilder(
        ApplicationProvider.getApplicationContext(),
        TwitchDatabase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().build()

    val behavior = NetworkBehavior.create()

    val delegate = MockRetrofit.Builder(retrofit).networkBehavior(behavior).build()
        .create(TwitchApi::class.java)

    override suspend fun fetchStream(page: Int): Response<Streams> {
        return streamApi.fetchStream(PAGE_SIZE, page)
    }

    override suspend fun fetchClip(gameTitle: String): Response<ClipResponse> {
        return streamApi.fetchClip(gameTitle = gameTitle)
    }

    override suspend fun insertClip(clip: Clip) {
        twitchDatabase.twitchDao().insertClip(clip)
    }

    override suspend fun deleteClip(clip: Clip) {
        twitchDatabase.twitchDao().deleteClip(clip)
    }

    override fun getFavoriteGame(): Flow<List<Clip>> {
        return flow {
            emit(twitchDatabase.twitchDao().getAllClips())
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val DATABASE_NAME = "twitch_database_test"
        private const val BASE_URL = "https://api.twitch.tv/kraken/"
        private const val PAGE_SIZE = 7
    }
}