package com.example.twitchapp.model.repository

import com.example.twitchapp.model.RetrofitInstance
import com.example.twitchapp.model.Streams
import retrofit2.Response

class TwitchRepository {
    suspend fun getStream(): Response<Streams>{
        return RetrofitInstance.twichApi.getStream()
    }
}