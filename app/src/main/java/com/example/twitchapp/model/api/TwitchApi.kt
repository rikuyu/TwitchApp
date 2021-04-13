package com.example.twitchapp.model.api

import com.example.twitchapp.BuildConfig
import com.example.twitchapp.model.Streams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TwitchApi {

    companion object{
        const val CLIENT_ID = BuildConfig.CLIENT_ID
    }

    @Headers(
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: $CLIENT_ID"
    )
    @GET("kraken/streams?game=PUBG Mobile")
    suspend fun getStream(): Response<Streams>
}