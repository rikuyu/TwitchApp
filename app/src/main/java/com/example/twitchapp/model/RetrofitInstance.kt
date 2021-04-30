package com.example.twitchapp.model

import com.example.twitchapp.model.api.TwitchApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val STREAM_BASE_URL = "https://api.twitch.tv/kraken/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val streamRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(STREAM_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val streamApi: TwitchApi by lazy{
        streamRetrofit.create(TwitchApi::class.java)
    }
}