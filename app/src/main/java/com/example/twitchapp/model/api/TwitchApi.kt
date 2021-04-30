package com.example.twitchapp.model.api

import com.example.twitchapp.BuildConfig
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.streamdata.Streams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitchApi {

    companion object {
        const val CLIENT_ID = BuildConfig.CLIENT_ID
    }

    @Headers(
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: $CLIENT_ID"
    )
    @GET("streams")
    suspend fun fetchStream(@Query("game") gameTitle: String): Response<Streams>

    @Headers(
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: $CLIENT_ID"
    )
    @GET("clips/top")
    suspend fun fetchClip(
        @Query("trending") trend: Boolean = true,
        @Query("game") gameTitle: String
    ): Response<Clip>
}