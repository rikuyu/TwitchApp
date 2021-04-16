package com.example.twitchapp.model.repository

import com.example.twitchapp.model.RetrofitInstance
import com.example.twitchapp.model.Streams
import retrofit2.Response

class TwitchRepository {
    suspend fun fetchPubgMobileStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("PUBG Mobile")
    }

    suspend fun fetchApexStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("Apex Legends")
    }

    suspend fun fetchAmongusStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("Among Us")
    }

    suspend fun fetchGenshinStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("Genshin Impact")
    }

    suspend fun fetchMinecratStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("Minecraft")
    }

    suspend fun fetchFortnightStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("Fortnite")
    }

    suspend fun fetchCallofdutyStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("Call of Duty: Warzone")
    }

    suspend fun fetchLolStream(): Response<Streams>{
        return RetrofitInstance.twichApi.fetchStream("League of Legends")
    }
}