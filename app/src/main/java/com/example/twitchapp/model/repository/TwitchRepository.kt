package com.example.twitchapp.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.RetrofitInstance
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import retrofit2.Response

class TwitchRepository(
    private val db: TwitchDatabase?
) {
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

    suspend fun fetchPubgMobileClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "PUBG Mobile")
    }

    suspend fun fetchApexClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Apex Legends")
    }

    suspend fun fetchAmongusClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Among Us")
    }

    suspend fun fetchGenshinClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Genshin Impact")
    }

    suspend fun fetchMinecratClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Minecraft")
    }

    suspend fun fetchFortniteClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Fortnite")
    }

    suspend fun fetchCallofdutyClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "Call of Duty: Warzone")
    }

    suspend fun fetchLolClip(): Response<ClipResponse>{
        return RetrofitInstance.streamApi.fetchClip(gameTitle = "League of Legends")
    }

    suspend fun insertClip(clip: Clip){
        db!!.twitchDao().insertClip(clip)
    }

    suspend fun deleteClip(clip: Clip){
        db!!.twitchDao().deleteClip(clip)
    }

    fun getFavoriteClips(): List<Clip>{
        return db!!.twitchDao().getAllClips()
    }
}