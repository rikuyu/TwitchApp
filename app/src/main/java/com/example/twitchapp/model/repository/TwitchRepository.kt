package com.example.twitchapp.model.repository

import com.example.twitchapp.model.RetrofitInstance
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.streamdata.Streams
import retrofit2.Response

class TwitchRepository {
    suspend fun fetchPubgMobileStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("PUBG Mobile")
    }

    suspend fun fetchApexStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Apex Legends")
    }

    suspend fun fetchAmongusStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Among Us")
    }

    suspend fun fetchGenshinStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Genshin Impact")
    }

    suspend fun fetchMinecratStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Minecraft")
    }

    suspend fun fetchFortniteStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Fortnite")
    }

    suspend fun fetchCallofdutyStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Call of Duty: Warzone")
    }

    suspend fun fetchLolStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("League of Legends")
    }

    suspend fun fetchPubgMobileClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "PUBG Mobile")
    }

    suspend fun fetchApexClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Apex Legends")
    }

    suspend fun fetchAmongusClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Among Us")
    }

    suspend fun fetchGenshinClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Genshin Impact")
    }

    suspend fun fetchMinecratClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Minecraft")
    }

    suspend fun fetchFortniteClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Fortnite")
    }

    suspend fun fetchCallofdutyClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Call of Duty: Warzone")
    }

    suspend fun fetchLolClip(): Response<Clip>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "League of Legends")
    }
}